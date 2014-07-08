package org.vme.service.dto;


public class SpecificMeasureDto {
	
	public String text;
	public int year;
	public int validityPeriodStart;
	public int validityPeriodEnd;
	public String sourceURL;
	public String factsheetURL;
	
	public SpecificMeasureDto() {
		
	}
	
	public String getFactsheetURL() {
		return factsheetURL;
	}

	public void setFactsheetURL(String factsheetURL) {
		this.factsheetURL = factsheetURL;
	}

	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public int getValidityPeriodStart() {
		return validityPeriodStart;
	}


	public void setValidityPeriodStart(int validityPeriodStart) {
		this.validityPeriodStart = validityPeriodStart;
	}


	public int getValidityPeriodEnd() {
		return validityPeriodEnd;
	}


	public void setValidityPeriodEnd(int validityPeriodEnd) {
		this.validityPeriodEnd = validityPeriodEnd;
	}


	public String getSourceURL() {
		return sourceURL;
	}


	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}
}
