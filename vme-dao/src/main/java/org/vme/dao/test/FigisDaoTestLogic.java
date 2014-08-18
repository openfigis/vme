package org.vme.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.rule.DomainRule4ObservationXmlId;
import org.fao.fi.figis.domain.test.ObservationXmlMock;
import org.fao.fi.figis.domain.test.RefVmeMock;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.sources.figis.DefaultObservationDomain;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.figis.PrimaryRule;

public abstract class FigisDaoTestLogic {

	public static final int REPORTING_YEAR = 2014;
	PrimaryRule r = new PrimaryRule();
	private static final Logger LOG = LoggerFactory.getLogger(FigisDaoTestLogic.class);

	@Inject
	protected FigisDao dao;

	/**
	 * This test is done in unit test context and integration test context. In
	 * integration test context the @Before and @After do the cleaning work.
	 */
	@Test
	public void testSyncVmeObservationDomain() {
		RefVme refVme = RefVmeMock.create();
		if (dao.find(RefVme.class, refVme.getId()) == null) {
			dao.persist(refVme);
		}
		int[] count = count();
		VmeObservationDomain vod = createVmeObservationDomain(1);
		assertEquals(1, vod.getObservationDomainList().size());

		vod.setRefVme(refVme);
		checkCount(count, 0);
		dao.syncVmeObservationDomain(vod);
		checkCount(count, 1);

		assertTrue(vod.getObservationDomainList().get(0).getId() > 0);

		dao.syncVmeObservationDomain(vod);
		checkCount(count, 1);

		add1Observation2Vod(vod);

		PrimaryRule rule = new PrimaryRule();
		rule.apply(vod);
		dao.syncVmeObservationDomain(vod);
		assertFalse(vod.getObservationDomainList().get(0).isPrimary());
		assertTrue(vod.getObservationDomainList().get(1).isPrimary());
		assertEquals(2, vod.getObservationDomainList().size());
		checkCount(count, 2);

	}

	/**
	 * This test is done in unit test context and integration test context. In
	 * integration test context the @Before and @After do the cleaning work.
	 */
	@Test
	public void testSyncVmeObservationDomainUpdate() {
		RefVme refVme = RefVmeMock.create();
		if (dao.find(RefVme.class, refVme.getId()) == null) {
			dao.persist(refVme);
		}
		int[] count = count();
		VmeObservationDomain vod = createVmeObservationDomain(1);

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
		RefVme r1 = new RefVme();
		r1.setId(id);
		return r1;
	}

	protected VmeObservationDomain createVmeObservationDomain(int number) {
		VmeObservationDomain vod = new VmeObservationDomain();
		List<ObservationDomain> odList = new ArrayList<ObservationDomain>();
		vod.setObservationDomainList(odList);
		for (int i = 0; i < number; i++) {
			ObservationDomain o = new DefaultObservationDomain().defineDefaultObservation();
			o.setReportingYear(Integer.toString(REPORTING_YEAR + i));
			ObservationXml xml = ObservationXmlMock.create();

			o.setObservationsPerLanguage(new ArrayList<ObservationXml>());
			o.getObservationsPerLanguage().add(xml);
			vod.getObservationDomainList().add(o);
		}
		r.apply(vod);
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

		LOG.info("ObservationXml= " + sizes[0]);
		LOG.info("Observation= " + sizes[1]);
		LOG.info("VmeObservation= " + sizes[2]);
		LOG.info("=================================================");

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
		RefVme r2 = createRefVme();
		dao.syncRefVme(r2);
		return r2;
	}

	protected void clean() {
		VmeObservationDomain vod = createVmeObservationDomain(1);
		vod.setRefVme(RefVmeMock.create());
		add1Observation2Vod(vod);
		List<ObservationDomain> oList = vod.getObservationDomainList();
		for (ObservationDomain od : oList) {
			// find VmeObservation
			VmeObservation vo = dao.findExactVmeObservation(vod.getRefVme().getId(),
					Integer.parseInt(od.getReportingYear()));
			if (vo != null) {
				Observation o = (Observation) dao.find(Observation.class, vo.getId().getObservationId());
				DomainRule4ObservationXmlId rule = new DomainRule4ObservationXmlId();
				ObservationXml xml = ObservationXmlMock.create();
				xml.setObservation(o);
				rule.composeId(xml);
				ObservationXml xmlFound = (ObservationXml) dao.find(ObservationXml.class, xml.getId());
				if (xmlFound != null) {
					dao.remove(xmlFound);
					dao.remove(vo);
					dao.remove(o);
				}
			}
		}
	}

}
