package org.fao.fi.vme.test;

import org.fao.fi.vme.domain.ValidityPeriod;

public class ValidityPeriodMock {

	public final static int BEGIN_YEAR = 2000;
	public final static int END_YEAR = 2003;

	public static ValidityPeriod create() {
		ValidityPeriod vp = new ValidityPeriod();
		vp.setBeginYear(BEGIN_YEAR);
		vp.setEndYear(END_YEAR);
		return vp;
	};

}
