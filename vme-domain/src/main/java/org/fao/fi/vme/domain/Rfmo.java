package org.fao.fi.vme.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	private List<GeneralMeasure> generalMeasureList;

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

	@OneToMany(cascade = { CascadeType.ALL })
	private List<History> hasFisheryAreasHistory;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(nullable = true)
	private List<History> hasVmesHistory;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<GeneralMeasure> getGeneralMeasureList() {
		return generalMeasureList;
	}

	public void setGeneralMeasureList(List<GeneralMeasure> generalMeasureList) {
		this.generalMeasureList = generalMeasureList;
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

	public List<History> getHasVmesHistory() {
		return hasVmesHistory;
	}

	public void setHasVmesHistory(List<History> hasVmesHistory) {
		this.hasVmesHistory = hasVmesHistory;
	}

}
