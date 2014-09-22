package org.fao.fi.vme.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.model.reference.VmeCriteria;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.fao.fi.vme.domain.model.reference.VmeType;
import org.fao.fi.vme.domain.support.MultiLingualStringConverter;
import org.fao.fi.vme.domain.util.YearComparator;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReport;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGMandatory;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGManyAmong;
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
public class Vme implements ObjectId<Long>, Report, Serializable, Period {

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
	@OneToMany(mappedBy = "vme", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<SpecificMeasure> specificMeasureList;

	@RSGName("VME Profiles")
	@RSGWeight(3)
	@RSGSection
	@OneToMany(mappedBy = "vme", cascade = CascadeType.ALL)
	private List<Profile> profileList;

	@RSGName("VME Map Reference")
	@RSGWeight(5)
	@RSGSection
	@OneToMany(mappedBy = "vme", cascade = CascadeType.ALL)
	private List<GeoRef> geoRefList;

	/**
	 * This validity period on the level of the reference object and applies to
	 * the VME itself. It has noting to do with the reporting year.
	 */
	@RSGName("Validity Period")
	@RSGWeight(1)
	private ValidityPeriod validityPeriod;

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

	@RSGName(value = "Vme Scope", hideHeading = true)
	@RSGMandatory
	@RSGOneAmong(concept = VmeScope.class, label = ConceptData.NAME, value = ConceptData.ID)
	@RSGConverter(LongDataConverter.class)
	@RSGWeight(2)
	private Long scope;

	@RSGName(value = "Area Type", hideHeading = true)
	@RSGMandatory
	@RSGOneAmong(concept = VmeType.class, label = ConceptData.NAME, value = ConceptData.ID)
	@RSGConverter(LongDataConverter.class)
	@RSGWeight(2)
	private Long areaType;

	@RSGName(value = "Criteria", hideHeading = true)
	@RSGMandatory
	@RSGManyAmong(concept = VmeCriteria.class, label = ConceptData.NAME, value = ConceptData.ID)
	@RSGConverter(LongDataConverter.class)
	@RSGWeight(2)
	@ElementCollection
	@CollectionTable(name = "VME_TO_CRITERIA", joinColumns = @JoinColumn(name = "VME_ID"))
	@Column(name = "CRITERIA_ID")
	private List<Long> criteria;

	@RSGName("Inventory identifier")
	@RSGConverter(StringDataConverter.class)
	@RSGMandatory
	private String inventoryIdentifier;

	@RSGName("Media Reference")
	@RSGWeight(6)
	@RSGSection
	@OneToMany(mappedBy = "vme", cascade = CascadeType.ALL)
	private List<MediaReference> mediaReferenceList;

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

	/**
	 * @return the 'areaType' value
	 */
	public Long getAreaType() {
		return this.areaType;
	}

	/**
	 * @param areaType
	 *            the 'areaType' value to set
	 */
	public void setAreaType(Long areaType) {
		this.areaType = areaType;
	}

	/**
	 * @return the 'criteria' value
	 */
	public List<Long> getCriteria() {
		return this.criteria;
	}

	/**
	 * @param criteria
	 *            the 'criteria' value to set
	 */
	public void setCriteria(List<Long> criteria) {
		this.criteria = criteria;
	}

	/**
	 * @return the geoarea
	 */
	public MultiLingualString getGeoArea() {
		return geoArea;
	}

	public Long getScope() {
		return scope;
	}

	public void setScope(Long scope) {
		this.scope = scope;
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

	public List<MediaReference> getMediaReferenceList() {
		return mediaReferenceList;
	}

	public void setMediaReferenceList(List<MediaReference> mediaReferenceList) {
		this.mediaReferenceList = mediaReferenceList;
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

		Vme other = (Vme) obj;

		if (this.geoRefList == null && other.geoRefList != null || this.geoRefList != null && other.geoRefList == null) {
			return false;
		} else if (this.geoRefList.hashCode() != other.geoRefList.hashCode()) {
			return false;
		}

		if (this.id == null && other.id != null || this.id != null && other.id == null) {
			return false;
		} else if (!this.id.equals(other.id)) {
			return false;
		}

		if (this.scope == null && other.scope != null) {
			return false;
		} else if (this.scope != other.scope) {
			return false;
		}

		if (this.areaType == null && other.areaType != null || this.areaType != null && other.areaType == null) {
			return false;
		} else if (!this.areaType.equals(other.areaType)) {
			return false;
		}

		if (this.criteria == null && other.criteria != null || this.criteria != null && other.criteria == null) {
			return false;
		} else if (!this.criteria.equals(other.criteria)) {
			return false;
		}

		if (this.geoArea == null && other.geoArea != null || this.geoArea != null && other.geoArea == null) {
			return false;
		} else if (!this.geoArea.equals(other.geoArea)) {
			return false;
		}

		if (this.inventoryIdentifier == null && other.inventoryIdentifier != null || this.inventoryIdentifier != null
				&& other.inventoryIdentifier == null) {
			return false;
		} else if (!this.inventoryIdentifier.equals(other.inventoryIdentifier)) {
			return false;
		}

		if (this.name == null && other.name != null || this.name != null && other.name == null) {
			return false;
		} else if (!this.name.equals(other.name)) {
			return false;
		}

		if (this.profileList == null && other.profileList != null) {
			return false;
		} else if (!this.profileList.equals(other.profileList)) {
			return false;
		}

		if (this.rfmo == null && other.rfmo != null || this.rfmo != null && other.rfmo == null) {
			return false;
		} else if (!this.rfmo.equals(other.rfmo)) {
			return false;
		}

		if (this.specificMeasureList == null && other.specificMeasureList != null || this.specificMeasureList != null
				&& other.specificMeasureList == null) {
			return false;
		} else if (!this.specificMeasureList.equals(other.specificMeasureList)) {
			return false;
		}

		if (this.validityPeriod == null && other.validityPeriod != null || this.validityPeriod != null
				&& other.validityPeriod == null) {
			return false;
		} else if (!this.validityPeriod.equals(other.validityPeriod)) {
			return false;
		}

		return true;
	}

}
