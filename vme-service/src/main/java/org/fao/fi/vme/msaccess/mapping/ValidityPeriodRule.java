package org.fao.fi.vme.msaccess.mapping;

/**
 * Rules for the validity period are described here :
 * http://km.fao.org/FIGISwiki/index.php/VME_UML#Rules_for_the_validity_period
 * and implemented in this class.
 * 
 * @author Erik van Ingen
 * 
 */

public class ValidityPeriodRule {

	int start;
	int end;

	public ValidityPeriodRule(String startYear, String endYear) {
		if (startYear == null) {
			start = 0;
		} else {
			start = (new Integer(startYear).intValue());
		}

		if (endYear == null) {
			end = 9999;
		} else {
			end = (new Integer(endYear).intValue());
		}
	}

	public ValidityPeriodRule(int startYear, int endYear) {
		if (startYear == 0) {
			start = 0;
		} else {
			start = startYear;
		}

		if (endYear == 0) {
			end = 9999;
		} else {
			end = endYear;
		}
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

}
