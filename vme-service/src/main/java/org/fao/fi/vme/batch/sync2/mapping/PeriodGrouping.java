package org.fao.fi.vme.batch.sync2.mapping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.interfaces.Year;
import org.fao.fi.vme.domain.logic.YearComparator;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.support.ValidityPeriodUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * PeriodGrouping is slicing all the information from the VME domain model in a slice per year.
 * 
 * There are Period objects and Year Objects. It could be better to have only Period Objects.
 * 
 * PeriodGrouping makes for every possible year a slice. The Slice filter will than decide which ones to filer out.
 * 
 * 
 * @author Erik van Ingen
 * @param <T>
 * 
 */
public class PeriodGrouping {

	private static final int FUTURE = 9999;
	private ValidityPeriodUtil vu = new ValidityPeriodUtil();
	private static final Logger LOG = LoggerFactory.getLogger(PeriodGrouping.class);
	private CalculateBeginYearRule calculateBeginYearRule = new CalculateBeginYearRule();
	private GeoRefRule geoRefRule = new GeoRefRule();

	public List<DisseminationYearSlice> collect(Vme vme) {
		// precondition
		if (vme.getValidityPeriod() == null || vme.getValidityPeriod().getBeginDate() == null
				|| vme.getValidityPeriod().getEndDate() == null) {
			throw new VmeException("This vme does not have a properly filled validityPeridod: Vme id " + vme.getId()
					+ " " + vme.getInventoryIdentifier());
		}

		// logic
		List<DisseminationYearSlice> l = new ArrayList<DisseminationYearSlice>();

		int beginYear = calculateBeginYearRule.calculate(vme);
		int endYear = vu.getEndYear(vme.getValidityPeriod());

		if (endYear == FUTURE) {
			endYear = Calendar.getInstance().get(Calendar.YEAR);
		}
		for (int disseminationYear = beginYear; disseminationYear <= endYear; disseminationYear++) {

			DisseminationYearSlice slice = new DisseminationYearSlice();

			slice.setSpecificMeasure(findYearObject(vme.getSpecificMeasureList(), disseminationYear));
			slice.setGeneralMeasure(findYearObject(vme.getRfmo().getGeneralMeasureList(), disseminationYear));
			slice.setFisheryAreasHistory(findYearObject(vme.getRfmo().getHasFisheryAreasHistory(), disseminationYear));
			slice.setVmesHistory(findYearObject(vme.getRfmo().getHasVmesHistory(), disseminationYear));
			slice.setGeoRef(findYearObject(vme.getGeoRefList(), disseminationYear));

			geoRefRule.applyRule(slice, vme.getGeoRefList(), disseminationYear);

			slice.setProfile(findYearObject(vme.getProfileList(), disseminationYear));

			slice.setVme(vme);
			slice.setInformationSourceList(vme.getRfmo().getInformationSourceList());

			slice.setYear(disseminationYear);
			l.add(slice);
		}

		return l;
	}

	@SuppressWarnings("unchecked")
	private <T> T findYearObject(List<T> list, int disseminationYear) {
		List<Year<?>> yearList = (List<Year<?>>) list;

		Year<?> history = null;
		if (yearList != null) {
			Collections.sort(yearList, new YearComparator());
			for (Year<?> yearObject : yearList) {
				// take the first you can get. Otherwise the year of the object
				// founds need to be equal or less.

				if (yearObject == null || yearObject.getYear() == null) {
					LOG.info("yearObject == null || yearObject.getYear() == null");
				}

				if (disseminationYear >= yearObject.getYear()) {
					history = yearObject;
				}
			}
		}

		return (T) history;

	}
}
