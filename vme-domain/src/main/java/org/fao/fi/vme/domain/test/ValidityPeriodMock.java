package org.fao.fi.vme.domain.test;

import org.fao.fi.vme.domain.model.ValidityPeriod;

public class ValidityPeriodMock {

	public final static int BEGIN_YEAR = 2000;
	public final static int END_YEAR = 2003;
	public final static int YEARS = END_YEAR - BEGIN_YEAR + 1;

	public static ValidityPeriod create() {
		ValidityPeriod vp = new ValidityPeriod();
		vp.setBeginYear(BEGIN_YEAR);
		vp.setEndYear(END_YEAR);
		return vp;
	};

	public static ValidityPeriod create(Integer beginYear, Integer endYear) {
		ValidityPeriod vp = new ValidityPeriod();
		vp.setBeginYear(beginYear);
		vp.setEndYear(endYear);
		return vp;
	}

	public static int getNumberOfYearInclusive() {
		return END_YEAR - BEGIN_YEAR + 1;
	}

}
