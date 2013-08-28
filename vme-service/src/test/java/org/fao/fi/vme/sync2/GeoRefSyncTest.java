package org.fao.fi.vme.sync2;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.RefWaterArea;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class })
public class GeoRefSyncTest {

	@Inject
	FigisDao figisDao;

	@Inject
	VmeDao vmeDao;

	@Inject
	GeoRefSync geoRefSync;

	@Test
	public void testSync() {
		String geographicFeatureID = "VME_X";

		Vme vme = VmeMock.create();
		GeoRef geoRef = vme.getGeoRefList().get(0);
		geoRef.setGeographicFeatureID(geographicFeatureID);
		vmeDao.persist(vme);
		vmeDao.persist(geoRef);
		int x = figisDao.loadObjects(RefWaterArea.class).size();
		geoRefSync.sync();
		assertEquals(++x, figisDao.loadObjects(RefWaterArea.class).size());
		geoRefSync.sync();
		assertEquals(x, figisDao.loadObjects(RefWaterArea.class).size());
	}
}
