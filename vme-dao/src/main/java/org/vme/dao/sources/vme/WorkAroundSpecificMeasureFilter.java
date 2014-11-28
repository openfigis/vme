package org.vme.dao.sources.vme;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fao.fi.vme.domain.model.SpecificMeasure;

public class WorkAroundSpecificMeasureFilter {

	public List<SpecificMeasure> filter(List<SpecificMeasure> redundantList) {
		List<SpecificMeasure> nonRedundantList = new ArrayList<SpecificMeasure>();
		Set<Long> set = new HashSet<Long>();
		for (SpecificMeasure specificMeasure : redundantList) {
			if (!set.contains(specificMeasure.getId())) {
				set.add(specificMeasure.getId());
				nonRedundantList.add(specificMeasure);
			}
		}
		return nonRedundantList;
	}
}
