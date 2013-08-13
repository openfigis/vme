package org.fao.fi.vme.domain.logic;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.interfacee.Period;

/**
 * This class implements the rules for DisseminationYear.
 * 
 * http://km.fao.org/FIGISwiki/index.php/VME_UML#Rules_for_the_validity_period_and_Year
 * 
 * @deprecated see PeriodGrouping
 * 
 * @author Erik van Ingen
 * 
 */
public class Slicer {

	/**
	 * 
	 * Return all period objects where the beginYear is equal or earlier than the disseminationYear.
	 * 
	 * @param disseminationYear
	 * @param collection
	 * @return
	 */
	public List<Period<?>> slice(int disseminationYear, List<Period<?>> collection) {

		List<Period<?>> slice = new ArrayList<Period<?>>();

		for (Period<?> period : collection) {
			if (period.getValidityPeriod().getBeginYear() <= disseminationYear) {
				slice.add(period);
			}
		}
		return slice;
	}
}
