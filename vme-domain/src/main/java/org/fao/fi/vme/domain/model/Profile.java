package org.fao.fi.vme.domain.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.interfaces.Year;
import org.gcube.application.rsg.support.annotations.RSGReferenced;
import org.gcube.application.rsg.support.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.annotations.fields.RSGMandatory;
import org.gcube.application.rsg.support.annotations.fields.RSGName;

/**
 * The features of a VME, defined in a certain year.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@RSGReferenced
@Entity
public class Profile implements Year<Profile> {

	@RSGIdentifier
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * YearObject in which the features where defined. This maps to the figis reporting year.
	 */
	@RSGName("Year")
	@RSGMandatory
	private Integer year;

	@ManyToOne
	private Vme vme;

	/**
	 * Physical description of the VME
	 * 
	 */
	@RSGName("VME Phisical description")
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString descriptionPhisical;

	/**
	 * 
	 * Biological description of the VME
	 */
	@RSGName("VME Biological description")
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString descriptionBiological;

	/**
	 * Description of the impact of this VME
	 */
	@RSGName("VME Impact description")
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString descriptionImpact;

	@RSGName("Geoform")
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString geoform;

	public Long getId() {
		return id;
	}

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Profile)) {
			return false;
		}
		Profile other = (Profile) obj;
		if (descriptionBiological == null) {
			if (other.descriptionBiological != null) {
				return false;
			}
		} else if (!descriptionBiological.equals(other.descriptionBiological)) {
			return false;
		}
		if (descriptionImpact == null) {
			if (other.descriptionImpact != null) {
				return false;
			}
		} else if (!descriptionImpact.equals(other.descriptionImpact)) {
			return false;
		}
		if (descriptionPhisical == null) {
			if (other.descriptionPhisical != null) {
				return false;
			}
		} else if (!descriptionPhisical.equals(other.descriptionPhisical)) {
			return false;
		}
		if (geoform == null) {
			if (other.geoform != null) {
				return false;
			}
		} else if (!geoform.equals(other.geoform)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (vme == null) {
			if (other.vme != null) {
				return false;
			}
		} else if (!vme.equals(other.vme)) {
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
