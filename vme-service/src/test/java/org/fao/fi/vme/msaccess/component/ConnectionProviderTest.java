package org.fao.fi.vme.msaccess.component;

import org.fao.fi.vme.msaccess.component.ConnectionProvider;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConnectionProviderTest {

	ConnectionProvider p = new ConnectionProvider();

	@Test
	public void testGetConnecton() {
		assertNotNull(p.getConnecton());

	}

}
