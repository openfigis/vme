package org.fao.fi.vme.domain.support;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VmeSimpleDateFormatTest {

	private VmeSimpleDateFormat f = new VmeSimpleDateFormat();

	@Test
	public void testParse() {
		f.parse("01012000");
	}

	@Test
	public void testCreateUiString() {

		assertEquals("01 Jan 2000", f.createUiString(f.parse("01012000")));
	}
}
