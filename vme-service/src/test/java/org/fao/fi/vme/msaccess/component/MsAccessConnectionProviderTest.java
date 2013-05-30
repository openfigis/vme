package org.fao.fi.vme.msaccess.component;

import org.fao.fi.vme.msaccess.component.MsAccessConnectionProvider;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MsAccessConnectionProviderTest {

	MsAccessConnectionProvider p = new MsAccessConnectionProvider();

	@Test
	public void testGetConnecton() {
		assertNotNull(p.getConnecton());

	}

}
