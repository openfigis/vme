package org.fao.fi.vme.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	private String id;

	/**
	 * Rfmo
	 * 
	 */
	@OneToMany(mappedBy = "rfmo")
	private List<GeneralMeasures> generalMeasuresList;

	/**
	 * 
	 * This RFMO manages this list of Vmes
	 * 
	 */
	@OneToMany(mappedBy = "rfmo")
	private List<Vme> listOfManagedVmes = new ArrayList<Vme>();

	/**
	 * An RFMO has a number of information sources
	 */
	@ManyToMany
	private List<InformationSource> informationSourceList = new ArrayList<InformationSource>();

	@OneToMany
	private List<History> hasFisheryAreasHistory;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<GeneralMeasures> getGeneralMeasuresList() {
		return generalMeasuresList;
	}

	public void setGeneralMeasuresList(List<GeneralMeasures> generalMeasuresList) {
		this.generalMeasuresList = generalMeasuresList;
	}

	public List<Vme> getListOfManagedVmes() {
		return listOfManagedVmes;
	}

	public void setListOfManagedVmes(List<Vme> listOfManagedVmes) {
		this.listOfManagedVmes = listOfManagedVmes;
	}

	public List<InformationSource> getInformationSourceList() {
		return informationSourceList;
	}

	public void setInformationSourceList(List<InformationSource> informationSourceList) {
		this.informationSourceList = informationSourceList;
	}

	public List<History> getHasFisheryAreasHistory() {
		return hasFisheryAreasHistory;
	}

	public void setHasFisheryAreasHistory(List<History> hasFisheryAreasHistory) {
		this.hasFisheryAreasHistory = hasFisheryAreasHistory;
	};

}
