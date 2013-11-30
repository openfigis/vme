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

	@RSGName("Specific measure information source")
	@OneToOne(cascade = { CascadeType.ALL })
	private InformationSource informationSource;

	/**
	 *  
	  */
	@RSGName("Validity Period")
	private ValidityPeriod validityPeriod;

	/**
	 * YearObject in which the measure are defined, established.
	 */
	@RSGName("Year")
	@RSGConverter(IntegerDataConverter.class)
	private Integer year;

	/** 
	 */
	@RSGName("VME Specific Measure Summary")
	@RSGConverter(MultiLingualStringConverter.class)
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

	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + ((id == null) ? 0 : id.hashCode());
	// result = prime * result + ((informationSource == null) ? 0 : informationSource.hashCode());
	// result = prime * result + ((validityPeriod == null) ? 0 : validityPeriod.hashCode());
	// result = prime * result + ((vmeList == null) ? 0 : vmeList.hashCode());
	// result = prime * result + ((vmeSpecificMeasure == null) ? 0 : vmeSpecificMeasure.hashCode());
	// result = prime * result + ((year == null) ? 0 : year.hashCode());
	// return result;
	// }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SpecificMeasure)) {
			return false;
		}
		SpecificMeasure other = (SpecificMeasure) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (informationSource == null) {
			if (other.informationSource != null) {
				return false;
			}
		} else if (!informationSource.equals(other.informationSource)) {
			return false;
		}
		if (validityPeriod == null) {
			if (other.validityPeriod != null) {
				return false;
			}
		} else if (!validityPeriod.equals(other.validityPeriod)) {
			return false;
		}
		if (vmeList == null) {
			if (other.vmeList != null) {
				return false;
			}
		} else if (!vmeList.equals(other.vmeList)) {
			return false;
		}
		if (vmeSpecificMeasure == null) {
			if (other.vmeSpecificMeasure != null) {
				return false;
			}
		} else if (!vmeSpecificMeasure.equals(other.vmeSpecificMeasure)) {
			return false;
		}
		if (year == null) {
			if (other.year != null) {
				return false;
			}
		} else if (!year.equals(other.year)) {
			return false;
		}
		return true;
	}

}
