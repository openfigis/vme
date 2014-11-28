package org.fao.fi.vme.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenceReport;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.ReferenceReport;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
@RSGReferenceReport(name = "Authority")
public class Rfmo implements ReferenceReport, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2965413570014009146L;

	/**
	 * The id comes from RMTS
	 */
	@RSGIdentifier("Acronym")
	@Id
	private String id;

	/**
	 * Rfmo
	 * 
	 */
	// @RSGName("Authority - General Measures")
	@OneToMany(mappedBy = "rfmo", fetch = FetchType.EAGER)
	private List<GeneralMeasure> generalMeasureList;

	/**
	 * 
	 * This RFMO manages this list of Vmes
	 * 
	 */
	@OneToMany(mappedBy = "rfmo", cascade = CascadeType.ALL)
	private List<Vme> listOfManagedVmes = new ArrayList<Vme>();

	/**
	 * An RFMO has a number of information sources
	 */
	// @RSGName("Authority - Information Sources")
	@OneToMany(mappedBy = "rfmo", cascade = CascadeType.ALL)
	private List<InformationSource> informationSourceList = new ArrayList<InformationSource>();

	/*
	 * FetchType.LAZY instead of FetchType.EAGER was necessary in order to avoid the
	 * "MultipleBagFetchException: cannot simultaneously fetch multiple bags" TODO: Investigate this more, because using
	 * LAZY here would not necessarily be a robust solution.
	 */
	// @RSGName("Fishery Areas History")
	@OneToMany(mappedBy = "rfmo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<FisheryAreasHistory> hasFisheryAreasHistory;

	// @RSGName("VMEs History")
	@OneToMany(mappedBy = "rfmo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<VMEsHistory> hasVmesHistory;

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

	public List<FisheryAreasHistory> getHasFisheryAreasHistory() {
		return hasFisheryAreasHistory;
	}

	public void setHasFisheryAreasHistory(List<FisheryAreasHistory> hasFisheryAreasHistory) {
		this.hasFisheryAreasHistory = hasFisheryAreasHistory;
	}

	public List<VMEsHistory> getHasVmesHistory() {
		return hasVmesHistory;
	}

	public void setHasVmesHistory(List<VMEsHistory> hasVmesHistory) {
		this.hasVmesHistory = hasVmesHistory;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Rfmo other = (Rfmo) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}