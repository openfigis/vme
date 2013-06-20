package org.vme.service;

import java.util.LinkedList;
import java.util.List;

import org.vme.service.dto.VmeSearchRefDto;
import org.vme.service.dto.VmeSearchRefRequestDto;
import org.vme.service.dto.VmeSearchRefResult;


public class VmeSearchRefService {



	
	
	
	public VmeSearchRefResult retrieveResultsFor(VmeSearchRefRequestDto request) {
		VmeSearchRefResult res = new VmeSearchRefResult(request);
		if (request.getConcept().equalsIgnoreCase("authority")){
			res.addElements(getVmeAuthorityList());
		}
		if (request.getConcept().equalsIgnoreCase("type")){
			res.addElements(getVmeTypeList());
		}
		if (request.getConcept().equalsIgnoreCase("criteria")){
			res.addElements(getVmeCriteriaList());
		}
		if (request.getConcept().equalsIgnoreCase("years")){
			res.addElements(getVmeYearsList());
		}
		return res;
	}

	


	private List<VmeSearchRefDto> getVmeAuthorityList(){
		LinkedList<VmeSearchRefDto> res = new LinkedList<VmeSearchRefDto>();
		res.add(new VmeSearchRefDto(20010, "en", "CCAMLR")); 
		res.add(new VmeSearchRefDto(22080, "en", "GFCM"));
		res.add(new VmeSearchRefDto(20220, "en", "NAFO")); 
		res.add(new VmeSearchRefDto(21580, "en", "NEAFC")); 
		res.add(new VmeSearchRefDto(22140, "en", "SEAFO"));

		
		return res;
	}
	
	
	
	private List<VmeSearchRefDto> getVmeTypeList(){
		LinkedList<VmeSearchRefDto> res = new LinkedList<VmeSearchRefDto>();
		res.add(new VmeSearchRefDto(10, "en", "Established VME")); 
		res.add(new VmeSearchRefDto(20, "en", "getVmeTypeList")); 
		res.add(new VmeSearchRefDto(30, "en", "Temporary VME")); 
		res.add(new VmeSearchRefDto(40, "en", "Risk area")); 
		res.add(new VmeSearchRefDto(50, "en", "Benthic protected area")); 
		res.add(new VmeSearchRefDto(60, "en", "VmeSearchRefDto")); 
		res.add(new VmeSearchRefDto(70, "en", "Voluntary closed area")); 
		res.add(new VmeSearchRefDto(80, "en", "Other types of managed area")); 
		return res;
	}


	private List<VmeSearchRefDto> getVmeCriteriaList(){
		LinkedList<VmeSearchRefDto> res = new LinkedList<VmeSearchRefDto>();
		res.add(new VmeSearchRefDto(10, "en", "Uniqueness or rarity")); 
		res.add(new VmeSearchRefDto(20, "en", "Functional significance of the habitat")); 
		res.add(new VmeSearchRefDto(30, "en", "Fragility")); 
		res.add(new VmeSearchRefDto(40, "en", "Life-history traits")); 
		res.add(new VmeSearchRefDto(50, "en", "Structural complexity")); 
		res.add(new VmeSearchRefDto(60, "en", "Unspecified")); 
		return res;
	}
	
	private List<VmeSearchRefDto> getVmeYearsList(){
		LinkedList<VmeSearchRefDto> res = new LinkedList<VmeSearchRefDto>();
		res.add(new VmeSearchRefDto(2012, "en", "2012")); 
		return res;
	}

	
}
