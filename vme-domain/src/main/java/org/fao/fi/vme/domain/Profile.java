package org.fao.fi.vme.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * The features of a VME, defined in a certain year.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class Profile {

	@Id
	private Long id;

	/**
	 * Year in which the features where defined. This maps to the figis reporting year.
	 */
	private Long year;

	/**
	 * Physical description of the VME
	 * 
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString descriptionPhisical;

	/**
	 * 
	 * Biological description of the VME
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString descriptionBiological;

	/**
	 * Description of the impact of this VME
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString descriptionImpact;

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
	}

	public MultiLingualString getDescriptionPhisical() {
		return descriptionPhisical;
	}

	public void setDescriptionPhisical(MultiLingualString descriptionPhisical) {
		this.descriptionPhisical = descriptionPhisical;
	}

	public MultiLingualString getDescriptionBiological() {
		return descriptionBiological;
	}

	public void setDescriptionBiological(MultiLingualString descriptionBiological) {
		this.descriptionBiological = descriptionBiological;
	}

	public MultiLingualString getDescriptionImpact() {
		return descriptionImpact;
	}

	public void setDescriptionImpact(MultiLingualString descriptionImpact) {
		this.descriptionImpact = descriptionImpact;
	}

}
