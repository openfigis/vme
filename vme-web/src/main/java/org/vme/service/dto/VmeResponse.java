package org.vme.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@XmlRootElement
public class VmeResponse {

	private UUID uuid;
	private String note;
	private List<VmeDto> resultList;

	public VmeResponse() {
		super();
	}

	public VmeResponse(UUID uuid) {
		super();
		this.uuid = uuid;
		this.resultList = new ArrayList<VmeDto>();
	}

	public List<VmeDto> getVmeDto() {
		return this.resultList;
	}

	public void setVmeDto(List<VmeDto> vmeDto) {
		this.resultList = vmeDto;
	}

	public UUID getUuid() {
		return this.uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
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
