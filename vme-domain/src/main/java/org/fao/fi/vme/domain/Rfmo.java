package org.fao.fi.vme.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class Rfmo {

	@Id
	int id;

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

	public List<GeneralMeasures> getGeneralMeasuresList() {
		return generalMeasuresList;
	}

	public void setGeneralMeasuresList(List<GeneralMeasures> generalMeasuresList) {
		this.generalMeasuresList = generalMeasuresList;
	}

	public List<Vme> getManagedVmeList() {
		return managedVmeList;
	}

	public void setManagedVmeList(List<Vme> managedVmeList) {
		this.managedVmeList = managedVmeList;
	}

	public List<Meeting> getMeetingList() {
		return meetingList;
	}

	public void setMeetingList(List<Meeting> meetingList) {
		this.meetingList = meetingList;
	}

	public List<FishingActivity> getFishingActivityList() {
		return fishingActivityList;
	}

	public void setFishingActivityList(List<FishingActivity> fishingActivityList) {
		this.fishingActivityList = fishingActivityList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
