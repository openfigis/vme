package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class SpecificMeasures extends Observation {

	/**
	 *  
	 */
	@Id
	private int specificMeasuresId;

	/** 
	 */
	private String measureSummary;

	/** */
	public ValidityPeriod validityPeriod;

	/**
	 * The SpecificMeasures are defined on the level of this VME
	 */
	public Vme vme;

	/**
	 * Where the document of the SpecificMeasures is to be found.
	 */
	public Source document;
}
