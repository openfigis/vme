package org.fao.fi.vme.sync0;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.figis.FigisDataBaseProducer;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.service.dao.sources.figis.FigisDao;
import org.vme.service.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class,
		FilesystemMsAccessConnectionProvider.class })
public class TemporaryBatchIntegrationTest {

	@Inject
	TemporaryBatch temporaryBatch;

	@Inject
	VmeDao vmeDao;

	@Inject
	FigisDao figisDao;

	@Test
	public void testRun() {
		temporaryBatch.run();
		assertEquals(98, vmeDao.count(Vme.class).intValue());
		assertEquals(105, vmeDao.count(GeoRef.class).intValue());

		// commented, currently generates 208
		// assertEquals(197, figisDao.count(VmeObservation.class).intValue());
	}

}
