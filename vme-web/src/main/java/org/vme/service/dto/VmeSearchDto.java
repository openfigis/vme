package org.vme.service.dto;


public class VmeSearchDto {


	
	private String localName;
	
	private String factsheetUrl;
	private long  vmeId;
	private String geoArea;
	private String owner;
	private String vmeType;
	private String geographicLayerId;
	private int year;
	private String envelope;
	private String validityPeriod;



	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	/**
	 * @return the factsheetUrl
	 */
	public String getFactsheetUrl() {
		return factsheetUrl;
	}

	/**
	 * @param factsheetUrl the factsheetUrl to set
	 */
	public void setFactsheetUrl(String factsheetUrl) {
		this.factsheetUrl = factsheetUrl;
	}

	/**
	 * @return the vmeId
	 */
	public long getVmeId() {
		return vmeId;
	}

	/**
	 * @param vmeId the vmeId to set
	 */
	public void setVmeId(long vmeId) {
		this.vmeId = vmeId;
	}

	
	
	
	/**
	 * @return the geographicLayerId
	 */
	public String getGeographicLayerId() {
		return geographicLayerId;
	}

	/**
	 * @param geographicLayerId the geographicLayerId to set
	 */
	public void setGeographicLayerId(String geographicLayerId) {
		this.geographicLayerId = geographicLayerId;
	}

	/**
	 * @return the geoArea
	 */
	public String getGeoArea() {
		return geoArea;
	}

	/**
	 * @param geoArea the geoArea to set
	 */
	public void setGeoArea(String geoArea) {
		this.geoArea = geoArea;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the vmeType
	 */
	public String getVmeType() {
		return vmeType;
	}

	/**
	 * @param vmeType the vmeType to set
	 */
	public void setVmeType(String vmeType) {
		this.vmeType = vmeType;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the envelope
	 */
	public String getEnvelope() {
		return envelope;
	}

	/**
	 * @param envelope the envelope to set
	 */
	public void setEnvelope(String envelope) {
		this.envelope = envelope;
	}

	/**
	 * @return the validityPeriod
	 */
	public String getValidityPeriod() {
		return validityPeriod;
	}

	/**
	 * @param validityPeriod the validityPeriod to set
	 */
	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	
	
	
	
}
