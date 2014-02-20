package org.fao.fi.figis.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class AIntegrationConfigurationTest {

	@Test
	public void testVmeConfig() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("META-INF/persistence.xml");

		try {
			assertNotNull("No persistence.xml found! Configure it through a Maven Profile", inputStream);
			String text = IOUtils.toString(inputStream, "UTF-8");
			System.out.println(text);

			// make sure that the devel DB is never used for integration testing
			assertFalse("devel DB should not be used for integration testing",
					text.contains("figis>jdbc:oracle:thin:@LDVDBWO1:1521:WDV01"));

			// make sure that either the integration DB or only H2 is used.
			Pattern p = Pattern.compile("jdbc:h2:mem:db2;INIT=create schema IF NOT EXISTS FIGIS");
			Matcher m = p.matcher(text);
			int count = 0;
			while (m.find()) {
				count += 1;
			}

			assertTrue("no 2 H2 or no integration DB found",
					count == 2 || text.contains("jdbc:oracle:thin:@ldvdbs01:1521:FIDEV1"));

		} catch (IOException e) {
			fail();
		}
	}

}
