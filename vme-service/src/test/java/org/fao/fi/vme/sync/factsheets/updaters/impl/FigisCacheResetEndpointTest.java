package org.fao.fi.vme.sync.factsheets.updaters.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FigisCacheResetEndpointTest {

	@Test
	public void testGetCacheResetEndpoint() {
		FigisCacheResetEndpoint e = new FigisCacheResetEndpoint();
		assertEquals(e.getCacheResetEndpoint(),
				"http://figis02:8282/fiweb/servlet/CacheDeleteFactsheetDomain?domain=vme");
	}

}
