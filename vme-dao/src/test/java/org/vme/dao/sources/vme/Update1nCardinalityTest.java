package org.vme.dao.sources.vme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.junit.Test;

public class Update1nCardinalityTest {

	Update1nCardinality<Profile> u1n = new Update1nCardinality<Profile>();
	DummyEm em = new DummyEm();

	@Test
	public void erik() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		SpecificMeasure source = new SpecificMeasure();
		source.setValidityPeriod(ValidityPeriodMock.create());
		SpecificMeasure destination = new SpecificMeasure();

		PropertyUtilsBean l = new PropertyUtilsBean();
		l.setProperty(destination, "validityPeriod", source.getValidityPeriod());

		assertTrue(destination.getValidityPeriod() instanceof ValidityPeriod);
	}

	@Test
	public void testProcessOtherProperties() {
		SpecificMeasure source = new SpecificMeasure();
		InformationSource is1 = new InformationSource();

		List<SpecificMeasure> sList = new ArrayList<SpecificMeasure>();
		sList.add(source);
		is1.setSpecificMeasureList(sList);
		source.setInformationSource(is1);

		// 1 to 1
		SpecificMeasure destination = new SpecificMeasure();
		u1n.processOtherProperties(em, source, destination);
		assertEquals(source.getInformationSource(), destination.getInformationSource());

		// 1 to 0
		source.setInformationSource(null);
		u1n.processOtherProperties(em, source, destination);
		assertNull(destination.getInformationSource());

		// 0 to 0
		source.setInformationSource(null);
		u1n.processOtherProperties(em, source, destination);
		assertNull(destination.getInformationSource());

		// 0 to 1
		source.setInformationSource(is1);
		u1n.processOtherProperties(em, source, destination);
		assertEquals(is1, destination.getInformationSource());

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateProfile() {
		Vme vmeDto = VmeMock.create();
		Vme vmeEm = VmeMock.create();

		List<Profile> l = vmeEm.getProfileList();
		for (int i = 0; i < l.size(); i++) {
			Profile g = l.get(i);
			em.persist(g);
			vmeDto.getProfileList().get(i).setId(g.getId());
		}

		assertEquals(4, vmeDto.getProfileList().size());
		assertEquals(vmeDto.getProfileList().size(), vmeDto.getProfileList().size());
		u1n.update(em, vmeEm, vmeDto.getProfileList(), vmeEm.getProfileList());

		// test delete 1 element
		Profile toBeDeleted = vmeDto.getProfileList().get(0);
		vmeDto.getProfileList().remove(toBeDeleted);
		u1n.update(em, vmeEm, vmeDto.getProfileList(), vmeEm.getProfileList());
		assertEquals(3, vmeEm.getProfileList().size());
		assertEquals(toBeDeleted.getId(), ((ObjectId<Long>) em.getRemovedObject()).getId());

		// test change a property (be aware the order of the list will therefore change because of the YearComperator)
		Profile p = vmeDto.getProfileList().get(0);
		p.setYear(100);
		u1n.update(em, vmeEm, vmeDto.getProfileList(), vmeEm.getProfileList());

		List<Profile> profiles = vmeEm.getProfileList();
		int found = 0;
		for (Profile profile : profiles) {
			if (profile.getId().equals(p.getId()) && profile.getYear().equals(100)) {
				found++;
			}
		}
		assertEquals(1, found);
	}

	@Test
	public void testCopyCertainProperties() {
		Update1nCardinality<Profile> u1n = new Update1nCardinality<Profile>();
		Profile source = new Profile();
		source.setVme(new Vme());
		Profile destination = new Profile();
		u1n.copyCertainProperties(source, destination);
		assertFalse(source.getVme().equals(destination.getVme()));

		source.setId(100l);
		u1n.copyCertainProperties(source, destination);
		assertTrue(source.getId().equals(destination.getId()));

	}
}
