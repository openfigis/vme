package org.vme.dao.sources.vme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.junit.Test;

public class Update1nCardinalityTest {

	Update1nCardinality u1n = new Update1nCardinality();

	/**
	 * TODO update this also for Vme-SpecificMeasureList - InformationSource
	 * 
	 * 
	 */
	@Test
	public void testUpdateCRUDInformationSource() {
		DummyEm em = new DummyEm();

		SpecificMeasure specificMeasureManaged = new SpecificMeasure();
		em.persist(specificMeasureManaged);
		SpecificMeasure specificMeasureDto = new SpecificMeasure();

		List<SpecificMeasure> specificMeasureListManaged = new ArrayList<SpecificMeasure>();
		List<SpecificMeasure> specificMeasureListDto = new ArrayList<SpecificMeasure>();

		Vme vmeManaged = new Vme();
		em.persist(vmeManaged);
		vmeManaged.setSpecificMeasureList(specificMeasureListManaged);

		InformationSource informationSourceManaged = new InformationSource();
		em.persist(informationSourceManaged);

		// the following status changes only apply to informationsource
		// 0 to 0
		u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);
		assertEquals(0, specificMeasureListManaged.size());
		assertEquals(0, vmeManaged.getSpecificMeasureList().size());

		// 0 to 1
		specificMeasureDto.setInformationSource(informationSourceManaged);
		specificMeasureListDto.add(specificMeasureDto);
		u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);
		assertEquals(1, specificMeasureListManaged.size());
		assertEquals(informationSourceManaged, specificMeasureListManaged.get(0).getInformationSource());

		// 1 to 0
		specificMeasureDto.setInformationSource(null);
		u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);
		assertEquals(1, specificMeasureListManaged.size());
		assertNull(specificMeasureListManaged.get(0).getInformationSource());

		// 1 to 1
		// 1 to another

	}

	/**
	 * 
	 * 
	 * 
	 */
	@Test
	public void testUpdate() {
		DummyEm em = new DummyEm();

		SpecificMeasure specificMeasureManaged = new SpecificMeasure();
		em.persist(specificMeasureManaged);
		SpecificMeasure specificMeasureDto = new SpecificMeasure();

		List<SpecificMeasure> specificMeasureListManaged = new ArrayList<SpecificMeasure>();
		List<SpecificMeasure> specificMeasureListDto = new ArrayList<SpecificMeasure>();

		Vme vmeManaged = new Vme();
		em.persist(vmeManaged);

		// 0 to 0
		u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);
		assertEquals(0, specificMeasureListManaged.size());

		// 0 to 1
		specificMeasureListDto.add(specificMeasureDto);
		u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);
		assertEquals(1, specificMeasureListManaged.size());

		// 1 to 0
		specificMeasureListDto.remove(specificMeasureDto);
		u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);
		assertEquals(0, specificMeasureListManaged.size());

		// 0 to 1
		em.remove(specificMeasureManaged);
		specificMeasureDto.setId(null);
		specificMeasureListDto.add(specificMeasureDto);
		u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);
		assertEquals(1, specificMeasureListManaged.size());

		// 1 to 1
		u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);
		assertEquals(1, specificMeasureListManaged.size());
		u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);
		assertEquals(1, specificMeasureListManaged.size());

		// 1 to another 1
		specificMeasureListDto.remove(specificMeasureDto);
		SpecificMeasure anotherDto = new SpecificMeasure();
		specificMeasureListDto.add(anotherDto);
		u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);
		assertEquals(1, specificMeasureListManaged.size());
		assertEquals(anotherDto, specificMeasureListManaged.get(0));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateProfile() {
		Vme vmeDto = VmeMock.create();
		Vme vmeEm = VmeMock.create();
		DummyEm em = new DummyEm();

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

}
