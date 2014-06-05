package org.fao.fi.vme.domain.support;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class ValidityPeriodUtilTest {

	private ValidityPeriodUtil u = new ValidityPeriodUtil();
	private VmeSimpleDateFormat f = new VmeSimpleDateFormat();

	@Test
	public void testBeginYear2BeginDate() {
		Date b = u.beginYear2BeginDate(2000);
		assertEquals(0, b.compareTo(f.parse("01012000")));

	}

	@Test
	public void testEndYear2endDate() {
		Date b = u.endYear2endDate(2001);
		assertEquals(0, b.compareTo(f.parse("31122001")));
	}

}
