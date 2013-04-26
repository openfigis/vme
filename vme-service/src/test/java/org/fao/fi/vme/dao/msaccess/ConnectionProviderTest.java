package org.fao.fi.vme.dao.msaccess;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConnectionProviderTest {

	ConnectionProvider p = new ConnectionProvider();

	@Test
	public void testGetConnecton() {
		assertNotNull(p.getConnecton());

	}

}
