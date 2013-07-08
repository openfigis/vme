package org.fao.fi.vme.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity(name = "SPECIFIC_MEASURES")
public class SpecificMeasures implements YearObject<YearObject> {

	/**
	 *  
	 */
	@Id
	private Integer id;

	/**
	 * The SpecificMeasures are defined on the level of this VME, sometimes applies also to other VMEs.
	 */
	@ManyToMany(mappedBy = "specificMeasureList")
	public List<Vme> vmeList;

	/**
	 *  
	  */
	public ValidityPeriod validityPeriod;

	/**
	 * YearObject in which the measures are defined, established.
	 */
	private Integer year;

	/** 
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString vmeSpecificMeasure;

	public Integer getId() {
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

	@Override
	public Integer getYear() {
		return year;
	}

	@Override
	public void setYear(Integer year) {
		this.year = year;
	}

	public MultiLingualString getVmeSpecificMeasure() {
		return vmeSpecificMeasure;
	}

	public void setVmeSpecificMeasure(MultiLingualString vmeSpecificMeasure) {
		this.vmeSpecificMeasure = vmeSpecificMeasure;
	}

	@Override
	public int hashCode() {
		final Integer prime = 31;
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
