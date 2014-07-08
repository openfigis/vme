package org.vme.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VmeSmResponse {

	private UUID uuid;
	
	private List<SpecificMeasureDto> specificMeasure;

	public VmeSmResponse(UUID uuid){
		super();
		this.uuid = uuid;
		this.specificMeasure = new ArrayList<SpecificMeasureDto>();
	}
	
	public String getUuid() {
		return uuid.toString();
	}
	
	public List<SpecificMeasureDto> getResponseList(){
		return this.specificMeasure;
	}
	
	public void setResponseList(List<SpecificMeasureDto> specList){
		this.specificMeasure = specList;
	}

}
