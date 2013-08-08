package org.fao.fi.vme.sync2;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.RefWaterArea;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.test.GeoRefMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

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
		vmeDao.persist(o);

		// perform the logic
		geoRefSync.sync();

		// assume 1 object in source and destination
		assertNrOfObjects(1);

		// test repeatability
		geoRefSync.sync();
		assertNrOfObjects(1);

		RefWaterArea found = (RefWaterArea) figisDao.find(RefWaterArea.class, o.getId());
		assertEquals(GeoRefMock.geographicFeatureID, found.getExternalId());

	}

	private void assertNrOfObjects(int i) {
		assertEquals(i, figisDao.count(RefWaterArea.class).intValue());
		assertEquals(i, vmeDao.count(GeoRef.class).intValue());
	}

}
