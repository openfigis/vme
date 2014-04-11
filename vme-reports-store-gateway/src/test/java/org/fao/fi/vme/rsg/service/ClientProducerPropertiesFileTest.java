package org.fao.fi.vme.rsg.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClientProducerPropertiesFileTest {

	@Test
	public void testProduceVmeWebSearchCacheClient() {
		ClientProducerPropertiesFile p = new ClientProducerPropertiesFile();
		assertNotNull(p.produceVmeWebSearchCacheClient());
		System.out.println(p.produceVmeWebSearchCacheClient().getServer().length());
		assertTrue(p.produceVmeWebSearchCacheClient().getServer().length() > 0);

	}
}
