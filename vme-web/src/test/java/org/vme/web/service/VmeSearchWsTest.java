package org.vme.web.service;

import static org.junit.Assert.fail;

import org.junit.Test;

public class VmeSearchWsTest {

	@Test
	public void testFind() throws Exception {

		VmeSearchWs ws = new VmeSearchWs();
		try {
			ws.find("vme", "2010)", null, null, null);
		} catch (NumberFormatException e) {
			fail();
		} catch (NullPointerException e) {
			// because there is no CDI
		}
		try {
			ws.find("vme", "2010", null, null, null);
		} catch (NumberFormatException e) {
			fail();
		} catch (NullPointerException e) {
			// because there is no CDI
		}

	}

}
