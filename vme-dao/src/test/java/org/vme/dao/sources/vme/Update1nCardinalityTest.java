package org.vme.dao.sources.vme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.junit.Before;
import org.junit.Test;

public class Update1nCardinalityTest {

	Update1nCardinality u1n;
	EntityManager em;

	@Before
	public void before() {
		u1n = new Update1nCardinality();
		em = new DummyEm();
	}

	/**
	 * Solving this bug: java.lang.NullPointerException at
	 * org.vme.dao.sources.vme.Update1nCardinality.processCopyInformationSource(Update1nCardinality.java:176)
	 * 
	 * This test is a kind of repetition of 1 to 0 of testUpdateCRUDInformationSource
	 * 
	 */
	@Test
	public void testProcessCopyInformationSource() {

		InformationSource informationSourceManaged = new InformationSource();
		em.persist(informationSourceManaged);
		List<SpecificMeasure> specificMeasureListManaged = new ArrayList<SpecificMeasure>();
		informationSourceManaged.setSpecificMeasureList(specificMeasureListManaged);

		em.persist(informationSourceManaged);

		SpecificMeasure specificMeasureManaged = new SpecificMeasure();
		specificMeasureManaged.setInformationSource(informationSourceManaged);
		specificMeasureListManaged.add(specificMeasureManaged);

		em.persist(specificMeasureManaged);
		// this is a strange hack. Probably because the em is not running in an transaction, it is not setting the id

		if (em instanceof DummyEm) {

			SpecificMeasure specificMeasureDto = new SpecificMeasure();

			List<SpecificMeasure> specificMeasureListDto = new ArrayList<SpecificMeasure>();
			specificMeasureListDto.add(specificMeasureDto);
			specificMeasureDto.setId(specificMeasureManaged.getId());

			Vme vmeManaged = new Vme();
			em.persist(vmeManaged);
			vmeManaged.setSpecificMeasureList(specificMeasureListManaged);

			// 1 to 0
			specificMeasureDto.setInformationSource(null);
			assertNotNull(specificMeasureListManaged.get(0).getInformationSource());
			u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);
			assertEquals(0, specificMeasureListManaged.size());
		}
	}

	/**
	 * 
	 */
	@Test
	public void testUpdateSpecificMeasureNull2() {
		SpecificMeasure specificMeasureManaged = new SpecificMeasure();
		specificMeasureManaged.setYear(2013);
		em.persist(specificMeasureManaged);

		List<SpecificMeasure> specificMeasureListManaged = new ArrayList<SpecificMeasure>();
		specificMeasureListManaged.add(specificMeasureManaged);

		SpecificMeasure specificMeasureDto = new SpecificMeasure();
		List<SpecificMeasure> specificMeasureListDto = new ArrayList<SpecificMeasure>();
		specificMeasureListDto.add(specificMeasureDto);

		Vme vmeManaged = new Vme();
		em.persist(vmeManaged);
		vmeManaged.setSpecificMeasureList(specificMeasureListManaged);
		em.persist(vmeManaged);

		u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);

	}

	/**
	 * Bug found in QA
	 * 
	 * 17:58:35,591 [ ERROR ] { org.fao.fi.vme.rsg.service.RsgServiceWriteImplVme } - Unable to update
	 * org.fao.fi.vme.domain.model.Vme report #23787: IllegalArgumentException [ org.hibernate.ObjectDeletedException:
	 * deleted instance passed to merge: [org.fao.fi.vme.domain.model.SpecificMeasure#<null>] ]
	 * java.lang.IllegalArgumentException: org.hibernate.ObjectDeletedException: deleted instance passed to merge:
	 * [org.fao.fi.vme.domain.model.SpecificMeasure#<null>] at
	 * org.hibernate.jpa.spi.AbstractEntityManagerImpl.merge(AbstractEntityManagerImpl.java:1199) at
	 * sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	 * 
	 * 
	 * 
	 */
	@Test
	public void testUpdateSpecificMeasureNull() {
		SpecificMeasure specificMeasureDto = new SpecificMeasure();

		List<SpecificMeasure> specificMeasureListDto = new ArrayList<SpecificMeasure>();
		specificMeasureListDto.add(specificMeasureDto);

		Vme vmeManaged = new Vme();
		em.persist(vmeManaged);

		u1n.update(em, vmeManaged, specificMeasureListDto, null);

	}

	/**
	 * Bug found in QA, what if the getMediaReferenceList is null?
	 * 
	 */
	@Test
	public void testUpdategetMediaReferenceListNull() {
		Vme vmeManaged = new Vme();
		em.persist(vmeManaged);
		u1n.update(em, vmeManaged, null, null);
	}

	/**
	 * 
	 * 
	 */
	@Test
	public void testUpdateCRUDInformationSource() {

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
		specificMeasureDto.setInformationSource(informationSourceManaged);
		specificMeasureListDto.add(specificMeasureDto);
		u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);
		assertEquals(1, specificMeasureListManaged.size());
		assertEquals(informationSourceManaged, specificMeasureListManaged.get(0).getInformationSource());

		// 1 to another
		InformationSource anotherInformationSource = new InformationSource();
		em.persist(anotherInformationSource);

		// this is a strange hack. Probably because the em is not running in an transaction, it is not setting the id
		anotherInformationSource.setId(2587l);

		specificMeasureDto.setInformationSource(anotherInformationSource);
		u1n.update(em, vmeManaged, specificMeasureListDto, specificMeasureListManaged);
		assertEquals(1, specificMeasureListManaged.size());
		assertEquals(anotherInformationSource, specificMeasureListManaged.get(0).getInformationSource());

	}

	/**
	 * 
	 * 
	 * 
	 */
	@Test
	public void testUpdate() {

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

	@Test
	public void testUpdateProfile() {
		Vme vmeDto = VmeMock.create();
		Vme vmeEm = VmeMock.create();
		em.persist(vmeEm);

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
