package org.fao.fi.vme.sync2.mapping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.interfaces.Period;
import org.fao.fi.vme.domain.interfaces.Year;
import org.fao.fi.vme.domain.logic.PeriodYearComparator;
import org.fao.fi.vme.domain.logic.YearComparator;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.History;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;

/**
 * 
 * PeriodGrouping is slicing all the information from the VME domain model in a
 * slice per year.
 * 
 * There are Period objects and Year Objects. It could be better to have only
 * Period Objects.
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
		List<? extends Year<History>> fishingHistoryList = vme.getRfmo().getHasFisheryAreasHistory();
		if (fishingHistoryList != null) {
			Year<?> fishingHistory = findRelavantYear(fishingHistoryList, disseminationYear);
			slice.setFisheryAreasHistory((History) fishingHistory);
		}

		// vme history
		List<? extends Year<History>> hasVmesHistoryList = vme.getRfmo().getHasVmesHistory();
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
		if (profileList != null && profileList.size() > 0) {
			Year<?> profile = findRelavantYear(profileList, disseminationYear);
			slice.setProfile((Profile) profile);
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

		if (informationSourceList != null)
			for (InformationSource informationSource : informationSourceList) {
				if (informationSource.getPublicationYear() == null
						|| informationSource.getPublicationYear() <= disseminationYear) {

					appropriateList.add(informationSource);
				}
			}
		slice.setInformationSourceList(appropriateList);
	}

	private Year<?> findRelavantYear(List<? extends Year<?>> hList, int disseminationYear) {

		Collections.sort(hList, new YearComparator());
		Year<?> history = null;
		for (Year<?> foundHistory : hList) {
			// take the first you can get. Otherwise the year of the object
			// founds need to be equal or less.
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

			Collections.sort(smList, new PeriodYearComparator());
			for (SpecificMeasure specificMeasures : smList) {
				if (period(year, specificMeasures)) {
					// this logic would pick the latest SpecificMeasures, which
					// would be correct.

					if (specificMeasures.getVmeSpecificMeasure() == null) {
						throw new VmeException(
								"Error during generating slices. At this point, the specific measure object needs to have a value for VME "
										+ specificMeasures.getVmeList().get(0).getInventoryIdentifier() + " "
										+ specificMeasures.getVmeList().get(0).getId());

					}

					slice.setSpecificMeasures(specificMeasures);
				}
			}
		}

		List<GeneralMeasure> gmList = vme.getRfmo().getGeneralMeasureList();
		if (gmList != null) {
			Collections.sort(gmList, new PeriodYearComparator());
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
