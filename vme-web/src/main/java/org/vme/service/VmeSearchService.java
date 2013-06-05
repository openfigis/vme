package org.vme.service;

import javax.inject.Named;

import org.vme.service.dto.VmeDto;

@Named
public class VmeSearchService {

	public VmeDto retrieveResultsFor(int id) {
		VmeDto dto = new VmeDto();
		dto.setName("Piet ging uit varen");

		return dto;
	}

}
