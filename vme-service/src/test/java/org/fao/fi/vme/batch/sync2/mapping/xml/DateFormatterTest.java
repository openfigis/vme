package org.fao.fi.vme.batch.sync2.mapping.xml;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

public class DateFormatterTest {

	DateFormatter df = new DateFormatter();

	@Test
	public void testFormatDateDate() {
		System.out.println(df.format(Calendar.getInstance().getTime(), Calendar.getInstance().getTime()));
		assertEquals(25, df.format(Calendar.getInstance().getTime(), Calendar.getInstance().getTime()).length());
	}

}
