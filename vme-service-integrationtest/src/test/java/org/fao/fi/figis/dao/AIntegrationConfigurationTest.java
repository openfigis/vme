package org.fao.fi.figis.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class AIntegrationConfigurationTest {

	private static final String FIGIS_INT = "jdbc:oracle:thin:@LDVDBWO1:1521:WDV01";
	private static final String VME_INT = "vme_int";
	private static final String FISP = "FISP";

	@Test
	public void testVmeConfig() {
		URL res = this.getClass().getClassLoader().getResource("META-INF/persistence.xml");

		System.out.println(res.toExternalForm());

		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("META-INF/persistence.xml");

		try {
			assertNotNull("No persistence.xml found! Configure it through a Maven Profile", inputStream);
			String text = IOUtils.toString(inputStream, "UTF-8");
			System.out.println(text);

			// make sure that either the integration DB or only H2 is used.
			Pattern p = Pattern.compile("jdbc:h2:mem:db");
			Matcher m = p.matcher(text);
			int count = 0;
			while (m.find()) {
				count += 1;
			}

			assertTrue("no  H2 or no integration DB found", count == 2 || text.contains(FIGIS_INT));
			assertTrue("no  H2 or no integration DB found", count == 2 || text.contains(VME_INT));
			assertFalse(text.contains(FISP));

		} catch (IOException e) {
			fail();
		}
	}

}
