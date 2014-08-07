package org.fao.fi.vme.figis;

import javax.inject.Inject;

import org.fao.fi.vme.batch.sync2.SyncBatch2;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, VmePersistenceUnitConfiguration.class,
		FigisPersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class, FigisDataBaseProducer.class })
public class VmeDomainFigisSyncTest {

	@Inject
	SyncBatch2 vmeDomainFigisSync;

	@Test
	public void testSyncFigisVmeXml() {
		vmeDomainFigisSync.syncFigisWithVme();
	}

}