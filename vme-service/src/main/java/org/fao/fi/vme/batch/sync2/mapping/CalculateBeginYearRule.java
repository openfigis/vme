package org.fao.fi.vme.batch.sync2.mapping;

import java.util.List;

import org.fao.fi.vme.domain.interfaces.Year;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.support.ValidityPeriodUtil;

/**
 * 
 * Rule to calculate the begin year, based on the validity period of the VME and the specific and general measures.
 * 
 * 
 * @author Erik van Ingen
 *
 */
public class CalculateBeginYearRule {

	private ValidityPeriodUtil vu = new ValidityPeriodUtil();

	private int year;

	/**
	 * * Factsheets will be generated, starting with the beginyear of the validityperiod. If there is a measure which
	 * has a issue year earlier than that, that one will count as the beginyear. CalculateBeginYearRule
	 * 
	 * @param vme
	 * @return
	 */
	int calculate(Vme vme) {
		this.year = vu.getBeginYear(vme.getValidityPeriod());
		considerBeginYear(vme.getSpecificMeasureList());
		considerBeginYear(vme.getRfmo().getGeneralMeasureList());
		return year;
	}

	private <T> void considerBeginYear(List<T> yearList) {
		if (yearList != null) {
			for (T t : yearList) {
				Year<?> yearObject = (Year<?>) t;
				if (yearObject.getYear() < this.year) {
					this.year = yearObject.getYear();
				}
			}
		}
	}
}
