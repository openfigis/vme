package org.fao.fi.vme.sync2.mapping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.fao.fi.vme.domain.interfaces.Year;
import org.fao.fi.vme.domain.logic.YearComparator;
import org.fao.fi.vme.domain.model.Vme;

/**
 * 
 * PeriodGrouping is slicing all the information from the VME domain model in a
 * slice per year.
 * 
 * There are Period objects and Year Objects. It could be better to have only
 * Period Objects.
 * 
 * PeriodGrouping makes for every possible year a slice. The Slice filter will
 * than decide which ones to filer out.
 * 
 * 
 * @author Erik van Ingen
 * @param <T>
 * 
 */
public class PeriodGrouping {

	private static final int FUTURE = 9999;

	public List<DisseminationYearSlice> collect(Vme vme) {

		List<DisseminationYearSlice> l = new ArrayList<DisseminationYearSlice>();

		int beginYear = vme.getValidityPeriod().getBeginYear();
		int endYear = vme.getValidityPeriod().getEndYear();

		if (endYear == FUTURE) {
			endYear = Calendar.getInstance().get(Calendar.YEAR);
		}
		for (int disseminationYear = beginYear; disseminationYear <= endYear; disseminationYear++) {

			DisseminationYearSlice slice = new DisseminationYearSlice();

			slice.setSpecificMeasure(findYearObject(vme.getSpecificMeasureList(), disseminationYear, slice));
			slice.setGeneralMeasure(findYearObject(vme.getRfmo().getGeneralMeasureList(), disseminationYear, slice));
			slice.setFisheryAreasHistory(findYearObject(vme.getRfmo().getHasFisheryAreasHistory(), disseminationYear,
					slice));
			slice.setVmesHistory(findYearObject(vme.getRfmo().getHasVmesHistory(), disseminationYear, slice));
			slice.setGeoRef(findYearObject(vme.getGeoRefList(), disseminationYear, slice));
			slice.setProfile(findYearObject(vme.getProfileList(), disseminationYear, slice));

			slice.setVme(vme);
			slice.setInformationSourceList(vme.getRfmo().getInformationSourceList());

			slice.setYear(disseminationYear);
			l.add(slice);
		}

		return l;
	}

	private <T> T findYearObject(List<T> list, int disseminationYear, DisseminationYearSlice slice) {
		@SuppressWarnings("unchecked")
		List<Year<?>> yearList = (List<Year<?>>) list;

		Year<?> history = null;
		if (yearList != null) {
			Collections.sort(yearList, new YearComparator());
			for (Year<?> foundHistory : yearList) {
				// take the first you can get. Otherwise the year of the object
				// founds need to be equal or less.
				if (disseminationYear >= foundHistory.getYear()) {
					history = foundHistory;
				}
			}
		}
		@SuppressWarnings("unchecked")
		T t = (T) history;
		return t;

	}
}
