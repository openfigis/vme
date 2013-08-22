package org.fao.fi.vme.domain.logic;

import java.util.Comparator;

import org.fao.fi.vme.domain.interfacee.Year;

/**
 * 
 * @author Erik van Ingen
 * 
 */

public class YearComperator implements Comparator<Year<?>> {

	@Override
	public int compare(Year<?> y1, Year<?> y2) {
		return y1.getYear().compareTo(y2.getYear());
	}

}
