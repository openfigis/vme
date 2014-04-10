package org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb;

import static net.jadler.Jadler.closeJadler;
import static net.jadler.Jadler.initJadler;
import static net.jadler.Jadler.onRequest;
import static net.jadler.Jadler.port;
//import static net.jadler.Jadler.closeJadler;
//import static net.jadler.Jadler.initJadler;
//import static net.jadler.Jadler.onRequest;
//import static net.jadler.Jadler.port;
//import static net.jadler.Jadler.verifyThatRequest;
//import static org.hamcrest.Matchers.notNullValue;
import static net.jadler.Jadler.verifyThatRequest;

import org.fao.fi.vme.domain.model.Vme;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * http://localhost:8081/vme-web/webservice/cache-delete
 * 
 * @author Erik van Ingen
 * 
 */

public class VmeWebSearchCacheTest {

	private VmeWebSearchCacheClient c = new VmeWebSearchCacheClient();
	// private HttpClient client;

	private String SERVER = "http://localhost:8081";
	private String RESOURCE = "/vme-web/webservice/cache-delete";

	/*
	 * Tests the havingBody methods.
	 */
	// @Test
	public void run() throws Exception {
		String server = SERVER;
		c.setServer(server);
		c.setResource(RESOURCE);
		c.VMEChanged(new Vme());
	}

	@Test
	public void testVMEChangedHavingBody() throws Exception {
		String server = "http://localhost:" + port();
		c.setServer(server);
		c.setResource(RESOURCE);

		onRequest().havingMethodEqualTo("GET").havingPathEqualTo(RESOURCE).respond().withStatus(200)
				.withBody(CacheDeleteHandler.MESSAGE);

		c.VMEChanged(new Vme());
		long endd = System.currentTimeMillis() + 100;
		while (System.currentTimeMillis() < endd) {
			// Do nothing here, let just some time pass, because the request is
			// asynchronic.
		}
		verifyThatRequest().havingMethodEqualTo("GET").havingPathEqualTo(RESOURCE).receivedOnce();

	}

	@Before
	public void setUp() {
		initJadler();
	}

	@After
	public void tearDown() {
		closeJadler();
	}

}
