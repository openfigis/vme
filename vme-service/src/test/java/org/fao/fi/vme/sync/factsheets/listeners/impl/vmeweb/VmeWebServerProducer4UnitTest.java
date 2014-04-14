/**
 * (c) 2014 FAO / UN (project: vme-dao)
 */
package org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb;

import static net.jadler.Jadler.port;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

@Alternative
public class VmeWebServerProducer4UnitTest {

	@Produces
	@ApplicationScoped
	public VmeWebServer produceVmeWebSearchCacheClient() {

		String server = "http://localhost:" + port();
		VmeWebServer c = new VmeWebServer();
		c.setServer(server);
		return c;
	}
}