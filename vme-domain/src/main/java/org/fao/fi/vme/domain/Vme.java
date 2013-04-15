package org.fao.fi.vme.domain;

import java.util.List;

/**
 * 
 * @author Erik van Ingen
 * 
 */
public class Vme {

	private int id;
	private int year;
	private Rfb rfb;

	private GeneralMeasures generalMeasures;
	private SpecificMeasures specificMeasures;
	private List<FishingHistory> fishingHistoryList;
	private List<Meeting> meetingList;
	private ValidityPeriod validityPeriod;
	private String geoform;
	private String geogArea1;
	private String geogArea2;
	private String geogAreaFAO;
	private String coord;
	private String areaType;
	private String status;
	private String descriptionPhysical;
	private String descriptionBiology;
	private String descriptionImpact;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<FishingHistory> getFishingHistoryList() {
		return fishingHistoryList;
	}

	public void setFishingHistoryList(List<FishingHistory> fishingHistoryList) {
		this.fishingHistoryList = fishingHistoryList;
	}

	public List<Meeting> getMeetingList() {
		return meetingList;
	}

	public void setMeetingList(List<Meeting> meetingList) {
		this.meetingList = meetingList;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Rfb getRfb() {
		return rfb;
	}

	public void setRfb(Rfb rfb) {
		this.rfb = rfb;
	}

	public ValidityPeriod getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public String getGeoform() {
		return geoform;
	}

	public void setGeoform(String geoform) {
		this.geoform = geoform;
	}

	public String getGeogArea1() {
		return geogArea1;
	}

	public void setGeogArea1(String geogArea1) {
		this.geogArea1 = geogArea1;
	}

	public String getGeogArea2() {
		return geogArea2;
	}

	public void setGeogArea2(String geogArea2) {
		this.geogArea2 = geogArea2;
	}

	public String getGeogAreaFAO() {
		return geogAreaFAO;
	}

	public void setGeogAreaFAO(String geogAreaFAO) {
		this.geogAreaFAO = geogAreaFAO;
	}

	public String getCoord() {
		return coord;
	}

	public void setCoord(String coord) {
		this.coord = coord;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescriptionPhysical() {
		return descriptionPhysical;
	}

	public void setDescriptionPhysical(String descriptionPhysical) {
		this.descriptionPhysical = descriptionPhysical;
	}

	public String getDescriptionBiology() {
		return descriptionBiology;
	}

	public void setDescriptionBiology(String descriptionBiology) {
		this.descriptionBiology = descriptionBiology;
	}

	public String getDescriptionImpact() {
		return descriptionImpact;
	}

	public void setDescriptionImpact(String descriptionImpact) {
		this.descriptionImpact = descriptionImpact;
	}

}
