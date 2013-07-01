package org.fao.fi.vme.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity(name = "GENERAL_MEASURES")
public class GeneralMeasures {

	/** 
	 * 
	 */
	@Id
	private int id;

	/**
	 * GeneralMeasures are defined on the level of a RFMO.
	 */
	@OneToOne
	private Rfmo rfmo;

	/**
	 * GeneralMeasure has one linkCemSource. It should be the column Link_CEM_Source within the Measures_VME_general
	 * table.
	 * 
	 */
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<InformationSource> informationSourceList;

	/**
	 * Year in which the measures are defined, established.
	 */
	private int year;

	/**
	 * 
	 */
	private String fishingAreas;

	/**
	 * 
	 */
	private String explorataryFishingProtocols;

	/**
	 * 
	 */
	private String vmeEncounterProtocols;

	/**
	 * 
	 */
	private String vmeIndicatorSpecies;

	/**
	 * 
	 */
	private String vmeThreshold;

	/**
	 * 
	 */
	private ValidityPeriod validityPeriod;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Rfmo getRfmo() {
		return rfmo;
	}

	public void setRfmo(Rfmo rfmo) {
		this.rfmo = rfmo;
	}

	public List<InformationSource> getInformationSourceList() {
		return informationSourceList;
	}

	public void setInformationSourceList(List<InformationSource> informationSourceList) {
		this.informationSourceList = informationSourceList;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getFishingAreas() {
		return fishingAreas;
	}

	public void setFishingAreas(String fishingAreas) {
		this.fishingAreas = fishingAreas;
	}

	public String getExplorataryFishingProtocols() {
		return explorataryFishingProtocols;
	}

	public void setExplorataryFishingProtocols(String explorataryFishingProtocols) {
		this.explorataryFishingProtocols = explorataryFishingProtocols;
	}

	public String getVmeEncounterProtocols() {
		return vmeEncounterProtocols;
	}

	public void setVmeEncounterProtocols(String vmeEncounterProtocols) {
		this.vmeEncounterProtocols = vmeEncounterProtocols;
	}

	public String getVmeIndicatorSpecies() {
		return vmeIndicatorSpecies;
	}

	public void setVmeIndicatorSpecies(String vmeIndicatorSpecies) {
		this.vmeIndicatorSpecies = vmeIndicatorSpecies;
	}

	public String getVmeThreshold() {
		return vmeThreshold;
	}

	public void setVmeThreshold(String vmeThreshold) {
		this.vmeThreshold = vmeThreshold;
	}

	public ValidityPeriod getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
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
		GeneralMeasures other = (GeneralMeasures) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
