package org.fao.fi.vme.domain.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.interfaces.PeriodYear;
import org.fao.fi.vme.domain.interfaces.Year;
import org.fao.fi.vme.domain.support.MultiLingualStringConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenced;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGName;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGWeight;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.IntegerDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.LongDataConverter;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@RSGReferenced
@Entity(name = "SPECIFIC_MEASURE")
public class SpecificMeasure implements PeriodYear, Year<SpecificMeasure> {

	/**
	 *  
	 */
	@RSGIdentifier
	@RSGConverter(LongDataConverter.class)
	@Id
	private Long id;

	/**
	 * The SpecificMeasure is defined on the level of this VME, sometimes applies also to other VMEs.
	 */
	/*
	 * This is the inverse side of the ManyToMany relationship
	 */
	@ManyToMany(mappedBy = "specificMeasureList")
	private List<Vme> vmeList;

	@RSGName("Source Of Information")
	@RSGWeight(100)
	@OneToOne(cascade = { CascadeType.ALL })
	private InformationSource informationSource;

	/**
	 *  
	  */
	@RSGName("Validity Period")
	@RSGWeight(2)
	private ValidityPeriod validityPeriod;

	/**
	 * YearObject in which the measure are defined, established.
	 */
	@RSGName("Year")
	@RSGWeight(1)
	@RSGConverter(IntegerDataConverter.class)
	private Integer year;

	/** 
	 */
	@RSGName("VME Specific Measure Summary")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(2)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString vmeSpecificMeasure;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Vme> getVmeList() {
		return vmeList;
	}

	public void setVmeList(List<Vme> vmeList) {
		this.vmeList = vmeList;
	}

	public InformationSource getInformationSource() {
		return informationSource;
	}

	public void setInformationSource(InformationSource informationSource) {
		this.informationSource = informationSource;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.informationSource == null) ? 0 : this.informationSource.hashCode());
		result = prime * result + ((this.validityPeriod == null) ? 0 : this.validityPeriod.hashCode());
		result = prime * result + ((this.vmeList == null) ? 0 : this.vmeList.hashCode());
		result = prime * result + ((this.vmeSpecificMeasure == null) ? 0 : this.vmeSpecificMeasure.hashCode());
		result = prime * result + ((this.year == null) ? 0 : this.year.hashCode());
		return result;
	}

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
		SpecificMeasure other = (SpecificMeasure) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		if (this.informationSource == null) {
			if (other.informationSource != null)
				return false;
		} else if (!this.informationSource.equals(other.informationSource))
			return false;
		if (this.validityPeriod == null) {
			if (other.validityPeriod != null)
				return false;
		} else if (!this.validityPeriod.equals(other.validityPeriod))
			return false;
		if (this.vmeList == null) {
			if (other.vmeList != null)
				return false;
		} else if (!this.vmeList.equals(other.vmeList))
			return false;
		if (this.vmeSpecificMeasure == null) {
			if (other.vmeSpecificMeasure != null)
				return false;
		} else if (!this.vmeSpecificMeasure.equals(other.vmeSpecificMeasure))
			return false;
		if (this.year == null) {
			if (other.year != null)
				return false;
		} else if (!this.year.equals(other.year))
			return false;
		return true;
	}
}