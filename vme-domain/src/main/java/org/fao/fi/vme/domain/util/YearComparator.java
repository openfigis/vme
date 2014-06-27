/**
 * (c) 2014 FAO / UN (project: vme-domain)
 */
package org.fao.fi.vme.domain.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.fao.fi.vme.domain.interfaces.Year;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 27 Feb 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 27 Feb 2014
 */
public class YearComparator implements Comparator<Year<?>> {
	private static final YearComparator INSTANCE = new YearComparator();
	
	public static final YearComparator getInstance() {
		return INSTANCE;
	}
	
	public static final <Y extends Year<?>> List<Y> sort(List<Y> toSort) {
		if(toSort == null) {
			return null;
		}
		
		Collections.sort(toSort, INSTANCE);
		
		return toSort;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Year<?> y1, Year<?> y2) {
		Integer year1 = y1.getYear();
		Integer year2 = y2.getYear();
		
		if(year1 == null) {
			return year2 == null ? 0 : -1;
		}
		
		if(year2 == null) {
			return 1;
		}
		
		return year2.compareTo(year1);
	}
}
