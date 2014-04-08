package org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb;

import static org.mockserver.integration.ClientAndProxy.startClientAndProxy;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.util.concurrent.TimeUnit;

import org.fao.fi.vme.domain.model.Vme;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndProxy;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Delay;

public class VmeWebSearchCacheTest {

	VmeWebSearchCache c = new VmeWebSearchCache();

	private ClientAndProxy proxy;
	private ClientAndServer mockServer;

	@Before
	public void startProxy() {
		mockServer = startClientAndServer(8080);

		new MockServerClient("localhost", 8080).when(request().withMethod("GET").withPath("/login")

		).respond(
				response().withBody("{ message: 'incorrect username and password combination' }").withDelay(
						new Delay(TimeUnit.SECONDS, 1)));

		proxy = startClientAndProxy(9090);
	}

	@After
	public void stopProxy() {
		proxy.stop();
		mockServer.stop();
	}

	@Test
	public void testVMEChanged() throws Exception {
		// TODO finalize this test

		c.VMEChanged(new Vme());

	}

}
