package org.fao.fi.vme.test;

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

import static org.junit.Assert.assertEquals;

public abstract class FigisDaoTestLogic {

	@Inject
	protected FigisDao dao;

	// @Test
	// public void testPersistVmeObservationDomain1() {
	//
	// int sizes[] = count();
	//
	// RefVme r = registerRefVme();
	//
	// // save the related factsheet
	// VmeObservationDomain vod = createVmeObservationDomain();
	// vod.setRefVme(r);
	// dao.syncVmeObservationDomain(vod);
	//
	// checkCount(sizes, 1);
	//
	// dao.removeVmeObservationDomain(vod.getRefVme());
	//
	// // TODO
	// // checkCount(sizes, 0);
	//
	// }

	// @Test
	// public void testRemoveVmeObservationDomain() {
	//
	// int sizes[] = count();
	// // pre-register RefVme
	// RefVme r = createRefVme();
	// dao.syncRefVme(r);
	//
	// // save the related factsheet
	// VmeObservationDomain vod = createVmeObservationDomain();
	// vod.setRefVme(r);
	// dao.syncVmeObservationDomain(vod);
	// dao.removeVmeObservationDomain(vod.getRefVme());
	//
	// // TODO
	// // checkCount(sizes, 0);
	// }

	protected RefVme createRefVme() {

		Long id = new Long(4354);
		RefVme r = new RefVme();
		r.setId(id);
		return r;
	}

	protected VmeObservationDomain createVmeObservationDomain() {
		VmeObservationDomain vo = new VmeObservationDomain();
		List<ObservationDomain> odList = new ArrayList<ObservationDomain>();
		vo.setObservationDomainList(odList);
		ObservationDomain o = new ObservationDomain();
		o.setReportingYear("2014");
		ObservationXml xml = ObservationXmlMock.create();

		o.setObservationsPerLanguage(new ArrayList<ObservationXml>());
		o.getObservationsPerLanguage().add(xml);
		vo.getObservationDomainList().add(o);
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

	protected RefVme registerRefVme() {
		// pre-register RefVme
		RefVme r = createRefVme();
		dao.syncRefVme(r);
		return r;
	}

}
