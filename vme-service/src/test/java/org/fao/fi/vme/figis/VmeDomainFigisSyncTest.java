package org.fao.fi.vme.figis;

import javax.inject.Inject;

import org.fao.fi.vme.batch.sync2.SyncBatch2;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, VmeTestPersistenceUnitConfiguration.class,
		VmeDataBaseProducerApplicationScope.class, FigisDataBaseProducer.class,
		FigisTestPersistenceUnitConfiguration.class })
public class VmeDomainFigisSyncTest {

	@Inject
	SyncBatch2 vmeDomainFigisSync;

	@Test
	public void testSyncFigisVmeXml() {
		vmeDomainFigisSync.syncFigisWithVme();
	}

}