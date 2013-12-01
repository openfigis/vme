package org.fao.fi.vme.msaccess.component;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FilesystemMsAccessConnectionProvider.class })
public class MsAccessConnectionProviderTest {

	@Inject private MsAccessConnectionProvider p;

	@Test
	public void testGetConnecton() {
		assertNotNull(p.getConnection());
	}

}
