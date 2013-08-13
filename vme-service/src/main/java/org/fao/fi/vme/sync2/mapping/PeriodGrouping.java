package org.fao.fi.vme.sync2.mapping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.History;
import org.fao.fi.vme.domain.Profile;
import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.interfacee.Period;
import org.fao.fi.vme.domain.interfacee.YearObject;
import org.fao.fi.vme.domain.logic.PeriodComperator;
import org.fao.fi.vme.domain.logic.YearComperator;

/**
 * 
 * PeriodGrouping is slicing all the information from the VME domain model in a slice per year.
 * 
 * There are Period objects and Year Objects. It could be better to have only Period Objects.
 * 
 * 
 * 
 * 
 * @author Erik van Ingen
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
			doValidityPeriodStuff(vme, disseminationYear, slice);
			doYearStuff(vme, disseminationYear, slice);

			slice.setYear(disseminationYear);
			l.add(slice);
		}
		return l;
	}

	private void doYearStuff(Vme vme, int disseminationYear, DisseminationYearSlice slice) {

		// fishing history
		List<? extends YearObject<?>> fishingHistoryList = vme.getRfmo().getFishingHistoryList();
		if (fishingHistoryList != null) {
			YearObject<?> fishingHistory = findRelavantYear(fishingHistoryList, disseminationYear);
			slice.setRfmoHistory((History) fishingHistory);
		}

		// vme history
		List<History> vmeHistoryList = vme.getHistoryList();
		if (vmeHistoryList != null) {
			YearObject<?> vmeHistory = findRelavantYear(vmeHistoryList, disseminationYear);
			slice.setVmeHistory((History) vmeHistory);
		}

		// GeoRef
		List<GeoRef> geoRefList = vme.getGeoRefList();
		if (geoRefList != null) {
			YearObject<?> geoRef = findRelavantYear(geoRefList, disseminationYear);
			slice.setGeoRef((GeoRef) geoRef);
		}

		// Profile.
		List<Profile> profileList = vme.getProfileList();
		if (profileList != null) {
			YearObject<?> geoRef = findRelavantYear(profileList, disseminationYear);
			slice.setProfile((Profile) geoRef);
		}

	}

	private YearObject<?> findRelavantYear(List<? extends YearObject<?>> hList, int disseminationYear) {

		Collections.sort(hList, new YearComperator());
		YearObject<?> history = null;
		for (YearObject<?> foundHistory : hList) {
			// take the first you can get. Otherwise the year of the object founds need to be equal or less.
			if (history == null || disseminationYear >= foundHistory.getYear()) {
				history = foundHistory;
			}
		}
		return history;
	}

	private void doValidityPeriodStuff(Vme vme, int year, DisseminationYearSlice slice) {
		if (period(year, vme)) {
			slice.setVme(vme);
		}

		List<SpecificMeasures> smList = vme.getSpecificMeasuresList();
		if (smList != null) {
			Collections.sort(smList, new PeriodComperator());
			for (SpecificMeasures specificMeasures : smList) {
				if (period(year, specificMeasures)) {
					slice.setSpecificMeasures(specificMeasures);
				}
			}
		}

		List<GeneralMeasures> gmList = vme.getRfmo().getGeneralMeasuresList();
		if (gmList != null) {
			Collections.sort(gmList, new PeriodComperator());
			for (GeneralMeasures generalMeasures : gmList) {
				if (period(year, generalMeasures)) {
					slice.setGeneralMeasures(generalMeasures);
				}
			}
		}
	}

	private boolean period(int disseminationYear, Period<?> period) {
		return period.getValidityPeriod().getBeginYear() <= disseminationYear;
	}

}
