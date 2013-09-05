package org.fao.fi.vme.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.interfacee.Period;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class Vme implements Period<Vme> {

	@Id
	private Long id;

	/**
	 * This VME is managed by this Rfmo
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private Rfmo rfmo;

	/*
	 * This is the owning side of the manyToMany relationship
	 */
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "VME_SPECIFIC_MEASURE", //
	joinColumns = @JoinColumn(name = "VME_ID"), inverseJoinColumns = @JoinColumn(name = "SPECIFIC_MEASURE_ID"))
	private List<SpecificMeasure> specificMeasureList;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<Profile> profileList;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<GeoRef> geoRefList;

	/**
	 * This validity period on the level of the reference object and applies to the VME itself. It has noting to do with
	 * the reporting year.
	 * 
	 * 
	 */
	private ValidityPeriod validityPeriod;

	/**
	 *
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString name;

	/**
	 *
	 */
	private String geoform;

	/**
	 *
	 */
	private String areaType;

	/**
	 *
	 */
	private String geoarea;

	/**
	 *
	 */
	private String criteria;

	private String inventoryIdentifier;

	public String getInventoryIdentifier() {
		return inventoryIdentifier;
	}

	public void setInventoryIdentifier(String inventoryIdentifier) {
		this.inventoryIdentifier = inventoryIdentifier;
	}

	public MultiLingualString getName() {
		return name;
	}

	public void setName(MultiLingualString name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Rfmo getRfmo() {
		return rfmo;
	}

	public void setRfmo(Rfmo rfmo) {
		this.rfmo = rfmo;
	}

	public List<Profile> getProfileList() {
		return profileList;
	}

	public void setProfileList(List<Profile> profileList) {
		this.profileList = profileList;
	}

	public List<GeoRef> getGeoRefList() {
		return geoRefList;
	}

	public void setGeoRefList(List<GeoRef> geoRefList) {
		this.geoRefList = geoRefList;
	}

	public ValidityPeriod getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public String getGeoform() {
		return geoform;
	}

	public void setGeoform(String geoform) {
		this.geoform = geoform;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	/**
	 * @return the geoarea
	 */
	public String getGeoArea() {
		return geoarea;
	}

	/**
	 * @param geoarea
	 *            the geoarea to set
	 */
	public void setGeoArea(String geoarea) {
		this.geoarea = geoarea;
	}

	public List<SpecificMeasure> getSpecificMeasureList() {
		return specificMeasureList;
	}

	public void setSpecificMeasureList(List<SpecificMeasure> specificMeasureList) {
		this.specificMeasureList = specificMeasureList;
	}

	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + ((areaType == null) ? 0 : areaType.hashCode());
	// result = prime * result + ((criteria == null) ? 0 : criteria.hashCode());
	// result = prime * result + ((geoRefList == null) ? 0 : geoRefList.hashCode());
	// result = prime * result + ((geoform == null) ? 0 : geoform.hashCode());
	// result = prime * result + ((id == null) ? 0 : id.hashCode());
	// result = prime * result + ((inventoryIdentifier == null) ? 0 : inventoryIdentifier.hashCode());
	// result = prime * result + ((name == null) ? 0 : name.hashCode());
	// result = prime * result + ((profileList == null) ? 0 : profileList.hashCode());
	// result = prime * result + ((rfmo == null) ? 0 : rfmo.hashCode());
	// result = prime * result + ((specificMeasureList == null) ? 0 : specificMeasureList.hashCode());
	// result = prime * result + ((validityPeriod == null) ? 0 : validityPeriod.hashCode());
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
		if (!(obj instanceof Vme)) {
			return false;
		}
		Vme other = (Vme) obj;
		if (areaType == null) {
			if (other.areaType != null) {
				return false;
			}
		} else if (!areaType.equals(other.areaType)) {
			return false;
		}
		if (criteria == null) {
			if (other.criteria != null) {
				return false;
			}
		} else if (!criteria.equals(other.criteria)) {
			return false;
		}
		if (geoRefList == null) {
			if (other.geoRefList != null) {
				return false;
			}
		} else if (!geoRefList.equals(other.geoRefList)) {
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
		if (inventoryIdentifier == null) {
			if (other.inventoryIdentifier != null) {
				return false;
			}
		} else if (!inventoryIdentifier.equals(other.inventoryIdentifier)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (profileList == null) {
			if (other.profileList != null) {
				return false;
			}
		} else if (!profileList.equals(other.profileList)) {
			return false;
		}
		if (rfmo == null) {
			if (other.rfmo != null) {
				return false;
			}
		} else if (!rfmo.equals(other.rfmo)) {
			return false;
		}
		if (specificMeasureList == null) {
			if (other.specificMeasureList != null) {
				return false;
			}
		} else if (!specificMeasureList.equals(other.specificMeasureList)) {
			return false;
		}
		if (validityPeriod == null) {
			if (other.validityPeriod != null) {
				return false;
			}
		} else if (!validityPeriod.equals(other.validityPeriod)) {
			return false;
		}
		return true;
	}

}
