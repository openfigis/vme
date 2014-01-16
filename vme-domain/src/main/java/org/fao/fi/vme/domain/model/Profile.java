package org.fao.fi.vme.domain.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.interfaces.Year;
import org.fao.fi.vme.domain.support.MultiLingualStringConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenced;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGMandatory;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGName;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.IntegerDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.LongDataConverter;

/**
 * The features of a VME, defined in a certain year.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@RSGReferenced
@Entity
public class Profile implements ObjectId<Long>, Year<Profile> {

	@RSGIdentifier
	@RSGConverter(LongDataConverter.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * YearObject in which the features where defined. This maps to the figis
	 * reporting year.
	 */
	@RSGName("Year")
	@RSGMandatory
	@RSGConverter(IntegerDataConverter.class)
	private Integer year;

	@ManyToOne
	private Vme vme;

	/**
	 * Physical description of the VME
	 * 
	 */
	@RSGName("VME Physical description")
	@RSGConverter(MultiLingualStringConverter.class)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString descriptionPhisical;

	/**
	 * 
	 * Biological description of the VME
	 */
	@RSGName("VME Biological description")
	@RSGConverter(MultiLingualStringConverter.class)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString descriptionBiological;

	/**
	 * Description of the impact of this VME
	 */
	@RSGName("VME Impact description")
	@RSGConverter(MultiLingualStringConverter.class)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString descriptionImpact;

	@RSGName("Geoform")
	@RSGConverter(MultiLingualStringConverter.class)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString geoform;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Vme getVme() {
		return vme;
	}

	public void setVme(Vme vme) {
		this.vme = vme;
	}

	public MultiLingualString getDescriptionPhisical() {
		return descriptionPhisical;
	}

	public void setDescriptionPhisical(MultiLingualString descriptionPhisical) {
		this.descriptionPhisical = descriptionPhisical;
	}

	public MultiLingualString getDescriptionBiological() {
		return descriptionBiological;
	}

	public void setDescriptionBiological(MultiLingualString descriptionBiological) {
		this.descriptionBiological = descriptionBiological;
	}

	public MultiLingualString getDescriptionImpact() {
		return descriptionImpact;
	}

	public void setDescriptionImpact(MultiLingualString descriptionImpact) {
		this.descriptionImpact = descriptionImpact;
	}

	public MultiLingualString getGeoform() {
		return geoform;
	}

	public void setGeoform(MultiLingualString geoform) {
		this.geoform = geoform;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.descriptionBiological == null) ? 0 : this.descriptionBiological.hashCode());
		result = prime * result + ((this.descriptionImpact == null) ? 0 : this.descriptionImpact.hashCode());
		result = prime * result + ((this.descriptionPhisical == null) ? 0 : this.descriptionPhisical.hashCode());
		result = prime * result + ((this.geoform == null) ? 0 : this.geoform.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.vme == null) ? 0 : this.vme.hashCode());
		result = prime * result + ((this.year == null) ? 0 : this.year.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		Profile other = (Profile) obj;
		if (this.descriptionBiological == null) {
			if (other.descriptionBiological != null)
				return false;
		} else if (!this.descriptionBiological.equals(other.descriptionBiological))
			return false;
		if (this.descriptionImpact == null) {
			if (other.descriptionImpact != null)
				return false;
		} else if (!this.descriptionImpact.equals(other.descriptionImpact))
			return false;
		if (this.descriptionPhisical == null) {
			if (other.descriptionPhisical != null)
				return false;
		} else if (!this.descriptionPhisical.equals(other.descriptionPhisical))
			return false;
		if (this.geoform == null) {
			if (other.geoform != null)
				return false;
		} else if (!this.geoform.equals(other.geoform))
			return false;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		if (this.vme == null) {
			if (other.vme != null)
				return false;
		} else if (!this.vme.equals(other.vme))
			return false;
		if (this.year == null) {
			if (other.year != null)
				return false;
		} else if (!this.year.equals(other.year))
			return false;
		return true;
	}
}