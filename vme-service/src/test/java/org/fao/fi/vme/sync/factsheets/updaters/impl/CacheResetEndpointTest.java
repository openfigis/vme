package org.fao.fi.vme.sync.factsheets.updaters.impl;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
public class CacheResetEndpointTest {

	@Inject
	FigisCacheResetEndpoint figisCacheResetEndpoint;

	@Test
	public void testGetCacheResetEndpoint() {
		assertNotNull(figisCacheResetEndpoint.getCacheResetEndpoint());
	}

}
