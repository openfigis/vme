package org.vme.service.dao.impl.hardcoded;

import java.util.LinkedList;
import java.util.List;

import org.fao.fi.vme.domain.dto.observations.ObservationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.service.dao.ObservationDAO;


public class ObservationHarcodedDao implements ObservationDAO   {
	static final private Logger LOG = LoggerFactory.getLogger(ObservationHarcodedDao.class);

	public ObservationHarcodedDao() {
		LOG.info("VME search engine 1.0 - Mockup service");

	}



	public List<ObservationDto> searchObservations(long authority_id, long type_id, long criteria_id, int year, String text) throws Exception  {
		LinkedList<ObservationDto> res = new LinkedList<ObservationDto>();
		String authority;
		String vme_type;
		int start_cycle;
		int end_cycle;

		switch ((int)authority_id) {
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
			dto.setLocalName("VME of " + year + " n." + i);
			dto.setVmeId(10);
			dto.setInventoryIdentifier("");
			dto.setGeographicFeatureId("VME_" + authority + "_" + i);
			dto.setYear(year);
			res.add(dto);
		}
		return res;
	}



	public List<ObservationDto> getObservationById(long id, int year)  {
		LinkedList<ObservationDto> res = new LinkedList<ObservationDto>();
		ObservationDto dto = new ObservationDto();
		dto.setEnvelope("envelope");
		dto.setFactsheetUrl("fishery/vme/10/en");
		dto.setGeoArea("Geographic reference");
		dto.setOwner("GFCM");
		dto.setVmeType("");
		dto.setValidityPeriodFrom(1999);
		dto.setValidityPeriodFrom(2000);
		dto.setLocalName("VME of 999");
		dto.setVmeId(id);
		dto.setInventoryIdentifier("");
		dto.setGeographicFeatureId("");
		dto.setYear(year);
		res.add(dto);
		return res;
	}	

	public List<ObservationDto> getObservationByInventoryIdentifier(String inv_id, int year)  {
		LinkedList<ObservationDto> res = new LinkedList<ObservationDto>();
		ObservationDto dto = new ObservationDto();
		dto.setEnvelope("envelope");
		dto.setFactsheetUrl("fishery/vme/10/en");
		dto.setGeoArea("Geographic reference");
		dto.setOwner("GFCM");
		dto.setVmeType("");
		dto.setValidityPeriodFrom(1999);
		dto.setValidityPeriodFrom(2000);
		dto.setLocalName("Test of VME inventory identifier " + inv_id);
		dto.setVmeId(9999);
		dto.setInventoryIdentifier(inv_id);
		dto.setGeographicFeatureId("");
		dto.setYear(year);
		res.add(dto);
		return res;	
	}

	public List<ObservationDto> getObservationByGeographicFeatureId(String geo_id, int year)  {
		LinkedList<ObservationDto> res = new LinkedList<ObservationDto>();
		ObservationDto dto = new ObservationDto();
		dto.setEnvelope("envelope");
		dto.setFactsheetUrl("fishery/vme/10/en");
		dto.setGeoArea("Geographic reference");
		dto.setOwner("GFCM");
		dto.setVmeType("");
		dto.setValidityPeriodFrom(1999);
		dto.setValidityPeriodFrom(2000);
		dto.setLocalName("Test of VME geographical id" + geo_id);
		dto.setVmeId(9999);
		dto.setInventoryIdentifier("");
		dto.setGeographicFeatureId(geo_id);
		dto.setYear(year);
		res.add(dto);
		return res;	
	}





}
