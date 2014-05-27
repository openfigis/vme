package org.fao.fi.vme.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.support.MultiLingualStringConverter;
import org.fao.fi.vme.domain.util.YearComparator;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReport;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGMandatory;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGName;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGOneAmong;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGSection;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGSimpleReference;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGWeight;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.constants.ConceptData;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.LongDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.StringDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.Report;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
@RSGReport(name = "Vulnerable Marine Ecosystem")
public class Vme implements ObjectId<Long>, Report, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8030400189803452093L;

	@RSGIdentifier
	@RSGConverter(LongDataConverter.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * This VME is managed by this Rfmo
	 */
	@RSGName("Authority")
	@RSGSimpleReference
	@ManyToOne(fetch = FetchType.EAGER)
	private Rfmo rfmo;

	/*
	 * This is the owning side of the manyToMany relationship
	 */
	@RSGName("VME Specific Measures")
	@RSGWeight(4)
	@RSGSection
	@OneToMany(mappedBy = "vme", cascade = CascadeType.ALL)
	private List<SpecificMeasure> specificMeasureList;

	@RSGName("VME Profiles")
	@RSGWeight(3)
	@RSGSection
	@OneToMany(mappedBy = "vme", cascade = CascadeType.ALL)
	private List<Profile> profileList;

	@RSGName("VME Map Reference")
	@RSGWeight(5)
	@RSGSection
	@OneToMany(cascade = { CascadeType.ALL })
	private List<GeoRef> geoRefList;

	@RSGName("Vme Criteria")
	@RSGWeight(6)
	@RSGSection
	@OneToMany(cascade = { CascadeType.ALL })
	private List<Criteria> criteriaList;

	/**
	 * This validity period on the level of the reference object and applies to
	 * the VME itself. It has noting to do with the reporting year.
	 * 
	 * 
	 */
	@RSGName("Validity Period")
	@RSGWeight(1)
	private ValidityPeriod validityPeriod;

	/**
	 *
	 */
	@RSGName("VME Name")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGMandatory
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString name;

	@RSGName("Geographic reference")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(1)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString geoArea;

	@RSGName("Area Type")
	@RSGMandatory
	@RSGOneAmong(concept = VmeType.class, label = ConceptData.NAME, value = ConceptData.NAME)
	@RSGConverter(StringDataConverter.class)
	@RSGWeight(2)
	private String areaType;

	/**
	 *
	 */
	@RSGName("Criteria")
	@RSGMandatory
	@RSGOneAmong(concept = VmeCriteria.class, label = ConceptData.NAME, value = ConceptData.NAME)
	@RSGConverter(StringDataConverter.class)
	@RSGWeight(2)
	private String criteria;

	@RSGName("Inventory identifier")
	@RSGConverter(StringDataConverter.class)
	@RSGMandatory
	private String inventoryIdentifier;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

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

	public Rfmo getRfmo() {
		return rfmo;
	}

	public void setRfmo(Rfmo rfmo) {
		this.rfmo = rfmo;
	}

	public List<Profile> getProfileList() {
		return YearComparator.sort(profileList);
	}

	public void setProfileList(List<Profile> profileList) {
		this.profileList = YearComparator.sort(profileList);
	}

	public List<GeoRef> getGeoRefList() {
		return YearComparator.sort(geoRefList);
	}

	public void setGeoRefList(List<GeoRef> geoRefList) {
		this.geoRefList = YearComparator.sort(geoRefList);
	}

	public ValidityPeriod getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
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
	public MultiLingualString getGeoArea() {
		return geoArea;
	}

	/**
	 * @param geoArea
	 *            the geoarea to set
	 */
	public void setGeoArea(MultiLingualString geoArea) {
		this.geoArea = geoArea;
	}

	public List<SpecificMeasure> getSpecificMeasureList() {
		return YearComparator.sort(specificMeasureList);
	}

	public void setSpecificMeasureList(List<SpecificMeasure> specificMeasureList) {
		this.specificMeasureList = YearComparator.sort(specificMeasureList);
	}

	// /* (non-Javadoc)
	// * @see java.lang.Object#hashCode()
	// */
	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + ((this.areaType == null) ? 0 :
	// this.areaType.hashCode());
	// result = prime * result + ((this.criteria == null) ? 0 :
	// this.criteria.hashCode());
	// result = prime * result + ((this.geoRefList == null) ? 0 :
	// this.geoRefList.hashCode());
	// result = prime * result + ((this.geoarea == null) ? 0 :
	// this.geoarea.hashCode());
	// result = prime * result + ((this.geoform == null) ? 0 :
	// this.geoform.hashCode());
	// result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
	// result = prime * result + ((this.inventoryIdentifier == null) ? 0 :
	// this.inventoryIdentifier.hashCode());
	// result = prime * result + ((this.name == null) ? 0 :
	// this.name.hashCode());
	// result = prime * result + ((this.profileList == null) ? 0 :
	// this.profileList.hashCode());
	// result = prime * result + ((this.rfmo == null) ? 0 :
	// this.rfmo.hashCode());
	// result = prime * result + ((this.specificMeasureList == null) ? 0 :
	// this.specificMeasureList.hashCode());
	// result = prime * result + ((this.validityPeriod == null) ? 0 :
	// this.validityPeriod.hashCode());
	// return result;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		Vme other = (Vme) obj;
		if (this.areaType == null) {
			if (other.areaType != null) {
				return false;
			}
		} else if (!this.areaType.equals(other.areaType)) {
			return false;
		}
		if (this.criteria == null) {
			if (other.criteria != null) {
				return false;
			}
		} else if (!this.criteria.equals(other.criteria)) {
			return false;
		}
		if (this.geoRefList == null) {
			if (other.geoRefList != null) {
				return false;
			}
		} else if (!this.geoRefList.equals(other.geoRefList)) {
			return false;
		}
		if (this.geoArea == null) {
			if (other.geoArea != null) {
				return false;
			}
		} else if (!this.geoArea.equals(other.geoArea)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.inventoryIdentifier == null) {
			if (other.inventoryIdentifier != null) {
				return false;
			}
		} else if (!this.inventoryIdentifier.equals(other.inventoryIdentifier)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.profileList == null) {
			if (other.profileList != null) {
				return false;
			}
		} else if (!this.profileList.equals(other.profileList)) {
			return false;
		}
		if (this.rfmo == null) {
			if (other.rfmo != null) {
				return false;
			}
		} else if (!this.rfmo.equals(other.rfmo)) {
			return false;
		}
		if (this.specificMeasureList == null) {
			if (other.specificMeasureList != null) {
				return false;
			}
		} else if (!this.specificMeasureList.equals(other.specificMeasureList)) {
			return false;
		}
		if (this.validityPeriod == null) {
			if (other.validityPeriod != null) {
				return false;
			}
		} else if (!this.validityPeriod.equals(other.validityPeriod)) {
			return false;
		}
		return true;
	}

}