package org.vme.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VmeSmResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5049747883502243577L;

	private UUID uuid;
	private String note;
	private long vmeId;
	private String localName;
	private String inventoryIdentifier;
	private String geoArea;
	private String owner;
	private String vmeType;

	private List<SpecificMeasureDto> specificMeasure;

	public VmeSmResponse(UUID uuid) {
		super();
		this.uuid = uuid;
		this.specificMeasure = new ArrayList<SpecificMeasureDto>();
	}

	public UUID getUuid() {
		return this.uuid;
	}

	public List<SpecificMeasureDto> getResponseList() {
		return this.specificMeasure;
	}

	public void setResponseList(List<SpecificMeasureDto> specList) {
		this.specificMeasure = specList;
	}

	public long getVmeId() {
		return vmeId;
	}

	public void setVmeId(long vmeId) {
		this.vmeId = vmeId;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getInventoryIdentifier() {
		return inventoryIdentifier;
	}

	public void setInventoryIdentifier(String inventoryIdentifier) {
		this.inventoryIdentifier = inventoryIdentifier;
	}

	public String getGeoArea() {
		return geoArea;
	}

	public void setGeoArea(String geoArea) {
		this.geoArea = geoArea;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getVmeType() {
		return vmeType;
	}

	public void setVmeType(String vmeType) {
		this.vmeType = vmeType;
	}

	@Override
	public String toString() {
		String s = "Response: UUID - " + this.uuid.toString() + " - vmeId - " + this.vmeId + " - local name - "
				+ this.localName + " - inventory Identifier - " + this.inventoryIdentifier + " - geoArea - "
				+ this.geoArea + " - owner - " + this.owner + " - vmeType -" + this.vmeType;

		return s;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setNoObsNote(int year) {
		this.note = "No observation available for " + year
				+ ", here follows the most recent one found from the selected year";
	}
}
