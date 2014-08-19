/**
 * 
 */
package org.vme.service.search;

import java.util.UUID;

/**
 * @author Fabrizio Sibeni
 * 
 */
public class ObservationsRequest extends ServiceRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 475204565423829341L;

	/**
	 * Internal Identifier of the requested VME object.
	 */
	private long id;

	/**
	 * Competent authority unique identifier.
	 */
	private int authority;

	/**
	 * VME status unique identifier.
	 */
	private int type;

	/**
	 * VME criteria unique identifier.
	 */
	private int criteria;

	/**
	 * VME request Year .
	 */
	private int year;

	/**
	 * Geographical Identifier of the requested VME object.
	 */
	private String geographicFeatureId;

	/**
	 * Inventory identifier of the requested VME object.
	 */
	private String inventoryIdentifier;

	/**
	 * VME textual field by which the search is performed.
	 */
	private String text;

	/**
	 * @param uuid
	 */
	public ObservationsRequest(UUID uuid) {
		super(uuid);
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the authority
	 */
	public int getAuthority() {
		return authority;
	}

	/**
	 * @return <code>true</code> if request has the Authority defined,
	 *         <code>false</code> otherwise
	 */
	public boolean hasAuthority() {
		return authority > 0;
	}

	/**
	 * @param authority
	 *            the authority to set
	 */
	public void setAuthority(int authority) {
		this.authority = authority;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return <code>true</code> if request has the type defined,
	 *         <code>false</code> otherwise
	 */
	public boolean hasType() {
		return type > 0;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the criteria
	 */
	public int getCriteria() {
		return criteria;
	}

	/**
	 * @return <code>true</code> if request has the criteria defined,
	 *         <code>false</code> otherwise
	 */
	public boolean hasCriteria() {
		return criteria > 0;
	}

	/**
	 * @param criteria
	 *            the criteria to set
	 */
	public void setCriteria(int criteria) {
		this.criteria = criteria;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return <code>true</code> if request has the year defined,
	 *         <code>false</code> otherwise
	 */
	public boolean hasYear() {
		return year > 0;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return <code>true</code> if request has some text defined (not *) ,
	 *         <code>false</code> otherwise
	 */
	public boolean hasText() {
		return text != null && !text.trim().equals("") && !text.trim().equals("*");
	}

	/**
	 * @return the geographicFeatureId
	 */
	public String getGeographicFeatureId() {
		return geographicFeatureId;
	}

	/**
	 * @param geographicFeatureId
	 *            the geographicFeatureId to set
	 */
	public void setGeographicFeatureId(String geographicFeatureId) {
		this.geographicFeatureId = geographicFeatureId;
	}

	/**
	 * @return <code>true</code> if request has the geographicFeatureId not
	 *         null, <code>false</code> otherwise
	 */
	public boolean hasGeographicFeatureId() {
		return geographicFeatureId != null;
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
	 * @return <code>true</code> if request has the inventoryIdentifier not
	 *         null, <code>false</code> otherwise
	 */
	public boolean hasInventoryIdentifier() {
		return inventoryIdentifier != null;
	}

	public boolean hasAtLeastOneSearchParameterButText() {
		return hasAuthority() || hasCriteria() || hasType();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + authority;
		result = prime * result + criteria;
		result = prime * result + ((geographicFeatureId == null) ? 0 : geographicFeatureId.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((inventoryIdentifier == null) ? 0 : inventoryIdentifier.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + type;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObservationsRequest other = (ObservationsRequest) obj;
		if (authority != other.authority)
			return false;
		if (criteria != other.criteria)
			return false;
		if (geographicFeatureId == null) {
			if (other.geographicFeatureId != null)
				return false;
		} else if (!geographicFeatureId.equals(other.geographicFeatureId))
			return false;
		if (id != other.id)
			return false;
		if (inventoryIdentifier == null) {
			if (other.inventoryIdentifier != null)
				return false;
		} else if (!inventoryIdentifier.equals(other.inventoryIdentifier))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (type != other.type)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

}
