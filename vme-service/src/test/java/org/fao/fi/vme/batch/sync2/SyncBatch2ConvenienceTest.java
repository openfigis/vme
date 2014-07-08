package org.fao.fi.vme.batch.sync2;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;

/**
 * This test is for convenience purposese in order to run the batch. In this
 * case the maven profile on the vme-configuration needs to be specified with
 * the correct environment, for instance vme=fiqa.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, VmePersistenceUnitConfiguration.class,
		VmeDataBaseProducerApplicationScope.class, FigisDataBaseProducer.class, FigisPersistenceUnitConfiguration.class })
public class SyncBatch2ConvenienceTest {

	@Inject
	private SyncBatch2 syncBatch2;

	@Test
	public void testSyncFigisWithVmePrimaryRule() {

		syncBatch2.syncFigisWithVme();

	}
}
