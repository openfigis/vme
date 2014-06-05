package org.fao.fi.vme.domain.support;

import java.util.Date;

import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.joda.time.DateTime;

/**
 * There was a time where VmePeriod was having beginYear and endYear. Then it
 * was changed to beginDate and endDate and this util help to convert from one
 * to the other.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class ValidityPeriodUtil {

	public Date beginYear2BeginDate(int beginYear) {
		DateTime dt = new DateTime(beginYear, 1, 1, 0, 0, 0, 0);
		return dt.toDate();

	}

	public Date endYear2endDate(int endYear) {
		DateTime dt = new DateTime(endYear, 12, 31, 0, 0, 0, 0);
		return dt.toDate();
	}

	public int getEndYear(ValidityPeriod vp) {
		return new DateTime(vp.getEndDate()).getYear();
	}

	public int getBeginYear(ValidityPeriod vp) {
		return new DateTime(vp.getBeginDate()).getYear();
	}

}
