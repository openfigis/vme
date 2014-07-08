package org.vme.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.fao.fi.vme.domain.dto.VmeDto;

public class VmeResponse {

	private UUID uuid;
	private List<VmeDto> listOfVmes;

	public VmeResponse(UUID uuid){
		super();
		this.uuid = uuid;
		this.listOfVmes = new ArrayList<VmeDto>();
	}

	public List<VmeDto> getVmeDto() {
		return listOfVmes;
	}

	public void setVmeDto(List<VmeDto> listOfVmes) {
		this.listOfVmes = listOfVmes;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
