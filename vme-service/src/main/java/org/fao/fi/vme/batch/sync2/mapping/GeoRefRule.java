package org.fao.fi.vme.batch.sync2.mapping;

import java.util.Collections;
import java.util.List;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.logic.YearComparator;
import org.fao.fi.vme.domain.model.GeoRef;

/**
 * The CalculateBeginYearRule possibly finds a year for which there is no Georef. This logic finds one anyway.
 * 
 * 
 * 
 * @author Erik van Ingen
 *
 */
public class GeoRefRule {

	public void applyRule(DisseminationYearSlice slice, List<GeoRef> geoRefList, int disseminationYear) {

		GeoRef history = null;
		if (slice.getGeoRef() == null && geoRefList != null) {
			Collections.sort(geoRefList, new YearComparator());
			for (GeoRef yearObject : geoRefList) {
				// take the first you can get.
				if (history == null) {
					history = yearObject;
				}
			}
			slice.setGeoRef(history);
		}
		if (slice.getGeoRef() == null) {
			throw new VmeException("No GeoRef found for ths Vme" + slice.getVme().getId());
		}

	}
}
