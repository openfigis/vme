package org.fao.fi.vme.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	 * 
	 * 
	 * 
	 * Rfmo
	 */
	/*
	 * This error occurs
	 * 
	 * org.hibernate.exception.GenericJDBCException: could not initialize a collection:
	 * [org.fao.fi.vme.domain.Rfmo.generalMeasuresList#3] Caused by: java.sql.SQLException: Stream has already been
	 * closed
	 * 
	 * 
	 * 
	 * http://www.razorsql.com/docs/support_oracle_long.html
	 */
	@OneToMany(mappedBy = "rfmo", fetch = FetchType.EAGER)
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
	private List<History> fishingHistoryList;

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

	public List<History> getFishingHistoryList() {
		return fishingHistoryList;
	}

	public void setFishingHistoryList(List<History> fishingHistoryList) {
		this.fishingHistoryList = fishingHistoryList;
	};

}
