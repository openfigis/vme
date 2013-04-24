package org.fao.fi.vme.domain;

import java.util.List;

import javax.persistence.Entity;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class Rfmo {

	/**
	 * The list of general measures defined on the level of this RFMO
	 * 
	 * 
	 */
	private List<GeneralMeasures> generalMeasuresList;

	/** 
	  */
	private List<Vme> managedVmeList;

	/**
	   */
	private List<Meeting> meetingList;

	/** 
	  */
	private List<FishingActivity> fishingActivityList;
}
