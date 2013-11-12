package org.vme.service.dto.obs;


public class ObservationDto {


	private long  vmeId;	
	private String localName;
	private String inventoryIdentifier;
	private String factsheetUrl;
	private String geoArea;
	private String owner;
	private String vmeType;
	private String geographicFeatureId;
	private int year;
	private String envelope;
	private int validityPeriodFrom;
	private int validityPeriodTo;



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
	 * @return the inventoryIdentifier
	 */
	public String getInventoryIdentifier() {
		return inventoryIdentifier;
	}

	/**
	 * @param inventoryIdentifier the inventoryIdentifier to set
	 */
	public void setInventoryIdentifier(String inventoryIdentifier) {
		this.inventoryIdentifier = inventoryIdentifier;
	}

	/**
	 * @return the geographicLayerId
	 */
	public String getgeographicFeatureId() {
		return geographicFeatureId;
	}

	/**
	 * @param geographicLayerId the geographicLayerId to set
	 */
	public void setGeographicFeatureId(String geographicFeatureId) {
		this.geographicFeatureId = geographicFeatureId;
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
	 * @return the validityPeriodFrom 
	 */
	public int getValidityPeriodFrom() {
		return validityPeriodFrom;
	}

	/**
	 * @param validityPeriod the validityPeriodFrom to set
	 */
	public void setValidityPeriodFrom(int validityPeriodFrom) {
		this.validityPeriodFrom = validityPeriodFrom;
	}

	
	/**
	 * @return the validityPeriodTo 
	 */
	public int getValidityPeriodTo() {
		return validityPeriodTo;
	}

	/**
	 * @param validityPeriod the validityPeriodTo to set
	 */
	public void setValidityPeriodTo(int validityPeriodTo) {
		this.validityPeriodTo = validityPeriodTo;
	}
	
	
}
