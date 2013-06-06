package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The features of a VME, defined in a certain year.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@Entity(name = "VME_FEATURES")
public class VmeFeatures {

	@Id
	private long id;

	/**
	 * Year in which the features where defined.
	 */
	private long year;

	/**
	 * Physical description of the VME
	 * 
	 */
	private String descriptionPhisical;

	/**
	 * 
	 * Biological description of the VME
	 */
	private String descriptionBiological;

	/**
	 * Description of the impact of this VME
	 */
	private String descriptionImpact;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
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
