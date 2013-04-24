package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

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
	private int generalMeasuresId;

	/** */
	private String rfbFishingAreas;

	/** */
	private String encounter;

	/** */
	private String indicatorSpecies;

	/** */
	private String threshold;

	/** */
	private ValidityPeriod validityPeriod;

	/**
	 * GeneralMeasures are defined on the level of a RFMO.
	 */
	private Rfmo rfmo;

	/** */
	private Source linkCemSource;

	/** */
	private Vme primairlyConcernedVme;

	public int getGeneralMeasuresId() {
		return generalMeasuresId;
	}

	public void setGeneralMeasuresId(int generalMeasuresId) {
		this.generalMeasuresId = generalMeasuresId;
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

}
