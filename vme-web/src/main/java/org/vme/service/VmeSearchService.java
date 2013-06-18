package org.vme.service;

import org.vme.service.dto.VmeDto;
import org.vme.service.dto.VmeSearchRequestDto;
import org.vme.service.dto.VmeServiceResult;


public class VmeSearchService {



	
	
	
	public VmeServiceResult retrieveResultsFor(VmeSearchRequestDto request) {
		VmeServiceResult res = new VmeServiceResult(request);
		
		for (int i = 0; i < 10; i++) {
			VmeDto dto = new VmeDto();
			dto.setEnvelope("envelope_" + i);
			dto.setFactsheetUrl("url_" + i);
			dto.setGeoArea("geoarea_" + i);
			dto.setOwner("owner_" + i);
			dto.setVmeId(1000 + i);
			dto.setYear(2000 + i);
			res.addElement(dto);
		}
		
		return res;
	}

}
