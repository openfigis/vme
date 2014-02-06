package org.fao.fi.vme.sync2.mapping;

import static org.junit.Assert.assertNotNull;

import org.fao.fi.figis.devcon.BiblioEntry;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.junit.Test;

public class BiblioEntryFromInformationSourceTest {

	BiblioEntryFromInformationSource b = new BiblioEntryFromInformationSource();

	@Test
	public void testTransform() {

		BiblioEntry be = b.transform(InformationSourceMock.create());
		assertNotNull(be);
	}
}
