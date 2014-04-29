package org.fao.vme.rsg.web.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.fao.fi.vme.sync.factsheets.updaters.impl.FigisCacheResetEndpoint;
import org.junit.Test;

public class VmeFactsheetCacheDeleteTest {

	/**
	 * This test is just a convenience test in order to check whether after this
	 * test, the directory \FIGIS\fi\webapps\figis\DiskCache\vme is really empty
	 * or not.
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	@Test
	public void test() throws MalformedURLException, IOException {
		FigisCacheResetEndpoint e = new FigisCacheResetEndpoint();
		new URL(e.getCacheResetEndpoint()).openStream();
	}
}
