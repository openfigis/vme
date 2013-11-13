package org.fao.fi.vme.domain.model;

import javax.persistence.Embeddable;

import org.gcube.application.rsg.support.annotations.fields.RSGInstructions;
import org.gcube.application.rsg.support.annotations.fields.RSGName;

/**
 * 
 * Rules for the validity period
 * 
 * An object can have a validity period
 * 
 * 
 * Only one object with a certain validity period applies to a moment in time
 * 
 * Validity periods cannot overlap
 * 
 * Validaty periods are sequential A validity period has a begin and an end year
 * 
 * If the end year is open, 9999 will be used
 * 
 * The begin and end year are inclusive Examples:
 * 
 * Objects={ Vme, SpecificMeasure, GeneralMeasure, }
 * 
 * ValidityPeriods for GeneralMeasureSet = {2000-2007, 2008-2009, 2010-9999}
 * 
 * 
 * See also http://km.fao.org/FIGISwiki/index.php/VME_UML#Rules_for_the_validity_period
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@Embeddable
public class ValidityPeriod implements Comparable<ValidityPeriod> {

	/** 
	  
	  */
	@RSGName("Validity Period - Start")
	private Integer beginYear;

	/** 
	  */
	@RSGName("Validity Period - End")
	@RSGInstructions("End date, leave empty if not applicable")
	private Integer endYear;

	public Integer getBeginYear() {
		return beginYear;
	}

	public void setBeginYear(Integer beginYear) {
		this.beginYear = beginYear;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	/**
	 * This method does not validate
	 */
	@Override
	public int compareTo(ValidityPeriod vp) {
		int compare = 0;
		if (this.getEndYear() < vp.getBeginYear()) {
			compare = -1;
		}
		if (this.getBeginYear() > vp.getEndYear()) {
			compare = 1;
		}
		return compare;
	}
}
