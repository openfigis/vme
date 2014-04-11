/**
 * (c) 2014 FAO / UN (project: vme-dao)
 */
package org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb;

import static net.jadler.Jadler.initJadler;
import static net.jadler.Jadler.port;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

/**
 * Place your class / interface description here.
 * 
 * History:
 * 
 * ------------- --------------- ----------------------- Date Author Comment
 * ------------- --------------- ----------------------- 19 Feb 2014 Fiorellato
 * Creation.
 * 
 * @version 1.0
 * @since 19 Feb 2014
 */
@Alternative
public class ClientProducerUnitTest {

	@Produces
	public VmeWebSearchCacheClient produceVmeWebSearchCacheClient() {
		initJadler();
		String server = "http://localhost:" + port();
		VmeWebSearchCacheClient c = new VmeWebSearchCacheClient();
		c.setServer(server);
		return c;
	}
}