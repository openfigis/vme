package org.fao.fi.vme.test;

import java.util.ArrayList;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.rule.VmeObservationDomainFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FigisDaoTestLogic {

	VmeObservationDomainFactory f = new VmeObservationDomainFactory();

	@Inject
	protected FigisDao dao;

	@Test
	public void testPersistVmeObservationDomain1() {

		int sizes[] = count();

		RefVme r = registerRefVme();

		// save the related factsheet
		VmeObservationDomain vod = createVmeObservationDomain();
		vod.setRefVme(r);
		dao.persistVmeObservationDomain(vod);

		checkCount(sizes, 1);

		dao.removeVmeObservationDomain(vod);

	}

	@Test
	public void testRemoveVmeObservationDomain() {

		int sizes[] = count();
		// pre-register RefVme
		RefVme r = createRefVme();
		dao.syncRefVme(r);

		// save the related factsheet
		VmeObservationDomain vod = createVmeObservationDomain();
		vod.setRefVme(r);
		dao.persistVmeObservationDomain(vod);
		dao.removeVmeObservationDomain(vod);

		checkCount(sizes, 0);
	}

	protected RefVme createRefVme() {

		Long id = new Long(4354);
		RefVme r = new RefVme();
		r.setId(id);
		return r;
	}

	protected VmeObservationDomain createVmeObservationDomain() {
		VmeObservationDomain vo = new VmeObservationDomain();
		vo.setReportingYear("2013");
		vo.setObservationList(new ArrayList<Observation>());
		Observation o = createObservation();
		ObservationXml xml = createObservationXml();
		o.setObservationsPerLanguage(new ArrayList<ObservationXml>());
		o.getObservationsPerLanguage().add(xml);
		vo.getObservationList().add(o);
		return vo;
	}

	protected void delegateCheckOnNumberOfObjectsInModel(int i) {
		assertEquals(i, dao.loadObjects(ObservationXml.class).size());
		assertEquals(i, dao.loadObjects(Observation.class).size());
		assertEquals(i, dao.loadObjects(VmeObservation.class).size());
	}

	protected int[] count() {
		int[] sizes = { dao.loadObjects(ObservationXml.class).size(), dao.loadObjects(Observation.class).size(),
				dao.loadObjects(VmeObservation.class).size() };
		return sizes;
	}

	protected void checkCount(int[] startSizes, int diff) {
		int[] newSizes = count();
		for (int i = 0; i < newSizes.length; i++) {
			int oldNewSize = startSizes[i] + diff;
			assertEquals("elementnr" + i, oldNewSize, newSizes[i]);
		}

	}

	protected ObservationXml createObservationXml() {
		ObservationXml xml = f.createObservationXml();
		return xml;
	}

	protected Observation createObservation() {
		Observation o = f.createObservation();
		return o;
	}

	protected RefVme registerRefVme() {
		// pre-register RefVme
		RefVme r = createRefVme();
		dao.syncRefVme(r);
		return r;
	}

}
