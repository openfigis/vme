package org.fao.fi.vme.domain.logic;

import java.util.Comparator;

import org.fao.fi.vme.domain.interfacee.Period;

/**
 * 
 * @author Erik van Ingen
 * 
 */

public class PeriodComperator implements Comparator<Period<?>> {

	@Override
	public int compare(Period<?> p1, Period<?> p2) {
		return p1.getValidityPeriod().compareTo(p2.getValidityPeriod());
	}

}
