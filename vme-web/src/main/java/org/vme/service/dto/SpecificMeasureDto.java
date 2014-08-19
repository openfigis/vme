package org.vme.service.dto;

import java.io.Serializable;
import java.util.Date;

public class SpecificMeasureDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5049759243702243577L;

	private String text;
	private int year;
	private Date validityPeriodStart;
	private Date validityPeriodEnd;
	private int reviewYear;
	private String sourceURL;
	private String factsheetURL;

	public SpecificMeasureDto() {

	}

	public String getFactsheetURL() {
		return this.factsheetURL;
	}

	public void setFactsheetURL(String factsheetURL) {
		this.factsheetURL = factsheetURL;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Date getValidityPeriodStart() {
		return this.validityPeriodStart;
	}

	public void setValidityPeriodStart(Date validityPeriodStart) {
		this.validityPeriodStart = validityPeriodStart;
	}

	public Date getValidityPeriodEnd() {
		return this.validityPeriodEnd;
	}

	public void setValidityPeriodEnd(Date validityPeriodEnd) {
		this.validityPeriodEnd = validityPeriodEnd;
	}

	public String getSourceURL() {
		return this.sourceURL;
	}

	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}

	public int getReviewYear() {
		return this.reviewYear;
	}

	public void setReviewYear(int reviewYear) {
		this.reviewYear = reviewYear;
	}
}
