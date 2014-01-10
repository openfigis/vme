package org.fao.fi.figis.dao;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationPk;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.vme.test.FigisDaoTestLogic;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.figis.FigisDataBaseProducer;
import org.vme.service.dao.sources.figis.FigisDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class })
public class FigisDaoIntegrationTest extends FigisDaoTestLogic {

	@Inject
	FigisDao figisDao;

	@Before
	public void testBefore() {
		clean();
	}

	@After
	public void testAfter() {
		clean();
	}

	/**
	 * TODO Bizarre problem. When it finds a RefVme, it will just block and the
	 * program never stops.
	 * 
	 * One hour later. Bizar, it looks like not using this anymore was the
	 * solution: dao.loadObjects(ObservationXml.class).size()
	 * 
	 */
	@Test
	public void testDeleteRefVme() {

		List<RefVme> refVmeList = figisDao.selectFrom(figisDao.getEm(), RefVme.class);
		for (RefVme refVme : refVmeList) {
			figisDao.remove(refVme);
		}
	}

	@Test
	public void testPersistVmeObservation2() {

		VmeObservationPk id = new VmeObservationPk();
		id.setObservationId(10000l);
		id.setReportingYear("1010");
		id.setVmeId(5050l);

		figisDao.find(VmeObservation.class, id);
		if (figisDao.find(VmeObservation.class, id) != null) {
			figisDao.remove(figisDao.find(VmeObservation.class, id));
		}
		VmeObservation vo = new VmeObservation();

		// TODO
		// vo.setObservationId(id.longValue());
		// vo.setReportingYear("2013");
		// vo.setVmeId(10l);
		// dao.persist(vo);
		// figisDao.remove(figisDao.find(VmeObservation.class,
		// vo.getObservationId()));
	}

	@Test
	public void testPersistObservation() {
		Observation observation = new Observation();
		observation.setOrder(Figis.ORDER);
		observation.setCollection(Figis.COLLECTION);
		observation.setPrimary(Figis.PRIMARY);
		observation.setReference(Figis.REFERENCE);

		figisDao.persist(observation);
		figisDao.remove(observation);
	}

}
