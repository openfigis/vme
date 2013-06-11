package org.vme.service;

import org.vme.service.dto.VmeDto;


public class VmeSearchService {


	public VmeDto retrieveResultsFor(int id) {
		VmeDto dto = new VmeDto();
		dto.setName("Piet ging uit varen");
		return dto;
	}

}
