package org.fao.fi.vme.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class Vme {

	@Id
	private Long id;

	/**
	 * This VME is managed by this Rfmo
	 */
	@ManyToOne
	private Rfmo rfmo;

	@ManyToMany
	@JoinTable(name = "VME_SPECIFIC_MEASURES", //
	joinColumns = { @JoinColumn(name = "VME_ID", referencedColumnName = "ID") }, //
	inverseJoinColumns = { @JoinColumn(name = "SPECIFIC_MEASURES_ID", referencedColumnName = "ID") })
	private List<SpecificMeasures> specificMeasureList;

	@OneToMany
	private List<Profile> vmeFeaturesList;;

	@OneToMany
	private List<GeoRef> geoRefList;;

	@OneToMany
	private List<History> vmeHistoryList;

	/**
	 *  
	 */
	private ValidityPeriod validityPeriod;

	/**
	 *
	 */
	private String name;

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
	private String criteria;

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

	public List<SpecificMeasures> getSpecificMeasureList() {
		return specificMeasureList;
	}

	public void setSpecificMeasureList(List<SpecificMeasures> specificMeasureList) {
		this.specificMeasureList = specificMeasureList;
	}

	public List<Profile> getVmeFeaturesList() {
		return vmeFeaturesList;
	}

	public void setVmeFeaturesList(List<Profile> vmeFeaturesList) {
		this.vmeFeaturesList = vmeFeaturesList;
	}

	public List<GeoRef> getGeoRefList() {
		return geoRefList;
	}

	public void setGeoRefList(List<GeoRef> geoRefList) {
		this.geoRefList = geoRefList;
	}

	public List<History> getVmeHistoryList() {
		return vmeHistoryList;
	}

	public void setVmeHistoryList(List<History> vmeHistoryList) {
		this.vmeHistoryList = vmeHistoryList;
	}

	public ValidityPeriod getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
