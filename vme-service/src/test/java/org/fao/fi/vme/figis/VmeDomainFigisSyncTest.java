package org.fao.fi.vme.figis;

import javax.inject.Inject;

import org.fao.fi.vme.batch.sync2.SyncBatch2;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.figis.FigisDataBaseProducer;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class })
public class VmeDomainFigisSyncTest {

	@Inject
	SyncBatch2 vmeDomainFigisSync;

	@Test
	public void testSyncFigisVmeXml() {
		vmeDomainFigisSync.syncFigisWithVme();
	}

}