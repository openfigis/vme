package org.fao.fi.vme.domain;

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
	@OneToOne
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

}
