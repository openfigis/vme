package org.vme.service.search.vme;

import org.vme.service.dto.VmeSearchDto;
import org.vme.service.dto.VmeSearchRequestDto;
import org.vme.service.dto.VmeSearchResult;


public class VmeSearchServiceMockup  implements SearchService  {



	
	
	
	public VmeSearchServiceMockup() {
		System.out.println("VME search engine 1.0 - Mockup service");

	}

	public VmeSearchResult search(VmeSearchRequestDto request) {
		VmeSearchResult res = new VmeSearchResult(request);
		String authority;
		String vme_type;
		int start_cycle;
		int end_cycle;
		
		
		
		switch (request.getAuthority()) {
		case 20010:
			authority = "CCAMLR";
			vme_type = "Risk Area";
			start_cycle = 100;
			end_cycle = 132;
			break;
		case 22080:
			authority = "GFCM";
			vme_type = "VME";
			start_cycle = 0;
			end_cycle = 0;
			break;
		case 20220:
			authority = "NAFO";
			vme_type = "VME";
			start_cycle = 1;
			end_cycle = 18;
			break;
		case 21580:
			authority = "NEAFC";
			vme_type = "VME";
			start_cycle = 12;
			end_cycle = 14;
			break;
		case 22140:
			authority = "SEAFO";
			vme_type = "VME";
			start_cycle = 134;
			end_cycle = 139;
			break;
		default:
			authority = "";
			vme_type = "VME";
			start_cycle = 0;
			end_cycle = 0;
			break;
		}
		
		
		for (int i = start_cycle; i < end_cycle; i++) {
			VmeSearchDto dto = new VmeSearchDto();
			dto.setEnvelope("envelope_" + i);
			dto.setFactsheetUrl("fishery/vme/10/en");
			dto.setGeoArea("Geographical reference test n. " + i);
			dto.setOwner(authority);
			dto.setVmeType(vme_type);
			dto.setValidityPeriod("period " + i);
			dto.setLocalName("VME of " + request.getYear() + " n." + i);
			dto.setVmeId(10);
			dto.setGeographicLayerId("VME_" + authority + "_" + i);
			dto.setYear(request.getYear());
			res.addElement(dto);
		}
		
		return res;
	}

}
