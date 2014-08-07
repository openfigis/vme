package org.fao.fi.vme.sync.factsheets.updaters.impl;

import static net.jadler.Jadler.closeJadler;
import static net.jadler.Jadler.initJadler;
import static net.jadler.Jadler.onRequest;
import static net.jadler.Jadler.port;
import static net.jadler.Jadler.verifyThatRequest;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, FigisFactsheetUpdater.class,
		FigisTestPersistenceUnitConfiguration.class, VmeTestPersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class, FigisDataBaseProducer.class })
public class FigisFactsheetUpdaterTest {

	private static String RESPONSE = "FIGIS_FACTSHEET_DOMAIN_CACHE_DELETED_SUCCESS";

	@Inject
	FigisFactsheetUpdater figisFactsheetUpdater = new FigisFactsheetUpdater();

	@Inject
	FigisCacheResetEndpoint figisCacheResetEndpoint;

	@Before
	public void setUp() {
		initJadler();
		figisCacheResetEndpoint.setCacheResetServer("http://localhost:" + port());
		onRequest()
				.havingMethodEqualTo("GET")
				.havingPathEqualTo(figisCacheResetEndpoint.getCacheResetResource())
				.havingParameterEqualTo(figisCacheResetEndpoint.getCacheResetParameterName(),
						figisCacheResetEndpoint.getCacheResetParameterValue()).respond().withStatus(200)
				.withBody(RESPONSE);
	}

	@After
	public void tearDown() {
		closeJadler();
	}

	@Test
	public void testUpdateCache() throws Exception {
		figisFactsheetUpdater.updateCache(2l);
		Thread.sleep(100);
		verifyThatRequest().havingMethodEqualTo("GET")
				.havingPathEqualTo(figisCacheResetEndpoint.getCacheResetResource()).receivedOnce();
	}

}
