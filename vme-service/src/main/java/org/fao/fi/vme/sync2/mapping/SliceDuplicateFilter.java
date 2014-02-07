package org.fao.fi.vme.sync2.mapping;

import java.util.List;

public class SliceDuplicateFilter {

	/**
	 * The algorithm filters duplicates which comes one after the other. If the
	 * same element is more than 1 position distant from its equal, it will not
	 * be filtered.
	 * 
	 * 
	 * 
	 * @param slices
	 */
	public void filter(List<DisseminationYearSlice> slices) {
		for (int i = 0; i < slices.size(); i++) {

			int previousElement = i - 1;
			if (previousElement >= 0) {
				if (slices.get(previousElement).equals(slices.get(i))) {
					slices.remove(i);
					i--;
				}
			}
		}
	}
}
