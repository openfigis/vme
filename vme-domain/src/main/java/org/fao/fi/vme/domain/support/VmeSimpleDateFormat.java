package org.fao.fi.vme.domain.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.fao.fi.vme.VmeException;

/**
 * The Vme simple date formatter. In order to standardise on date processing in
 * all of the Vme software components.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeSimpleDateFormat {

	private SimpleDateFormat sf = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);

	/**
	 * Date format is ddMMyyyy
	 * 
	 * 
	 * @param startDate
	 * @return
	 */
	public Date parse(String date) {
		try {
			return sf.parse(date);
		} catch (ParseException e) {
			throw new VmeException(e);
		}
	}

}
