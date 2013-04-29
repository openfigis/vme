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
	@OneToMany(mappedBy = "rfmo")
	private List<GeneralMeasures> generalMeasuresList = new ArrayList<GeneralMeasures>();

	/** 
	  */
	@OneToMany(mappedBy = "rfmo")
	private List<Vme> managedVmeList = new ArrayList<Vme>();

	/**
	   */
	@OneToMany(mappedBy = "rfmo")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rfmo other = (Rfmo) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
