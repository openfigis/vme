package org.vme.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.vme.service.GetInfoService;

public class VmeSmResponse {

	private UUID uuid;
	
	private GetInfoService service = new GetInfoService();
	
	private List<SpecificMeasureDto> specificMeasure = new ArrayList<SpecificMeasureDto>();

	public VmeSmResponse(UUID uuid){
		super();
		this.uuid = uuid;
	}
	
	public void createResponse(String vmeIdentifier, int year) {
		this.specificMeasure = service.findInfo(vmeIdentifier, year);
	}

	public void createResponse(String vmeIdentifier) {
		this.specificMeasure = service.findInfo(vmeIdentifier);
	}
	
	public String getUuid() {
		return uuid.toString();
	}
	
	public List<SpecificMeasureDto> getResponseList(){
		return this.specificMeasure;
	}

}
