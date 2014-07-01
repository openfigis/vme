package org.fao.fi.vme.batch.sync2;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.figis.domain.RefWaterArea;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.test.GeoRefMock;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.fao.fi.vme.domain.test.VmeTypeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class,
		FigisDataBaseProducer.class, FigisTestPersistenceUnitConfiguration.class })
public class WaterAreaRefSyncTest {

	@Inject
	GeoRefSync geoRefSync;

	@Inject
	VmeDao vmeDao;

	@Inject
	FigisDao figisDao;

	/**
	 * 
	 */
	@Test
	public void testSync() {
		// assume empty DB
		assertNrOfObjects(0);
		GeoRef o = GeoRefMock.create();
		vmeDao.persist(VmeTypeMock.create());
		vmeDao.persist(o.getVme());
		vmeDao.persist(o);

		// perform the logic
		geoRefSync.sync();

		// assume 1 object in source and destination
		assertNrOfObjects(ValidityPeriodMock.YEARS);

		// test repeatability
		geoRefSync.sync();
		assertNrOfObjects(ValidityPeriodMock.YEARS);

		Long id = (long) FigisDao.START_WATER_AREA_REF;

		RefWaterArea found = (RefWaterArea) figisDao.find(RefWaterArea.class, id);
		assertEquals(GeoRefMock.geographicFeatureID, found.getExternalId());

	}

	private void assertNrOfObjects(int i) {
		assertEquals(i, figisDao.count(RefWaterArea.class).intValue());
		assertEquals(i, vmeDao.count(GeoRef.class).intValue());
	}

}
