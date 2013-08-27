package org.fao.fi.vme.sync2.mapping.xml;

import java.util.Calendar;

public class CurrentDate {

	private DateFormatter df = new DateFormatter();

	public String getCurrentDateYyyyMmDd() {
		Calendar cal = Calendar.getInstance();
		return df.format(cal.getTime());
	}
}
