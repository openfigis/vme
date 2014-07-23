package org.fao.fi.vme.domain.dto;

import java.util.Date;

public class VmeDto {

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
		return vmeId;
	}

	/**
	 * @param vmeId
	 *            the vmeId to set
	 */
	public void setVmeId(long vmeId) {
		this.vmeId = vmeId;
	}
	
	public String getScope(){
		return this.scope;
	}
	
	public void setScope(String scope){
		this.scope = scope;
	}

	/**
	 * @return the inventoryIdentifier
	 */
	public String getInventoryIdentifier() {
		return inventoryIdentifier;
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
		return geographicFeatureId;
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
		return geoArea;
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
		return owner;
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
		return vmeType;
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
		return year;
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
		return envelope;
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
		return validityPeriodFrom;
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
		return validityPeriodTo;
	}

	/**
	 * @param validityPeriod
	 *            the validityPeriodTo to set
	 */
	public void setValidityPeriodTo(Date validityPeriodTo) {
		this.validityPeriodTo = validityPeriodTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((envelope == null) ? 0 : envelope.hashCode());
		result = prime * result + ((factsheetUrl == null) ? 0 : factsheetUrl.hashCode());
		result = prime * result + ((geoArea == null) ? 0 : geoArea.hashCode());
		result = prime * result + ((geographicFeatureId == null) ? 0 : geographicFeatureId.hashCode());
		result = prime * result + ((inventoryIdentifier == null) ? 0 : inventoryIdentifier.hashCode());
		result = prime * result + ((localName == null) ? 0 : localName.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + (int) (vmeId ^ (vmeId >>> 32));
		result = prime * result + ((vmeType == null) ? 0 : vmeType.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		VmeDto other = (VmeDto) obj;
		if (envelope == null) {
			if (other.envelope != null) {
				return false;
			}
		} else if (!envelope.equals(other.envelope)) {
			return false;
		}
		if (factsheetUrl == null) {
			if (other.factsheetUrl != null) {
				return false;
			}
		} else if (!factsheetUrl.equals(other.factsheetUrl)) {
			return false;
		}
		if (geoArea == null) {
			if (other.geoArea != null) {
				return false;
			}
		} else if (!geoArea.equals(other.geoArea)) {
			return false;
		}
		if (geographicFeatureId == null) {
			if (other.geographicFeatureId != null) {
				return false;
			}
		} else if (!geographicFeatureId.equals(other.geographicFeatureId)) {
			return false;
		}
		if (inventoryIdentifier == null) {
			if (other.inventoryIdentifier != null) {
				return false;
			}
		} else if (!inventoryIdentifier.equals(other.inventoryIdentifier)) {
			return false;
		}
		if (localName == null) {
			if (other.localName != null) {
				return false;
			}
		} else if (!localName.equals(other.localName)) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		if (validityPeriodFrom != other.validityPeriodFrom) {
			return false;
		}
		if (validityPeriodTo != other.validityPeriodTo) {
			return false;
		}
		if (vmeId != other.vmeId) {
			return false;
		}
		if (vmeType == null) {
			if (other.vmeType != null) {
				return false;
			}
		} else if (!vmeType.equals(other.vmeType)) {
			return false;
		}
		if (year != other.year) {
			return false;
		}
		return true;
	}

}
