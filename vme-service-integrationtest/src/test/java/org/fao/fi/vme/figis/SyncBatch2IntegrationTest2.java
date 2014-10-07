package org.fao.fi.vme.figis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.batch.sync2.SyncBatch2;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.test.VmeScopeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, VmePersistenceUnitConfiguration.class,
		FigisPersistenceUnitConfiguration.class })
@AdditionalClasses({ FigisDataBaseProducer.class, VmeDataBaseProducerApplicationScope.class })
public class SyncBatch2IntegrationTest2 {

	@Inject
	private SyncBatch2 syncBatch2;

	@Inject
	private VmeDao vmeDao;

	@Inject
	private FigisDao figisDao;

	@After
	public void after() {
		clean(Vme.class);
		clean(Rfmo.class);
		cleanFigis(ObservationXml.class);
		// cleanFigis(Observation.class);
		cleanFigis(VmeObservation.class);
	}

	@Before
	public void before() {
		clean(Vme.class);
		clean(Rfmo.class);
		vmeDao.persist(VmeScopeMock.create());

	}

	private void clean(Class<?> class1) {
		List<?> oList = vmeDao.loadObjects(class1);
		for (Object object : oList) {
			vmeDao.remove(object);
		}
	}

	private void cleanFigis(Class<?> class1) {
		List<?> oList = figisDao.loadObjects(class1);
		for (Object object : oList) {
			figisDao.remove(object);
		}
	}

	@Test
	public void testSyncFigisWithVmePrimaryRule() {
		Vme vme = VmeMock.create();
		Rfmo rfmo = new Rfmo();
		rfmo.setId("CCAMLR");
		vmeDao.persist(rfmo);

		vme.setRfmo(rfmo);
		vmeDao.persist(vme);

		syncBatch2.syncFigisWithVme();

		List<VmeObservation> voList = figisDao.findVmeObservationByVme(vme.getId());

		List<VmeObservation> nonPrimary = voList.subList(0, voList.size() - 2);
		for (VmeObservation vo : nonPrimary) {
			Observation o = (Observation) figisDao.find(Observation.class, vo.getId().getObservationId());
			assertFalse(o.isPrimary());
		}
		VmeObservation vo = (VmeObservation) voList.get(voList.size() - 1);
		Observation o = (Observation) figisDao.find(Observation.class, vo.getId().getObservationId());
		assertTrue(o.isPrimary());

	}
}
