package org.fao.fi.vme.sync2.mapping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.fao.fi.vme.domain.GeneralMeasure;
import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.History;
import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.domain.Profile;
import org.fao.fi.vme.domain.SpecificMeasure;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.interfacee.Period;
import org.fao.fi.vme.domain.interfacee.Year;
import org.fao.fi.vme.domain.logic.PeriodYearComperator;
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

		// fishing area history
		List<? extends Year<?>> fishingHistoryList = vme.getRfmo().getHasFisheryAreasHistory();
		if (fishingHistoryList != null) {
			Year<?> fishingHistory = findRelavantYear(fishingHistoryList, disseminationYear);
			slice.setFisheryAreasHistory((History) fishingHistory);
		}

		// vme history
		List<? extends Year<?>> hasVmesHistoryList = vme.getRfmo().getHasVmesHistory();
		if (hasVmesHistoryList != null) {
			Year<?> hasVmesHistory = findRelavantYear(hasVmesHistoryList, disseminationYear);
			slice.setVmesHistory((History) hasVmesHistory);
		}

		// GeoRef
		List<GeoRef> geoRefList = vme.getGeoRefList();
		if (geoRefList != null) {
			Year<?> geoRef = findRelavantYear(geoRefList, disseminationYear);
			slice.setGeoRef((GeoRef) geoRef);
		}

		// Profile.
		List<Profile> profileList = vme.getProfileList();
		if (profileList != null) {
			Year<?> geoRef = findRelavantYear(profileList, disseminationYear);
			slice.setProfile((Profile) geoRef);
		}

		// InformationSource.
		List<InformationSource> informationSourceList = vme.getRfmo().getInformationSourceList();
		if (informationSourceList != null) {
			defineCurrentAndPastSources(disseminationYear, slice, informationSourceList);
		}

	}

	private void defineCurrentAndPastSources(int disseminationYear, DisseminationYearSlice slice,
			List<InformationSource> informationSourceList) {
		List<InformationSource> appropriateList = new ArrayList<InformationSource>();
		for (InformationSource informationSource : informationSourceList) {
			if (informationSource.getPublicationYear() <= disseminationYear) {
				appropriateList.add(informationSource);
			}
		}
		slice.setInformationSourceList(appropriateList);
	}

	private Year<?> findRelavantYear(List<? extends Year<?>> hList, int disseminationYear) {

		Collections.sort(hList, new YearComperator());
		Year<?> history = null;
		for (Year<?> foundHistory : hList) {
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

		List<SpecificMeasure> smList = vme.getSpecificMeasureList();
		if (smList != null) {

			Collections.sort(smList, new PeriodYearComperator());
			for (SpecificMeasure specificMeasures : smList) {
				if (period(year, specificMeasures)) {
					// this logic would pick the latest SpecificMeasures, which would be correct.
					slice.setSpecificMeasures(specificMeasures);
				}
			}
		}

		List<GeneralMeasure> gmList = vme.getRfmo().getGeneralMeasureList();
		if (gmList != null) {
			Collections.sort(gmList, new PeriodYearComperator());
			for (GeneralMeasure generalMeasures : gmList) {
				if (period(year, generalMeasures)) {
					slice.setGeneralMeasure(generalMeasures);
				}
			}
		}
	}

	private boolean period(int disseminationYear, Period<?> period) {
		return period.getValidityPeriod().getBeginYear() <= disseminationYear;
	}

}
