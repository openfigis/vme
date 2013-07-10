package org.fao.fi.figis.dao;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.VmeObservationPk;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.test.FigisDaoTestLogic;
import org.fao.fi.vme.test.RefVmeMock;
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
	public void testSyncVmeObservationDomain() {
		RefVme refVme = RefVmeMock.create();
		figisDao.persist(refVme);
		int count[] = count();
		VmeObservationDomain vod = createVmeObservationDomain();
		vod.setRefVme(refVme);
		figisDao.syncVmeObservationDomain(vod);
		checkCount(count, 1);
		figisDao.syncVmeObservationDomain(vod);
		checkCount(count, 1);
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
		// figisDao.remove(figisDao.find(VmeObservation.class, vo.getObservationId()));
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
