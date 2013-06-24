package org.vme.service;

import org.vme.service.dto.VmeSearchDto;
import org.vme.service.dto.VmeSearchRequestDto;
import org.vme.service.dto.VmeSearchResult;


public class VmeSearchService {



	
	
	
	public VmeSearchResult retrieveResultsFor(VmeSearchRequestDto request) {
		VmeSearchResult res = new VmeSearchResult(request);
		
		for (int i = 0; i < 50; i++) {
			VmeSearchDto dto = new VmeSearchDto();
			dto.setEnvelope("envelope_" + i);
			dto.setFactsheetUrl("url_" + i);
			dto.setGeoArea("geoarea_" + i);
			dto.setOwner("owner_" + i);
			dto.setVmeType("type " + request.getType());
			dto.setValidityPeriod("period " + i);
			dto.setLocalName("VME of " + request.getYear() + " n." + i);
			dto.setVmeId(1000 + i);
			dto.setYear(request.getYear());
			res.addElement(dto);
		}
		
		return res;
	}

}
