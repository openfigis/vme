package org.vme.dao.impl.jpa;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JqlDateUtil {

	/**
	 * "yyyy/mm/dd hh:mm"
	 * 
	 * @param date
	 * @return
	 */
	public String date2JqlString(Date date) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY/MM/dd");
		DateTime dt = new DateTime(date);
		return dt.toString(fmt);
	}
}
