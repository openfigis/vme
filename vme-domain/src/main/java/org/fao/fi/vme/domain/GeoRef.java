package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity(name = "GEO_REF")
public class GeoRef {

	/**
	 * 
	 */
	private int year;

	/**
	 * 
	 */
	private String geographicFeatureID;

	/**
	 * 
	 */
	@ManyToOne
	private Vme vme;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
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

}
