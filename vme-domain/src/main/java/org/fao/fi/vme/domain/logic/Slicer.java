package org.fao.fi.vme.domain.logic;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.interfacee.Valid;

/**
 * This class implements the rules for DisseminationYear.
 * 
 * http://km.fao.org/FIGISwiki/index.php/VME_UML#Rules_for_the_validity_period_and_Year
 * 
 * @author Erik van Ingen
 * 
 */
public class Slicer {

	/**
	 * 
	 * Return all valid objects where the beginYear is equal or earlier than the disseminationYear.
	 * 
	 * @param disseminationYear
	 * @param collection
	 * @return
	 */
	public List<Valid> slice(int disseminationYear, List<Valid> collection) {
		List<Valid> slice = new ArrayList<Valid>();
		for (Valid valid : collection) {
			if (valid.getValidityPeriod().getBeginYear() <= disseminationYear) {
				slice.add(valid);
			}
		}
		return slice;
	}

}
