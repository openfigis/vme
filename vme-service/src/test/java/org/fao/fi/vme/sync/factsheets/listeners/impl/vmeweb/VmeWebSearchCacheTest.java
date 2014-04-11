package org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb;

import static net.jadler.Jadler.closeJadler;
import static net.jadler.Jadler.onRequest;
//import static net.jadler.Jadler.closeJadler;
//import static net.jadler.Jadler.initJadler;
//import static net.jadler.Jadler.onRequest;
//import static net.jadler.Jadler.port;
//import static net.jadler.Jadler.verifyThatRequest;
//import static org.hamcrest.Matchers.notNullValue;
import static net.jadler.Jadler.verifyThatRequest;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * http://localhost:8081/vme-web/webservice/cache-delete
 * 
 * @author Erik van Ingen
 * 
 */
@RunWith(CdiRunner.class)
@AdditionalClasses({ VmeWebSearchCacheClient.class })
@ActivatedAlternatives({ ClientProducerUnitTest.class })
public class VmeWebSearchCacheTest {

	@Inject
	private VmeWebSearchCacheClient c;

	static String RESOURCE = "/vme-web/webservice/cache-delete";

	@Inject
	@Any
	Event<VmeModelChange> aVmeModelChange;

	/**
	 * This is to test the working of CDI events with respect to the
	 * VmeWebSearchCacheClient
	 * 
	 * @TODO Somehow I do not get it to work. The VmeWebSearchCacheClient will
	 *       not be produced by the ClientProducerUnitTest, which might be a bug
	 *       of cdi-unit
	 * 
	 * @throws Exception
	 */
	// @Test
	public void testLogic() throws Exception {
		aVmeModelChange.fire(new VmeModelChange());
		delegate();
	}

	/**
	 * This is a direct test, without using CDI events.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testVMEChangedHavingBody() throws Exception {
		c.process(new VmeModelChange());
		delegate();
	}

	private void delegate() throws Exception {
		Thread.sleep(100);
		verifyThatRequest().havingMethodEqualTo("GET").havingPathEqualTo(RESOURCE).receivedOnce();
	}

	@Before
	public void setUp() {
		// initJadler();
		// String server = "http://localhost:" + port();
		// c.setServer(server);
		// c.setResource(RESOURCE);

		onRequest().havingMethodEqualTo("GET").havingPathEqualTo(RESOURCE).respond().withStatus(200)
				.withBody(CacheDeleteHandler.MESSAGE);
	}

	@After
	public void tearDown() {
		closeJadler();
	}

}
