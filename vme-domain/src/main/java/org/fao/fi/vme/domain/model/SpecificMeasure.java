package org.fao.fi.vme.domain.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.interfaces.Year;
import org.fao.fi.vme.domain.support.MultiLingualStringConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenced;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGName;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGRichInput;
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
public class SpecificMeasure implements ObjectId<Long>, Year<SpecificMeasure>, Serializable, Period {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6481813663182378122L;

	/**
	 *  
	 */
	@RSGIdentifier
	@RSGConverter(LongDataConverter.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The SpecificMeasure is defined on the level of this VME, sometimes applies also to other VMEs.
	 */
	/*
	 * This is the inverse side of the ManyToMany relationship
	 */

	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "vme_id")
	private Vme vme;

	@RSGName("Source Of Information")
	@RSGWeight(100)
	// @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "informationsource_id")
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
	 * Review year. Year in which this measure will be reviewed/is reviewed.
	 * 
	 * This attribute has been added to the Vme model as a result of the Vme workshop Rome 2014
	 * 
	 */
	@RSGName("Review Year")
	@RSGWeight(1)
	@RSGConverter(IntegerDataConverter.class)
	private Integer reviewYear;

	/** 
	 */
	@RSGName("VME Specific Measure Summary")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(2)
	@OneToOne(cascade = { CascadeType.ALL }, orphanRemoval=true)
	private @RSGRichInput MultiLingualString vmeSpecificMeasure;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Vme getVme() {
		return vme;
	}

	public void setVme(Vme vme) {
		this.vme = vme;
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

	public Integer getReviewYear() {
		return reviewYear;
	}

	public void setReviewYear(Integer reviewYear) {
		this.reviewYear = reviewYear;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((reviewYear == null) ? 0 : reviewYear.hashCode());
		result = prime * result + ((validityPeriod == null) ? 0 : validityPeriod.hashCode());
		result = prime * result + ((vmeSpecificMeasure == null) ? 0 : vmeSpecificMeasure.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
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
		if (reviewYear == null) {
			if (other.reviewYear != null) {
				return false;
			}
		} else if (!reviewYear.equals(other.reviewYear)) {
			return false;
		}
		if (validityPeriod == null) {
			if (other.validityPeriod != null) {
				return false;
			}
		} else if (!validityPeriod.equals(other.validityPeriod)) {
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