package org.fao.fi.vme.domain;

import java.util.ArrayList;
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
	 * A VME is managed by this Rfmo
	 */
	@ManyToOne
	private Rfmo rfmo;

	@OneToMany(mappedBy = "vme")
	private List<SpecificMeasures> specificMeasuresList = new ArrayList<SpecificMeasures>();;

	/** */
	private ValidityPeriod validityPeriod = new ValidityPeriod();

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

	public List<SpecificMeasures> getSpecificMeasuresList() {
		return specificMeasuresList;
	}

	public void setSpecificMeasuresList(List<SpecificMeasures> specificMeasuresList) {
		this.specificMeasuresList = specificMeasuresList;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vme other = (Vme) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
