package org.vme.service.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class VmeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8974944086791645381L;

	private long vmeId;
	private String scope;
	private String localName;
	private String inventoryIdentifier;
	private String factsheetUrl;
	private String geoArea;
	private String owner;
	private String vmeType;
	private String geographicFeatureId;
	private int year;
	private String envelope;
	private Date validityPeriodFrom;
	private Date validityPeriodTo;

	public String getLocalName() {
		return this.localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	/**
	 * @return the factsheetUrl
	 */
	public String getFactsheetUrl() {
		return this.factsheetUrl;
	}

	/**
	 * @param factsheetUrl
	 *            the factsheetUrl to set
	 */
	public void setFactsheetUrl(String factsheetUrl) {
		this.factsheetUrl = factsheetUrl;
	}

	/**
	 * @return the vmeId
	 */
	public long getVmeId() {
		return this.vmeId;
	}

	/**
	 * @param vmeId
	 *            the vmeId to set
	 */
	public void setVmeId(long vmeId) {
		this.vmeId = vmeId;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the inventoryIdentifier
	 */
	public String getInventoryIdentifier() {
		return this.inventoryIdentifier;
	}

	/**
	 * @param inventoryIdentifier
	 *            the inventoryIdentifier to set
	 */
	public void setInventoryIdentifier(String inventoryIdentifier) {
		this.inventoryIdentifier = inventoryIdentifier;
	}

	/**
	 * @return the geographicLayerId
	 */
	public String getgeographicFeatureId() {
		return this.geographicFeatureId;
	}

	/**
	 * @param geographicLayerId
	 *            the geographicLayerId to set
	 */
	public void setGeographicFeatureId(String geographicFeatureId) {
		this.geographicFeatureId = geographicFeatureId;
	}

	/**
	 * @return the geoArea
	 */
	public String getGeoArea() {
		return this.geoArea;
	}

	/**
	 * @param geoArea
	 *            the geoArea to set
	 */
	public void setGeoArea(String geoArea) {
		this.geoArea = geoArea;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return this.owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the vmeType
	 */
	public String getVmeType() {
		return this.vmeType;
	}

	/**
	 * @param vmeType
	 *            the vmeType to set
	 */
	public void setVmeType(String vmeType) {
		this.vmeType = vmeType;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the envelope
	 */
	public String getEnvelope() {
		return this.envelope;
	}

	/**
	 * @param envelope
	 *            the envelope to set
	 */
	public void setEnvelope(String envelope) {
		this.envelope = envelope;
	}

	/**
	 * @return the validityPeriodFrom
	 */
	public Date getValidityPeriodFrom() {
		return this.validityPeriodFrom;
	}

	/**
	 * @param validityPeriod
	 *            the validityPeriodFrom to set
	 */
	public void setValidityPeriodFrom(Date validityPeriodFrom) {
		this.validityPeriodFrom = validityPeriodFrom;
	}

	/**
	 * @return the validityPeriodTo
	 */
	public Date getValidityPeriodTo() {
		return this.validityPeriodTo;
	}

	/**
	 * @param validityPeriod
	 *            the validityPeriodTo to set
	 */
	public void setValidityPeriodTo(Date validityPeriodTo) {
		this.validityPeriodTo = validityPeriodTo;
	}

}
