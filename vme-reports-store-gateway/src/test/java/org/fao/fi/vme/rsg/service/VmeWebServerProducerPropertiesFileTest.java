package org.fao.fi.vme.rsg.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb.VmeWebServer;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeWebServerProducerPropertiesFile.class })
public class VmeWebServerProducerPropertiesFileTest {

	@Inject
	VmeWebServer c;

	@Test
	public void testProduceVmeWebSearchCacheClientCdi() {
		assertNotNull(c);

		// there is yet no property defined for vme.web.server in
		// vme-reports-store-gateway
		assertTrue(c.getServer().contains("http://"));

	}

	@Test
	public void testProduceVmeWebSearchCacheClientNative() {
		VmeWebServerProducerPropertiesFile p = new VmeWebServerProducerPropertiesFile();
		assertNotNull(p.produceVmeWebSearchCacheClient());
		assertTrue(p.produceVmeWebSearchCacheClient().getServer().length() > 0);

	}
}
