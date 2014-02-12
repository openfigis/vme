package org.fao.fi.vme.batch.sync2;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.figis.domain.RefWaterArea;
import org.fao.fi.vme.batch.sync2.GeoRefSync;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;

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
		Vme vme = VmeMock.create();
		vmeDao.persist(vme);
		int x = figisDao.loadObjects(RefWaterArea.class).size();
		geoRefSync.sync();
		assertEquals(x + vme.getGeoRefList().size(), figisDao.loadObjects(RefWaterArea.class).size());
		geoRefSync.sync();
		assertEquals(x + vme.getGeoRefList().size(), figisDao.loadObjects(RefWaterArea.class).size());
	}
}
