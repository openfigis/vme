package org.vme.service.search.vme;

import org.vme.service.dto.VmeSearchRequestDto;
import org.vme.service.dto.VmeSearchResult;


public class VmeSearchService {



	
	
	
	public VmeSearchResult retrieveResultsFor(VmeSearchRequestDto request) {
		VmeSearchResult res = new VmeSearchResult(request);
		return res;
	}

}
