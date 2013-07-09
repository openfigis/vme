package org.fao.fi.vme.sync2;

import javax.inject.Inject;

import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class })
public class SyncBatch2Test {

	@Inject
	private SyncBatch2 syncBatch2;

	@Test
	public void testSyncFigisWithVme() {
		syncBatch2.syncFigisWithVme();
	}

}
