package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class GeneralMeasures extends Observation {

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
	  */
	private Source linkCemSource = new Source();

	/** 
	  */
	@OneToOne
	private Vme primairlyConcernedVme;

	/** */
	private String rfbFishingAreas;

	/** */
	private String encounter;

	/** */
	private String indicatorSpecies;

	/** */
	private String threshold;

	/** */
	private ValidityPeriod validityPeriod = new ValidityPeriod();

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

	public Source getLinkCemSource() {
		return linkCemSource;
	}

	public void setLinkCemSource(Source linkCemSource) {
		this.linkCemSource = linkCemSource;
	}

	public Vme getPrimairlyConcernedVme() {
		return primairlyConcernedVme;
	}

	public void setPrimairlyConcernedVme(Vme primairlyConcernedVme) {
		this.primairlyConcernedVme = primairlyConcernedVme;
	}

	public String getRfbFishingAreas() {
		return rfbFishingAreas;
	}

	public void setRfbFishingAreas(String rfbFishingAreas) {
		this.rfbFishingAreas = rfbFishingAreas;
	}

	public String getEncounter() {
		return encounter;
	}

	public void setEncounter(String encounter) {
		this.encounter = encounter;
	}

	public String getIndicatorSpecies() {
		return indicatorSpecies;
	}

	public void setIndicatorSpecies(String indicatorSpecies) {
		this.indicatorSpecies = indicatorSpecies;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
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
