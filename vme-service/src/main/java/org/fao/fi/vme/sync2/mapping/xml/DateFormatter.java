package org.fao.fi.vme.sync2.mapping.xml;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

	private DateFormat dateFormatFormal = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat dateFormatMonthIn3Char = new SimpleDateFormat("dd MMM yyyy");

	public String format(Date date) {

		return dateFormatFormal.format(date);
	}

	public String format(Date fromDate, Date toDate) {
		return dateFormatMonthIn3Char.format(fromDate) + " - " + dateFormatMonthIn3Char.format(toDate);
	}
}
