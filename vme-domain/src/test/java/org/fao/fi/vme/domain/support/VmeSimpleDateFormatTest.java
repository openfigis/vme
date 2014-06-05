package org.fao.fi.vme.domain.support;

import org.junit.Test;

public class VmeSimpleDateFormatTest {

	private VmeSimpleDateFormat f = new VmeSimpleDateFormat();

	@Test
	public void testParse() {
		f.parse("01012000");
	}
}
