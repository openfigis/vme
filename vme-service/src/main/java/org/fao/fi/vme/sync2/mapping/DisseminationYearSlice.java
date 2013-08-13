package org.fao.fi.vme.sync2.mapping;

import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.History;
import org.fao.fi.vme.domain.Profile;
import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.Vme;

/**
 * 
 * Hold the slice of information for one disseminationYear.
 * 
 * This information can come from Period objects or from Year objects.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class DisseminationYearSlice {

	// period object
	private int year;

	// period object
	private Vme vme;

	// period object
	private GeneralMeasures generalMeasures;

	// period object
	private SpecificMeasures specificMeasures;

	// year object
	private Profile profile;

	// year object
	private History vmeHistory;

	// year object
	private History rfmoHistory;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Vme getVme() {
		return vme;
	}

	public void setVme(Vme vme) {
		this.vme = vme;
	}

	public GeneralMeasures getGeneralMeasures() {
		return generalMeasures;
	}

	public void setGeneralMeasures(GeneralMeasures generalMeasures) {
		this.generalMeasures = generalMeasures;
	}

	public SpecificMeasures getSpecificMeasures() {
		return specificMeasures;
	}

	public void setSpecificMeasures(SpecificMeasures specificMeasures) {
		this.specificMeasures = specificMeasures;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public History getVmeHistory() {
		return vmeHistory;
	}

	public void setVmeHistory(History vmeHistory) {
		this.vmeHistory = vmeHistory;
	}

	public History getRfmoHistory() {
		return rfmoHistory;
	}

	public void setRfmoHistory(History rfmoHistory) {
		this.rfmoHistory = rfmoHistory;
	}

}
