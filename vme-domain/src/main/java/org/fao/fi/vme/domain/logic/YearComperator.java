package org.fao.fi.vme.domain.logic;

import java.util.Comparator;

import org.fao.fi.vme.domain.interfacee.YearObject;

/**
 * 
 * @author Erik van Ingen
 * 
 */

public class YearComperator implements Comparator<YearObject<?>> {

	@Override
	public int compare(YearObject<?> y1, YearObject<?> y2) {
		return y1.getYear().compareTo(y2.getYear());
	}

}
