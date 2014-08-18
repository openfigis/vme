package org.fao.fi.vme.batch.sync2.mapping;

import java.util.List;

public class SliceDuplicateFilter {

	/**
	 * The algorithm filters duplicates which comes one after the other. If the
	 * same element is more than 1 position distant from its equal, it will not
	 * be filtered.
	 * 
	 * This algorithm is based on the equals method, which also takes into
	 * account the ids and the year. The slicer finds for every year the
	 * corresponding objects. If it would happen that a subsequent year a slice
	 * would use the same objects, this filter would filter these slices out.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param slices
	 */
	public void filter(List<DisseminationYearSlice> slices) {
		for (int i = 0; i < slices.size(); i++) {

			int previousElement = i - 1;
			if (previousElement >= 0 && slices.get(previousElement).equals(slices.get(i))) {
				slices.remove(i);
				i--;
			}
		}
	}
}
