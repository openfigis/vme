package org.fao.fi.vme.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenceReport;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGName;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
@RSGReferenceReport(name="Authority")
public class Rfmo {
	/**
	 * The id comes from RMTS
	 */
	@RSGIdentifier
	@Id
	private String id;

	/**
	 * Rfmo
	 * 
	 */
	@RSGName("Authority - General Measures")
	@OneToMany(mappedBy = "rfmo", fetch = FetchType.EAGER)
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
	@RSGName("Authority - Information Sources")
	@ManyToMany
	private List<InformationSource> informationSourceList = new ArrayList<InformationSource>();

	@RSGName("Fishery Areas History")
	@OneToMany(cascade = { CascadeType.ALL })
	private List<FisheryAreasHistory> hasFisheryAreasHistory;

	@RSGName("VMEs History")
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(nullable = true)
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
	};

	public List<VMEsHistory> getHasVmesHistory() {
		return hasVmesHistory;
	}

	public void setHasVmesHistory(List<VMEsHistory> hasVmesHistory) {
		this.hasVmesHistory = hasVmesHistory;
	}

//	/* (non-Javadoc)
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((this.generalMeasureList == null) ? 0 : this.generalMeasureList.hashCode());
//		result = prime * result + ((this.hasFisheryAreasHistory == null) ? 0 : this.hasFisheryAreasHistory.hashCode());
//		result = prime * result + ((this.hasVmesHistory == null) ? 0 : this.hasVmesHistory.hashCode());
//		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
//		result = prime * result + ((this.informationSourceList == null) ? 0 : this.informationSourceList.hashCode());
//		result = prime * result + ((this.listOfManagedVmes == null) ? 0 : this.listOfManagedVmes.hashCode());
//		return result;
//	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rfmo other = (Rfmo) obj;
		if (this.generalMeasureList == null) {
			if (other.generalMeasureList != null)
				return false;
		} else if (!this.generalMeasureList.equals(other.generalMeasureList))
			return false;
		if (this.hasFisheryAreasHistory == null) {
			if (other.hasFisheryAreasHistory != null)
				return false;
		} else if (!this.hasFisheryAreasHistory.equals(other.hasFisheryAreasHistory))
			return false;
		if (this.hasVmesHistory == null) {
			if (other.hasVmesHistory != null)
				return false;
		} else if (!this.hasVmesHistory.equals(other.hasVmesHistory))
			return false;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		if (this.informationSourceList == null) {
			if (other.informationSourceList != null)
				return false;
		} else if (!this.informationSourceList.equals(other.informationSourceList))
			return false;
		if (this.listOfManagedVmes == null) {
			if (other.listOfManagedVmes != null)
				return false;
		} else if (!this.listOfManagedVmes.equals(other.listOfManagedVmes))
			return false;
		return true;
	}
}