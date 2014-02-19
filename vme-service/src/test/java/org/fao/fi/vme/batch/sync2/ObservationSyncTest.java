package org.fao.fi.vme.batch.sync2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.rule.DomainRule4ObservationXmlId;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.figis.domain.test.RefVmeMock;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.test.FigisDaoTestLogic;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducer.class, FigisDataBaseProducer.class, FigisTestPersistenceUnitConfiguration.class })
public class ObservationSyncTest extends FigisDaoTestLogic {

	int NUMBER_OF_YEARS = 1;

	@Inject
	ObservationSync observationSync;

	@Inject
	FigisDao figisDao;

	@Inject
	VmeDao vmeDao;

	DomainRule4ObservationXmlId rule = new DomainRule4ObservationXmlId();
	MultiLingualStringUtil u = new MultiLingualStringUtil();

	Long id;

	@Before
	public void generateVme() {

		Vme vme = VmeMock.generateVme(NUMBER_OF_YEARS);
		vmeDao.saveVme(vme);

		RefVme refVme = RefVmeMock.create();
		refVme.setId(vme.getId());
		figisDao.persist(refVme);
	}

	/**
	 * 
	 */

	@Test
	public void testSync() {
		assertNrOfObjects(0);
		observationSync.sync();

		assertNrOfObjects(NUMBER_OF_YEARS);

		// // test repeatability
		observationSync.sync();
		observationSync.sync();
		assertNrOfObjects(NUMBER_OF_YEARS);
	}

	@Test
	public void testSyncWithUpdate() {
		observationSync.sync();
		assertNrOfObjects(NUMBER_OF_YEARS);

		assertEquals(1, vmeDao.count(SpecificMeasure.class).intValue());

		List<Vme> vmeList = (List<Vme>) vmeDao.loadObjects(Vme.class);
		for (Vme vme : vmeList) {
			SpecificMeasure specificMeasures = new SpecificMeasure();
			specificMeasures.setVmeSpecificMeasure(u.english("go sado masochistic"));
			specificMeasures.setId(333333333l);
			specificMeasures.setYear(VmeMock.YEAR + 1);
			specificMeasures.setValidityPeriod(ValidityPeriodMock.create());
			vme.getSpecificMeasureList().add(specificMeasures);
			vmeDao.merge(vme);
		}
		observationSync.sync();
		assertNrOfObjects(NUMBER_OF_YEARS);

		// test repeatability
		observationSync.sync();
		assertNrOfObjects(NUMBER_OF_YEARS);
	}

	@Test
	public void testSyncCD_COLLECTION() {
		assertNrOfObjects(0);
		observationSync.sync();
		assertNrOfObjects(NUMBER_OF_YEARS);
		List<?> oss = figisDao.loadObjects(Observation.class);
		for (Object object : oss) {
			Observation o = (Observation) object;
			assertNotNull(o.getCollection());
			assertNotNull(o.getOrder());
			String xmlId = rule.composeId(o.getId(), Figis.EN);
			ObservationXml xml = (ObservationXml) figisDao.find(ObservationXml.class, xmlId);
			assertNotNull(xml);
			assertEquals(o.getId(), xml.getObservation().getId());
		}

	}

	private void assertNrOfObjects(int i) {
		assertEquals(i, figisDao.count(VmeObservation.class).intValue());
		assertEquals(i, figisDao.count(Observation.class).intValue());
		assertEquals(i, figisDao.count(ObservationXml.class).intValue());
	}
};
