package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class Vme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * A VME is managed by this Rfmo
	 */
	public Rfmo rfmo;

	/** */
	public ValidityPeriod validityPeriod;

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

}
