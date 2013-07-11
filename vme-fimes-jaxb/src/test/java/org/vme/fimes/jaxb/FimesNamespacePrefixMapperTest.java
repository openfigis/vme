package org.vme.fimes.jaxb;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FimesNamespacePrefixMapperTest extends FimesNamespacePrefixMapper {

	@Test
	public void testGetPreferredPrefixStringStringBoolean() {
		FimesNamespacePrefixMapper m = new FimesNamespacePrefixMapper();

		assertEquals("aida", m.getPreferredPrefix("http://www.idmlinitiative.org/resources/dtds/AIDA22.xsd", "", false));
	}

}
