package org.fao.fi.vme;

public class Vme {

	private int id;
	private int year;
	private int rfbId;
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getRfbId() {
		return rfbId;
	}

	public void setRfbId(int rfbId) {
		this.rfbId = rfbId;
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
