package org.fao.fi.vme.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity(name = "GEO_REF")
public class GeoRef {

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
	private BigDecimal geographicFeatureID;

	/**
	 * 
	 */
	@ManyToOne
	private Vme vme;

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public BigDecimal getGeographicFeatureID() {
		return geographicFeatureID;
	}

	public void setGeographicFeatureID(BigDecimal geographicFeatureID) {
		this.geographicFeatureID = geographicFeatureID;
	}

	public Vme getVme() {
		return vme;
	}

	public void setVme(Vme vme) {
		this.vme = vme;
	}

}
