package org.fao.fi.vme.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
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
	private int id;

	/**
	 * This VME is managed by this Rfmo
	 */
	@ManyToOne
	private Rfmo rfmo;

	@OneToMany(mappedBy = "vme")
	private List<SpecificMeasures> specificMeasuresList;

	@OneToMany(mappedBy = "vme")
	private List<VmeFeatures> vmeFeaturesList;;

	/** */
	private ValidityPeriod validityPeriod;

	/** */
	private String name;

	/** */
	private String geoform;

	/** */
	private String geographicLayerId;

	/** */
	private String areaType;

	/** */
	private String criteria;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Rfmo getRfmo() {
		return rfmo;
	}

	public void setRfmo(Rfmo rfmo) {
		this.rfmo = rfmo;
	}

	public List<SpecificMeasures> getSpecificMeasuresList() {
		return specificMeasuresList;
	}

	public void setSpecificMeasuresList(List<SpecificMeasures> specificMeasuresList) {
		this.specificMeasuresList = specificMeasuresList;
	}

	public List<VmeFeatures> getVmeFeaturesList() {
		return vmeFeaturesList;
	}

	public void setVmeFeaturesList(List<VmeFeatures> vmeFeaturesList) {
		this.vmeFeaturesList = vmeFeaturesList;
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

	public String getGeographicLayerId() {
		return geographicLayerId;
	}

	public void setGeographicLayerId(String geographicLayerId) {
		this.geographicLayerId = geographicLayerId;
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
