package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class GeneralMeasures extends Observation {

	/** 
	 * 
	 */
	@Id
	@GeneratedValue
	private int generalMeasuresId;

	/** */
	private String rfbFishingAreas;

	/** */
	private String encounter;

	/** */
	private String indicatorSpecies;

	/** */
	private String threshold;

	/** */
	private ValidityPeriod validityPeriod;

	/**
	 * GeneralMeasures are defined on the level of a RFMO.
	 */
	private Rfmo rfmo;

	/** */
	private Source linkCemSource;

	/** */
	private Vme primairlyConcernedVme;
}
