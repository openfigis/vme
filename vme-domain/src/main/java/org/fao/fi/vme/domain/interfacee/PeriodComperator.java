package org.fao.fi.vme.domain.interfacee;

import java.util.Comparator;

public class PeriodComperator implements Comparator<Period> {

	@Override
	public int compare(Period o1, Period o2) {
		return o1.getValidityPeriod().compareTo(o2.getValidityPeriod());
	}
}
