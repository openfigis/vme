package org.fao.fi.vme.batch.sync2;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Vme;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

/**
 * This test is for convenience purposese in order to run the batch. In this case the maven profile on the
 * vme-configuration needs to be specified with the correct environment, for instance vme=fiqa.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, VmePersistenceUnitConfiguration.class,
		FigisPersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class, FigisDataBaseProducer.class })
public class SyncBatch2ConvenienceTest {

	@Inject
	private SyncBatch2 syncBatch2;

	@Inject
	private VmeDao dao;

	@Test
	public void syncFigisWithVmeAll() {

		syncBatch2.syncFigisWithVme();

	}

	@Test
	@Ignore
	public void syncFigisWithVme1() {

		Vme vme = dao.findVme(24755l);
		syncBatch2.syncFigisWithVme(vme);

	}

}
