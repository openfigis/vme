package org.vme.service.dto;

import java.util.List;

import javax.inject.Inject;

import org.vme.service.GetInfoService;

public class VmeSmResponse {

	@Inject
	public GetInfoService service;
	
	public List<SpecificMeasureDto> responseList;
	
	public VmeSmResponse(String vme_Identifier) {
		this.responseList = service.findInfo(vme_Identifier);
	}
	
	public VmeSmResponse(String vme_Identifier, int year){
		this.responseList = service.findInfo(vme_Identifier, year);
	}
}
