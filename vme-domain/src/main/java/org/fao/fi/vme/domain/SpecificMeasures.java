package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	 * The SpecificMeasures are defined on the level of this VME
	 */
	@ManyToOne
	public Vme vme;

	/** 
	 */
	private String measureSummary;

	/** */
	public ValidityPeriod validityPeriod = new ValidityPeriod();

	/**
	 * Where the document of the SpecificMeasures is to be found.
	 */
	public Source document = new Source();

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
		SpecificMeasures other = (SpecificMeasures) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
