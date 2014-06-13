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

		assertEquals("2000-01-01", f.createUiString(f.parse("01012000")));
	}
}
