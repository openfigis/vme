package org.fao.fi.figis.dao;

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

	private static final String INT_DB = "jdbc:oracle:thin:@LDVDBWO1:1521:WDV01";

	@Test
	public void testVmeConfig() {
		URL res = this.getClass().getClassLoader().getResource("META-INF/persistence.xml");

		System.out.println(res.toExternalForm());

		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("META-INF/persistence.xml");

		try {
			assertNotNull("No persistence.xml found! Configure it through a Maven Profile", inputStream);
			String text = IOUtils.toString(inputStream, "UTF-8");
			System.out.println(text);

			// make sure that the devel DB is never used for integration testing

			// Intervention Erik van Ingen. Now temporarily the devel DB is used
			// also for integration testing.

			// assertFalse("devel DB should not be used for integration testing",
			// text.contains("figis>jdbc:oracle:thin:@LDVDBWO1:1521:WDV01"));

			// make sure that either the integration DB or only H2 is used.
			Pattern p = Pattern.compile("jdbc:h2:mem:db2;INIT=create schema IF NOT EXISTS FIGIS");
			Matcher m = p.matcher(text);
			int count = 0;
			while (m.find()) {
				count += 1;
			}

			assertTrue("no 2 H2 or no integration DB found", count == 2 || text.contains(INT_DB));

		} catch (IOException e) {
			fail();
		}
	}

}
