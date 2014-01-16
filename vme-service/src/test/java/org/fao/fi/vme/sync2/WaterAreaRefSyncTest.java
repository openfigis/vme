package org.fao.fi.vme.sync2;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.figis.domain.RefWaterArea;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.test.GeoRefMock;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.figis.FigisDataBaseProducer;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.service.dao.sources.figis.FigisDao;
import org.vme.service.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class })
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
