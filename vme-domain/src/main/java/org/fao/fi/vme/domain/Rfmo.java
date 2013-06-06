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

	/**
	 * The id comes from RMTS
	 */
	@Id
	private int id;

	/**
	 * The list of general measures defined on the level of this RFMO
	 * 
	 * 
	 */
	@OneToMany(mappedBy = "rfmo")
	private final List<GeneralMeasures> generalMeasuresList = new ArrayList<GeneralMeasures>();

	/**
	 * 
	 * This RFMO manages this list of Vmes
	 * 
	 */
	@OneToMany(mappedBy = "rfmo")
	private final List<Vme> listOfManagedVmes = new ArrayList<Vme>();

	/** 
	  */
	@OneToMany
	private final List<FishingHistory> fishingActivityList = new ArrayList<FishingHistory>();

	/**
	 * An RFMO has a number of information sources
	 */
	@OneToMany
	private final List<InformationSource> informationSourceList = new ArrayList<InformationSource>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<GeneralMeasures> getGeneralMeasuresList() {
		return generalMeasuresList;
	}

	public List<Vme> getListOfManagedVmes() {
		return listOfManagedVmes;
	}

	public List<FishingHistory> getFishingActivityList() {
		return fishingActivityList;
	}

	public List<InformationSource> getInformationSourceList() {
		return informationSourceList;
	}

}
