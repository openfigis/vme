package org.fao.fi.vme.sync2.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.History;
import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.interfacee.Period;
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

	public List<DisseminationYearSlice> collect(Vme vme) {
		List<DisseminationYearSlice> l = new ArrayList<DisseminationYearSlice>();

		int beginYear = vme.getValidityPeriod().getBeginYear();
		int endYear = vme.getValidityPeriod().getEndYear();

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
		List<History> fishingHistoryList = vme.getRfmo().getFishingHistoryList();
		if (fishingHistoryList != null) {
			History fishingHistory = findRelavantYear(fishingHistoryList, disseminationYear);
			slice.setRfmoHistory(fishingHistory);
		}

		// vme history
		List<History> vmeHistoryList = vme.getHistoryList();
		if (vmeHistoryList != null) {
			History vmeHistory = findRelavantYear(vmeHistoryList, disseminationYear);
			slice.setVmeHistory(vmeHistory);
		}
		// TODO GeoRef

	}

	private History findRelavantYear(List<History> hList, int disseminationYear) {
		Collections.sort(hList, new YearComperator());
		History history = null;
		for (History foundHistory : hList) {
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

		List<SpecificMeasures> smList = vme.getSpecificMeasureList();
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
