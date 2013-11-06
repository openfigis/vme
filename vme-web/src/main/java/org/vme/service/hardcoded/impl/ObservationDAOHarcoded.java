package org.vme.service.hardcoded.impl;

import org.vme.service.dto.obs.ObservationDto;
import org.vme.web.service.io.ObservationsRequest;
import org.vme.web.service.io.ServiceResponse;


public class ObservationDAOHarcoded    {






	public ObservationDAOHarcoded() {
		System.out.println("VME search engine 1.0 - Mockup service");

	}

	public ServiceResponse<ObservationDto> searchObservations(ObservationsRequest request) {
		ServiceResponse<ObservationDto> res = new ServiceResponse<ObservationDto>(request);
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
			ObservationDto dto = new ObservationDto();
			dto.setEnvelope("envelope_" + i);
			dto.setFactsheetUrl("fishery/vme/10/en");
			dto.setGeoArea("Geographical reference test n. " + i);
			dto.setOwner(authority);
			dto.setVmeType(vme_type);
			dto.setValidityPeriodFrom(1999);
			dto.setValidityPeriodFrom(2000);
			dto.setLocalName("VME of " + request.getYear() + " n." + i);
			dto.setVmeId(10);
			dto.setInventoryIdentifier("");
			dto.setGeographicFeatureId("VME_" + authority + "_" + i);
			dto.setYear(request.getYear());
			res.addElement(dto);
		}
		return res;
	}



	public ServiceResponse<ObservationDto> getObservation(ObservationsRequest request) {
		ServiceResponse<ObservationDto> res = new ServiceResponse<ObservationDto>(request);
		ObservationDto dto = new ObservationDto();
		dto.setEnvelope("envelope");
		dto.setFactsheetUrl("fishery/vme/10/en");
		dto.setGeoArea("Geographic reference");
		dto.setOwner("GFCM");
		dto.setVmeType("");
		dto.setValidityPeriodFrom(1999);
		dto.setValidityPeriodFrom(2000);
		dto.setLocalName("VME of 999");
		dto.setVmeId(10);
		dto.setInventoryIdentifier("");
		dto.setGeographicFeatureId("");
		dto.setYear(9999);
		res.addElement(dto);
		return res;
	}



}
