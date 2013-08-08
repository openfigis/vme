package org.fao.fi.vme.msaccess.component;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class MsAccessConnectionProviderTest {

	MsAccessConnectionProvider p = new MsAccessConnectionProvider();

	@Test
	public void testGetConnecton() {
		assertNotNull(p.getConnecton());

	}

}
