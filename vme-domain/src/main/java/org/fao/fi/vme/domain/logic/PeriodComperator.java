package org.fao.fi.vme.domain.logic;

import java.util.Comparator;

import org.fao.fi.vme.domain.interfacee.Period;

/**
 * 
 * @author Erik van Ingen
 * 
 */

public class PeriodComperator implements Comparator<Period> {

	@Override
	public int compare(Period o1, Period o2) {
		return o1.getValidityPeriod().compareTo(o2.getValidityPeriod());
	}
}
