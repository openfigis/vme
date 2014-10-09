package org.vme.dao.sources.vme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.MediaReference;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.GeneralMeasureMock;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.MediaReferenceMock;
import org.fao.fi.vme.domain.test.RfmoMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.test.VmeTypeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class VmeDaoTest {

	@Inject
	private VmeDao dao;
	private Vme vme;

	@Before
	public void before() {
		vme = VmeMock.generateVme(3);
	}

	/**
	 * 
	 * http://figisapps.fao.org/jira/browse/VME-59
	 * 
	 * 12:58:52,669 [ ERROR ] {
	 * org.fao.fi.vme.rsg.service.RsgServiceWriteImplVme } - Unable to update
	 * org.fao.fi.vme.domain.model.Vme report #29744: PersistenceException [
	 * org.hibernate.HibernateException: A collection with
	 * cascade="all-delete-orphan" was no longer referenced by the owning entity
	 * instance: org.fao.fi.vme.domain.model.Vme.mediaReferenceList ]
	 * javax.persistence.PersistenceException: org.hibernate.HibernateException:
	 * A collection with cascade="all-delete-orphan" was no longer referenced by
	 * the owning entity instance:
	 * org.fao.fi.vme.domain.model.Vme.mediaReferenceList
	 * 
	 * 
	 * 
	 * Caused by: org.hibernate.HibernateException: A collection with
	 * cascade="all-delete-orphan" was no longer referenced by the owning entity
	 * instance: org.fao.fi.vme.domain.model.Vme.mediaReferenceList
	 * 
	 * @throws Throwable
	 */

	@Test
	public void testMediaReferenceNolongerReferencedBug() throws Throwable {
		// System.out.println(vme.getId());
		//
		// long id = 24232l;
		//
		// Vme vme = new Vme();
		// vme.setRfmo(RfmoMock.createUnreferenced());
		// Vme vmeFound = dao.findVme(id);
		//
		// vme.setValidityPeriod(ValidityPeriodMock.create(1900, 2050));
		// vme.setId(vmeFound.getId());
		//
		// vme.setMediaReferenceList(MediaReferenceMock.createList());
		// // vme.setMediaReferenceList(new ArrayList<MediaReference>());
		//
		// EntityTransaction t = dao.begin();
		// dao.update(vme);
		// t.commit();
	}

	@Test
	public void testMediaReference() throws Throwable {
		Vme vme = new Vme();
		vme.setMediaReferenceList(MediaReferenceMock.createList());
		MediaReference mr = vme.getMediaReferenceList().get(0);
		mr.setVme(vme);
		assertEquals(1, vme.getMediaReferenceList().size());
		int v = dao.count(Vme.class).intValue();
		int m = dao.count(MediaReference.class).intValue();

		dao.persist(vme);
		assertEquals(++v, dao.count(Vme.class).intValue());
		assertEquals(++m, dao.count(MediaReference.class).intValue());
		assertEquals(1, vme.getMediaReferenceList().size());
		assertTrue(vme.getMediaReferenceList().remove(mr));
		assertEquals(0, vme.getMediaReferenceList().size());
		dao.merge(vme);
		assertEquals(v, dao.count(Vme.class).intValue());
		assertEquals(--m, dao.count(MediaReference.class).intValue());

	}

	@Test
	public void testUpdateWithCopyMultiLingual() throws Throwable {
		MultiLingualStringUtil u = new MultiLingualStringUtil();

		GeneralMeasure g = GeneralMeasureMock.create();
		Rfmo rfmo = RfmoMock.createUnreferenced();
		g.setRfmo(rfmo);
		dao.persist(rfmo);
		dao.persist(g);
		assertEquals(5, dao.count(MultiLingualString.class).intValue());

		GeneralMeasure g2 = GeneralMeasureMock.create();
		g2.setExplorataryFishingProtocol(u.english("fiets"));
		u.copyMultiLingual(g2, g);
		EntityManager em = this.dao.getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		dao.update(g);
		tx.commit();
		assertEquals(5, dao.count(MultiLingualString.class).intValue());

	}

	/**
	 * Test to understand how the MultiLingualString behaves with JPA
	 * 
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testUpdate() throws Throwable {
		MultiLingualStringUtil u = new MultiLingualStringUtil();

		GeneralMeasure g = GeneralMeasureMock.create();
		Rfmo rfmo = RfmoMock.createUnreferenced();
		g.setRfmo(rfmo);
		dao.persist(rfmo);
		dao.persist(g);
		assertEquals(5, dao.count(MultiLingualString.class).intValue());

		System.out.println(u.getEnglish(g.getFishingArea()));
		String newText = "My new world";
		u.replaceEnglish(g.getFishingArea(), newText);

		EntityManager em = this.dao.getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		dao.update(g);
		tx.commit();
		System.out.println(u.getEnglish(g.getFishingArea()));

		assertEquals(5, dao.count(MultiLingualString.class).intValue());

	}

	@Test
	public void testCreate() throws Throwable {
		String id = "fhuewiqof";
		Rfmo rfmo = new Rfmo();
		rfmo.setId(id);
		dao.persist(rfmo);
		Vme vme = new Vme();
		vme.setRfmo(rfmo);
		vme.setInventoryIdentifier("FIETS");

		// This simulates how RsgServiceImplVme is using this method. Which is
		// debatable because it would be cleaner to have all the transaction
		// logic in the dao.
		EntityManager em = dao.getEm();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		dao.create(vme);
		tx.commit();
		assertEquals(1, dao.count(Vme.class).intValue());

	}

	@Test
	public void testCircelRelation() {
		String id = "fhuewiqof";
		Rfmo rfmo = new Rfmo();
		rfmo.setId(id);

		InformationSource i = new InformationSource();
		i.setRfmo(rfmo);

		SpecificMeasure s = new SpecificMeasure();
		s.setInformationSource(i);

		Vme v = new Vme();
		v.setRfmo(rfmo);

		List<Vme> vList = new ArrayList<Vme>();
		vList.add(v);
		rfmo.setListOfManagedVmes(vList);

		List<SpecificMeasure> sList4Vme = new ArrayList<SpecificMeasure>();
		sList4Vme.add(s);
		v.setSpecificMeasureList(sList4Vme);

		List<SpecificMeasure> sList4SpecificMeasure = new ArrayList<SpecificMeasure>();
		sList4SpecificMeasure.add(s);
		i.setSpecificMeasureList(sList4SpecificMeasure);

		List<InformationSource> iList4Rfmo = new ArrayList<InformationSource>();
		iList4Rfmo.add(i);
		rfmo.setInformationSourceList(iList4Rfmo);

		GeneralMeasure g = new GeneralMeasure();
		g.setRfmo(rfmo);

		List<InformationSource> iList4GeneralMeasure = new ArrayList<InformationSource>();
		iList4GeneralMeasure.add(i);
		g.setInformationSourceList(iList4GeneralMeasure);

		List<GeneralMeasure> gList4Rfmo = new ArrayList<GeneralMeasure>();
		gList4Rfmo.add(g);
		rfmo.setGeneralMeasureList(gList4Rfmo);

		List<GeneralMeasure> gList4IformationSource = new ArrayList<GeneralMeasure>();
		gList4IformationSource.add(g);
		i.setGeneralMeasureList(gList4IformationSource);

		List<Object> oList = new ArrayList<Object>();
		oList.add(rfmo);
		oList.add(v);
		oList.add(s);
		oList.add(i);
		oList.add(g);

		dao.persist(oList);
		for (Object object : oList) {
			assertTrue(dao.getEm().contains(object));
		}
	}

	@Test
	public void testMergeRfmo() {
		String id = "fiosdfsd";
		Rfmo rfmo = new Rfmo();
		rfmo.setId(id);
		dao.persist(rfmo);

		List<FisheryAreasHistory> hasFisheryAreasHistory = new ArrayList<FisheryAreasHistory>();
		FisheryAreasHistory h = new FisheryAreasHistory();
		h.setYear(2008);
		// h.setId(456l);

		dao.persist(h);

		hasFisheryAreasHistory.add(h);
		rfmo.setHasFisheryAreasHistory(hasFisheryAreasHistory);

		dao.merge(rfmo);

	}

	@Test
	public void testSave() {
		dao.persist(InformationSourceMock.createInformationSourceType());
		int nrOfyears = 1;
		Vme vme = VmeMock.generateVme(nrOfyears);
		dao.persist(VmeTypeMock.create());
		dao.saveVme(vme);
		assertNotNull(vme.getId());
		Vme vmeFound = dao.findVme(vme.getId());
		assertTrue(vmeFound.getRfmo().getGeneralMeasureList().size() > 0);
		for (GeneralMeasure gm : vmeFound.getRfmo().getGeneralMeasureList()) {
			assertNotNull(gm.getFishingArea());
		}

	}

	@Test
	public void testLoadVmes() {
		assertNotNull(dao);

		// Vme vme = new Vme();
		// String criteria = "go";
		// vme.setCriteria(criteria);
		dao.loadVmes();
	}

	@Test
	public void testCount() {
		assertEquals(0, dao.count(Vme.class).intValue());
		dao.saveVme(vme);
		assertEquals(1, dao.count(Vme.class).intValue());
	}

}
