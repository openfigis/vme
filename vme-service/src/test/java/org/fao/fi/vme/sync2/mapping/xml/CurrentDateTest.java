package org.fao.fi.vme.sync2.mapping.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CurrentDateTest {

	CurrentDate d = new CurrentDate();

	@Test
	public void testGetDate() {
		assertTrue(Integer.decode(d.getCurrentDateYyyyMmDd().substring(0, 4)) > 0);
		assertTrue(Integer.decode(d.getCurrentDateYyyyMmDd().substring(6, 7)) > 0);
	//	assertTrue(Integer.decode(d.getCurrentDateYyyyMmDd().substring(9, 10)) > 0);
		assertEquals(10, d.getCurrentDateYyyyMmDd().length());

	}
}
