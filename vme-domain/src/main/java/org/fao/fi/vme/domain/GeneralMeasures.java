package org.fao.fi.vme.domain;

import javax.persistence.Embeddable;

@Embeddable
public class GeneralMeasures {

	private ValidityPeriod measureValidityPeriod;
	private String rfbFishingAreas;
	private String rfbFishingAreasCoord;
	private String encounter;
	private String indicatorSp;
	private String threshold;
	private String linkCemBookmarked;
	private String linkCemSource;

	public ValidityPeriod getMeasureValidityPeriod() {
		return measureValidityPeriod;
	}

	public void setMeasureValidityPeriod(ValidityPeriod measureValidityPeriod) {
		this.measureValidityPeriod = measureValidityPeriod;
	}

	public String getRfbFishingAreas() {
		return rfbFishingAreas;
	}

	public void setRfbFishingAreas(String rfbFishingAreas) {
		this.rfbFishingAreas = rfbFishingAreas;
	}

	public String getRfbFishingAreasCoord() {
		return rfbFishingAreasCoord;
	}

	public void setRfbFishingAreasCoord(String rfbFishingAreasCoord) {
		this.rfbFishingAreasCoord = rfbFishingAreasCoord;
	}

	public String getEncounter() {
		return encounter;
	}

	public void setEncounter(String encounter) {
		this.encounter = encounter;
	}

	public String getIndicatorSp() {
		return indicatorSp;
	}

	public void setIndicatorSp(String indicatorSp) {
		this.indicatorSp = indicatorSp;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getLinkCemBookmarked() {
		return linkCemBookmarked;
	}

	public void setLinkCemBookmarked(String linkCemBookmarked) {
		this.linkCemBookmarked = linkCemBookmarked;
	}

	public String getLinkCemSource() {
		return linkCemSource;
	}

	public void setLinkCemSource(String linkCemSource) {
		this.linkCemSource = linkCemSource;
	}

}
