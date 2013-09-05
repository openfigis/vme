package org.fao.fi.vme.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.interfacee.Year;

/**
 * The features of a VME, defined in a certain year.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class Profile implements Year<Profile> {

	@Id
	private Long id;

	/**
	 * YearObject in which the features where defined. This maps to the figis reporting year.
	 */
	private Integer year;

	@ManyToOne
	private Vme vme;

	/**
	 * Physical description of the VME
	 * 
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString descriptionPhisical;

	/**
	 * 
	 * Biological description of the VME
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString descriptionBiological;

	/**
	 * Description of the impact of this VME
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString descriptionImpact;

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

	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + ((descriptionBiological == null) ? 0 : descriptionBiological.hashCode());
	// result = prime * result + ((descriptionImpact == null) ? 0 : descriptionImpact.hashCode());
	// result = prime * result + ((descriptionPhisical == null) ? 0 : descriptionPhisical.hashCode());
	// result = prime * result + ((id == null) ? 0 : id.hashCode());
	// result = prime * result + ((vme == null) ? 0 : vme.hashCode());
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
		if (getClass() != obj.getClass()) {
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
