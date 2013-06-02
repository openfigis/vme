package org.fao.fi.vme.figis;

import javax.inject.Inject;

import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.fao.fi.vme.msaccess.VmeAccessDbImport;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class })
public class VmeDomainFigisSyncTest {

	@Inject
	VmeDomainFigisSync vmeDomainFigisSync;

	@Inject
	VmeAccessDbImport i;

	@Test
	public void testSyncFigisVmeXml() {
		vmeDomainFigisSync.syncFigisWithVme();
	}

}