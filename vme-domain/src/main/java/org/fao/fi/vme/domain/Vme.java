package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	 * A VME is managed by this Rfmo
	 */
	@ManyToOne
	public Rfmo rfmo;

	/** */
	public ValidityPeriod validityPeriod = new ValidityPeriod();

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

	/** */
	private String descriptionPhisical;

	/** */
	private String descriptionBiological;

	/** */
	private String descriptionImpact;

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

	public String getDescriptionPhisical() {
		return descriptionPhisical;
	}

	public void setDescriptionPhisical(String descriptionPhisical) {
		this.descriptionPhisical = descriptionPhisical;
	}

	public String getDescriptionBiological() {
		return descriptionBiological;
	}

	public void setDescriptionBiological(String descriptionBiological) {
		this.descriptionBiological = descriptionBiological;
	}

	public String getDescriptionImpact() {
		return descriptionImpact;
	}

	public void setDescriptionImpact(String descriptionImpact) {
		this.descriptionImpact = descriptionImpact;
	}

}
