package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class SpecificMeasures extends Observation {

	/**
	 *  
	 */
	@Id
	private int id;

	/** 
	 */
	private String measureSummary;

	/** */
	public ValidityPeriod validityPeriod;

	/**
	 * The SpecificMeasures are defined on the level of this VME
	 */
	public Vme vme;

	/**
	 * Where the document of the SpecificMeasures is to be found.
	 */
	public Source document;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMeasureSummary() {
		return measureSummary;
	}

	public void setMeasureSummary(String measureSummary) {
		this.measureSummary = measureSummary;
	}

	public ValidityPeriod getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public Vme getVme() {
		return vme;
	}

	public void setVme(Vme vme) {
		this.vme = vme;
	}

	public Source getDocument() {
		return document;
	}

	public void setDocument(Source document) {
		this.document = document;
	}

}
