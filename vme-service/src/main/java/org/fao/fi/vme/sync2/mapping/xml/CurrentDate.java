package org.fao.fi.vme.sync2.mapping.xml;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CurrentDate {

	public String getCurrentDateYyyyMmDd() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}
}
