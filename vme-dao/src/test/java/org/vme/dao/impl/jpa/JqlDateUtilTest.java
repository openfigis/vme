package org.vme.dao.impl.jpa;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.fao.fi.vme.domain.support.ValidityPeriodUtil;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

public class JqlDateUtilTest {

	JqlDateUtil u = new JqlDateUtil();

	@Test
	public void testDate2JqlString() throws ParseException {

		DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY/MM/dd");

		String dateString = "2010/12/02";

		assertEquals(dateString, u.date2JqlString(fmt.parseDateTime(dateString).toDate()));

	}

	@Test
	public void testDate2JqlStringWithVu() throws ParseException {
		ValidityPeriodUtil vu = new ValidityPeriodUtil();
		Date date = vu.beginYear2BeginDate(2010);
		String dateString = "2010/01/01";
		System.out.println(u.date2JqlString(date));
		assertEquals(dateString, u.date2JqlString(date));

	}
}
