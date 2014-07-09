package org.vme.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.fao.fi.vme.domain.dto.VmeDto;

public class VmeResponse {

	private UUID uuid;
	private List<VmeDto> vmeDto;

	public VmeResponse(UUID uuid){
		super();
		this.uuid = uuid;
		this.vmeDto = new ArrayList<VmeDto>();
	}

	public List<VmeDto> getVmeDto() {
		return this.vmeDto;
	}

	public void setVmeDto(List<VmeDto> vmeDto) {
		this.vmeDto = vmeDto;
	}

	public UUID getUuid() {
		return this.uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
