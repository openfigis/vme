package org.fao.fi.vme.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity(name = "SPECIFIC_MEASURES")
public class SpecificMeasures {

	/**
	 *  
	 */
	@Id
	private int id;

	/**
	 * The SpecificMeasures are defined on the level of this VME, sometimes applies also to other VMEs.
	 */
	@ManyToMany
	public List<Vme> vmeList;

	/**
	 *  
	  */
	public ValidityPeriod validityPeriod;

	/**
	 * Year in which the measures are defined, established.
	 */
	private int year;

	/** 
	 */
	private String measureSummary;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Vme> getVmeList() {
		return vmeList;
	}

	public void setVmeList(List<Vme> vmeList) {
		this.vmeList = vmeList;
	}

	public ValidityPeriod getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMeasureSummary() {
		return measureSummary;
	}

	public void setMeasureSummary(String measureSummary) {
		this.measureSummary = measureSummary;
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
