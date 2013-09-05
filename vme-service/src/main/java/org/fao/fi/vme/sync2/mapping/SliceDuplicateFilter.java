package org.fao.fi.vme.sync2.mapping;

import java.util.ArrayList;
import java.util.List;

public class SliceDuplicateFilter {

	public void filter(List<DisseminationYearSlice> slices) {
		List<DisseminationYearSlice> redundant = new ArrayList<DisseminationYearSlice>();
		for (int i = 0; i < slices.size(); i++) {
			int previousElement = i - 1;
			if (previousElement >= 0) {
				if (slices.get(previousElement).equals(slices.get(i))) {
					redundant.add(slices.get(i));
				}
			}
		}
		for (DisseminationYearSlice disseminationYearSlice : redundant) {
			slices.remove(disseminationYearSlice);
		}

	}
}
