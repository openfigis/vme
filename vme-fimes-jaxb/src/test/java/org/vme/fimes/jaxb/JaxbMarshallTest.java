package org.vme.fimes.jaxb;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class JaxbMarshallTest {

	JaxbMarshall m = new JaxbMarshall();

	@Test
	public void testMarshalToString() {
		FIGISDoc doc = new FIGISDoc();

		String s = m.marshalToString(doc);
		assertTrue(s.startsWith("<?xml version="));
		assertTrue(s.contains("FIGISDoc"));

	}
}
