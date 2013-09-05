package org.fao.fi.vme.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.interfacee.Year;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity(name = "GEO_REF")
public class GeoRef implements Year<GeoRef> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 
	 */
	private Integer year;

	/**
	 *   
	 */
	private String geographicFeatureID;

	/**
	 * 
	 */
	@OneToOne(cascade = CascadeType.MERGE)
	private Vme vme;

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public Integer getYear() {
		return year;
	}

	@Override
	public void setYear(Integer year) {
		this.year = year;
	}

	public String getGeographicFeatureID() {
		return geographicFeatureID;
	}

	public void setGeographicFeatureID(String geographicFeatureID) {
		this.geographicFeatureID = geographicFeatureID;
	}

	public Vme getVme() {
		return vme;
	}

	public void setVme(Vme vme) {
		this.vme = vme;
	}

	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + ((geographicFeatureID == null) ? 0 : geographicFeatureID.hashCode());
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
		GeoRef other = (GeoRef) obj;
		if (geographicFeatureID == null) {
			if (other.geographicFeatureID != null) {
				return false;
			}
		} else if (!geographicFeatureID.equals(other.geographicFeatureID)) {
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
