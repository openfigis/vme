package org.fao.fi.figis.dao;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.sync2.VmeRefSync;
import org.fao.fi.vme.test.FigisDaoTestLogic;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class })
public class FigisDaoIntegrationTest extends FigisDaoTestLogic {

	@Inject
	FigisDao figisDao;

	@Test
	public void testPersistVmeObservation2() {
		Long id = new Long(10000);
		figisDao.find(VmeObservation.class, id);
		if (figisDao.find(VmeObservation.class, id) != null) {
			figisDao.remove(figisDao.find(VmeObservation.class, id));
		}
		VmeObservation vo = new VmeObservation();
		vo.setObservationId(id.longValue());
		vo.setReportingYear("2013");
		vo.setVmeId(10l);
		dao.persist(vo);
		figisDao.remove(figisDao.find(VmeObservation.class, vo.getObservationId()));
	}

	@Test
	public void testPersistObservation() {
		Observation observation = new Observation();
		observation.setOrder(VmeRefSync.ORDER);
		observation.setCollection(VmeRefSync.COLLECTION);
		observation.setPrimary(true);
		observation.setReference(true);

		figisDao.persist(observation);
		figisDao.remove(observation);
	}

}
