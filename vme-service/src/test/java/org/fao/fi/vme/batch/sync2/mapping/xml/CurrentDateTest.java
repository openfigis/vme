package org.fao.fi.vme.batch.sync2.mapping.xml;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CurrentDateTest {

	CurrentDate d = new CurrentDate();

	@Test
	public void testGetDate() {
		assertTrue(Integer.decode(d.getCurrentDateYyyyMmDd().substring(0, 4)) > 0);

	}
}
