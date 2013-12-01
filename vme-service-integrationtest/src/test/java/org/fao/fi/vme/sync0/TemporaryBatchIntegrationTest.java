package org.fao.fi.vme.sync0;

import javax.inject.Inject;

import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.figis.FigisDataBaseProducer;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class, FilesystemMsAccessConnectionProvider.class })
public class TemporaryBatchIntegrationTest {

	@Inject
	TemporaryBatch temporaryBatch;

	@Test
	public void testRun() {
		temporaryBatch.run();
	}
}
