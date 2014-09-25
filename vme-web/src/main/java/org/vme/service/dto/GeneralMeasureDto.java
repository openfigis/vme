package org.vme.service.dto;


public class GeneralMeasureDto {

	private int year;
	private int validityPeriodStart;
	private int validityPeriodEnd;
	private String fishingArea;
	private String exploratoryFishingProtocol;
	private String vmeEncounterProtocol;
	private String vmeIndicatorSpecies;
	private String threshold;
	private String factsheetURL;
	
	public GeneralMeasureDto(){
		
	}	

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getValidityPeriodStart() {
		return this.validityPeriodStart;
	}

	public void setValidityPeriodStart(int validityPeriodStart) {
		this.validityPeriodStart = validityPeriodStart;
	}

	public int getValidityPeriodEnd() {
		return this.validityPeriodEnd;
	}

	public void setValidityPeriodEnd(int validityPeriodEnd) {
		this.validityPeriodEnd = validityPeriodEnd;
	}

	public String getFishingArea() {
		return this.fishingArea;
	}

	public void setFishingArea(String fishingArea) {
		this.fishingArea = fishingArea;
	}

	public String getExploratoryFishingProtocol() {
		return this.exploratoryFishingProtocol;
	}

	public void setExploratoryFishingProtocol(String exploratoryFishingProtocol) {
		this.exploratoryFishingProtocol = exploratoryFishingProtocol;
	}

	public String getVmeEncounterProtocol() {
		return this.vmeEncounterProtocol;
	}

	public void setVmeEncounterProtocol(String vmeEncounterProtocol) {
		this.vmeEncounterProtocol = vmeEncounterProtocol;
	}

	public String getVmeIndicatorSpecies() {
		return this.vmeIndicatorSpecies;
	}

	public void setVmeIndicatorSpecies(String vmeIndicatorSpecies) {
		this.vmeIndicatorSpecies = vmeIndicatorSpecies;
	}

	public String getThreshold() {
		return this.threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getFactsheetURL() {
		return this.factsheetURL;
	}

	public void setFactsheetURL(String factsheetURL) {
		this.factsheetURL = factsheetURL;
	}
	
}
