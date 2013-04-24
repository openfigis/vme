package org.fao.fi.vme.domain;

import javax.persistence.Embeddable;

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
 * Objects={ Vme, SpecificMeasures, GeneralMeasures, }
 * 
 * ValidityPeriods for GeneralMeasureSet = {2000-2007, 2008-2009, 2010-9999}
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@Embeddable
public class ValidityPeriod {

	/** 
	  
	  */
	private int beginYear;

	/** 
	  */
	private int endYear;
}
