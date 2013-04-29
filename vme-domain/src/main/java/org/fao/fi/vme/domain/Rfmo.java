package org.fao.fi.vme.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	@OneToMany
	private List<GeneralMeasures> generalMeasuresList = new ArrayList<GeneralMeasures>();

	/** 
	  */
	@OneToMany
	private List<Vme> managedVmeList = new ArrayList<Vme>();

	/**
	   */
	@OneToMany
	private List<Meeting> meetingList = new ArrayList<Meeting>();

	/** 
	  */
	@OneToMany
	private List<FishingHistory> fishingActivityList = new ArrayList<FishingHistory>();

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

	public List<FishingHistory> getFishingActivityList() {
		return fishingActivityList;
	}

	public void setFishingActivityList(List<FishingHistory> fishingActivityList) {
		this.fishingActivityList = fishingActivityList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
