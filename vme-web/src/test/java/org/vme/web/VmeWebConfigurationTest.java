package org.vme.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class VmeWebConfigurationTest {

	@Test
	public void testVmeConfig() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("META-INF/persistence.xml");
		try {
			assertNotNull("No persistence.xml found! Configure it through a Maven Profile", inputStream);
			String text = IOUtils.toString(inputStream, "UTF-8");
			System.out.println(text);
//			assertFalse(text.contains("oracle.jdbc.driver.OracleDriver"));
//			assertTrue("Expecting only to work with and find H2 here for testing", text.contains("org.h2.Driver"));
		} catch (IOException e) {
			fail();
		}
	}

}
