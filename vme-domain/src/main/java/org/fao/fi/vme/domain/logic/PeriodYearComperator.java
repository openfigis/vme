package org.fao.fi.vme.domain.logic;

import java.util.Comparator;

import org.fao.fi.vme.domain.interfacee.PeriodYear;

public class PeriodYearComperator implements Comparator<PeriodYear> {

	@Override
	public int compare(PeriodYear arg0, PeriodYear arg1) {
		// prevailing is the validityPeriod
		int result = arg0.getValidityPeriod().compareTo(arg1.getValidityPeriod());
		if (result == 0) {
			// then the year
			result = arg0.getYear().compareTo(arg1.getYear());
		}
		return result;
	}
}
