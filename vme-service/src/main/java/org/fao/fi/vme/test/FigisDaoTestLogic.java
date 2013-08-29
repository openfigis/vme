package org.fao.fi.vme.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.test.ObservationXmlMock;
import org.fao.fi.figis.domain.test.RefVmeMock;
import org.fao.fi.vme.sync2.mapping.DefaultObservationDomain;
import org.junit.Test;

public abstract class FigisDaoTestLogic {

	public static String REPORTING_YEAR = "2014";

	@Inject
	protected FigisDao dao;

	/**
	 * This test is done in unit test context and integration test context. In integration test context the @Before and @After
	 * do the cleaning work.
	 */
	@Test
	public void testSyncVmeObservationDomain() {
		RefVme refVme = RefVmeMock.create();
		if (dao.find(RefVme.class, refVme.getId()) == null) {
			dao.persist(refVme);
		}
		int count[] = count();
		VmeObservationDomain vod = createVmeObservationDomain();
		assertEquals(1, vod.getObservationDomainList().size());

		vod.setRefVme(refVme);
		checkCount(count, 0);
		dao.syncVmeObservationDomain(vod);
		checkCount(count, 1);

		assertTrue(vod.getObservationDomainList().get(0).getId() > 0);

		dao.syncVmeObservationDomain(vod);
		checkCount(count, 1);

		add1Observation2Vod(vod);

		assertFalse(vod.getObservationDomainList().get(0).isPrimary());
		dao.syncVmeObservationDomain(vod);
		assertEquals(2, vod.getObservationDomainList().size());
		checkCount(count, 2);

	}

	/**
	 * This test is done in unit test context and integration test context. In integration test context the @Before and @After
	 * do the cleaning work.
	 */
	@Test
	public void testSyncVmeObservationDomainUpdate() {
		RefVme refVme = RefVmeMock.create();
		if (dao.find(RefVme.class, refVme.getId()) == null) {
			dao.persist(refVme);
		}
		int count[] = count();
		VmeObservationDomain vod = createVmeObservationDomain();

		vod.setRefVme(refVme);
		checkCount(count, 0);
		dao.syncVmeObservationDomain(vod);
		checkCount(count, 1);

		VmeObservationDomain f1 = dao.findVod(refVme.getId());
		String xml = "Hello";
		f1.getObservationDomainList().get(0).getObservationsPerLanguage().get(0).setXml(xml);
		dao.syncVmeObservationDomain(f1);

		VmeObservationDomain f2 = dao.findVod(refVme.getId());
		assertEquals(xml, f2.getObservationDomainList().get(0).getObservationsPerLanguage().get(0).getXml());

	}

	protected void add1Observation2Vod(VmeObservationDomain vod) {
		ObservationXml xml = ObservationXmlMock.create();

		ObservationDomain o = new DefaultObservationDomain().defineDefaultObservation();
		o.setReportingYear("2015");
		o.setObservationsPerLanguage(new ArrayList<ObservationXml>());
		o.getObservationsPerLanguage().add(xml);
		vod.getObservationDomainList().add(o);

	}

	protected RefVme createRefVme() {

		Long id = new Long(4354);
		RefVme r = new RefVme();
		r.setId(id);
		return r;
	}

	protected VmeObservationDomain createVmeObservationDomain() {
		VmeObservationDomain vod = new VmeObservationDomain();

		List<ObservationDomain> odList = new ArrayList<ObservationDomain>();
		vod.setObservationDomainList(odList);
		ObservationDomain o = new DefaultObservationDomain().defineDefaultObservation();
		o.setReportingYear(REPORTING_YEAR);
		ObservationXml xml = ObservationXmlMock.create();

		o.setObservationsPerLanguage(new ArrayList<ObservationXml>());
		o.getObservationsPerLanguage().add(xml);
		vod.getObservationDomainList().add(o);
		return vod;
	}

	protected void delegateCheckOnNumberOfObjectsInModel(int i) {
		assertEquals(i, dao.count(ObservationXml.class).intValue());
		assertEquals(i, dao.count(Observation.class).intValue());
		assertEquals(i, dao.count(VmeObservation.class).intValue());
	}

	protected int[] count() {
		int[] sizes = { dao.count(ObservationXml.class).intValue(), dao.count(Observation.class).intValue(),
				dao.count(VmeObservation.class).intValue() };
		return sizes;
	}

	protected void checkCount(int[] startSizes, int diff) {
		int[] newSizes = count();
		for (int i = 0; i < newSizes.length; i++) {
			int oldNewSize = startSizes[i] + diff;
			assertEquals("elementnr" + i, oldNewSize, newSizes[i]);
		}

	}

	protected RefVme registerRefVme() {
		// pre-register RefVme
		RefVme r = createRefVme();
		dao.syncRefVme(r);
		return r;
	}

}
