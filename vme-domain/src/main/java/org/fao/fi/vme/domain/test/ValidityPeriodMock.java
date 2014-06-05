package org.fao.fi.vme.domain.test;

import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.support.ValidityPeriodUtil;

public class ValidityPeriodMock {

	public final static int BEGIN_YEAR = 2000;
	public final static int END_YEAR = 2003;
	public final static int YEARS = END_YEAR - BEGIN_YEAR + 1;

	private static ValidityPeriodUtil u = new ValidityPeriodUtil();

	public static ValidityPeriod create() {
		ValidityPeriod vp = new ValidityPeriod();
		vp.setBeginDate(u.beginYear2BeginDate(BEGIN_YEAR));
		vp.setEndDate(u.endYear2endDate(END_YEAR));
		return vp;
	};

	public static ValidityPeriod create(Integer beginYear, Integer endYear) {
		ValidityPeriod vp = new ValidityPeriod();
		vp.setBeginDate(u.beginYear2BeginDate(beginYear));
		vp.setEndDate(u.endYear2endDate(endYear));
		return vp;
	}

	public static int getNumberOfDateInclusive() {
		return END_YEAR - BEGIN_YEAR + 1;
	}

}
