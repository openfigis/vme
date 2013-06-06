package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 
 * The source of information available on the level of an RFMO.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class InformationSource {

	@Id
	private long id;

	/**
	 * 
	 */
	@OneToOne
	private Meeting producedOnMeeting;

	/**
	 * 
	 */
	private Source formalSource;

	/**
	 * 
	 */
	@OneToOne
	private SpecificMeasures specificMeasures;

	/**
	 * 
	 */
	@OneToOne
	private GeneralMeasures generalMeasures;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Meeting getProducedOnMeeting() {
		return producedOnMeeting;
	}

	public void setProducedOnMeeting(Meeting producedOnMeeting) {
		this.producedOnMeeting = producedOnMeeting;
	}

	public Source getFormalSource() {
		return formalSource;
	}

	public void setFormalSource(Source formalSource) {
		this.formalSource = formalSource;
	}

	public SpecificMeasures getSpecificMeasures() {
		return specificMeasures;
	}

	public void setSpecificMeasures(SpecificMeasures specificMeasures) {
		this.specificMeasures = specificMeasures;
	}

	public GeneralMeasures getGeneralMeasures() {
		return generalMeasures;
	}

	public void setGeneralMeasures(GeneralMeasures generalMeasures) {
		this.generalMeasures = generalMeasures;
	}

}
