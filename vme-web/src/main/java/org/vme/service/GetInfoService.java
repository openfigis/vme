package org.vme.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.vme.dao.VmeSearchDao;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.service.dto.DtoTranslator;
import org.vme.service.dto.SpecificMeasureDto;
import org.vme.service.dto.VmeResponse;
import org.vme.service.dto.VmeSmResponse;

public class GetInfoService {

	@Inject
	private VmeDao vDao;

	@Inject
	private VmeSearchDao vSearchDao;

	@Inject
	@ConceptProvider
	private ReferenceDaoImpl refDao;

	@Inject
	private DtoTranslator translator;

	public VmeSmResponse findInfo(String vmeIdentifier, int vmeYear) {

		VmeSmResponse vmeSmResponse = new VmeSmResponse(UUID.randomUUID());

		List<SpecificMeasureDto> resultList = new ArrayList<SpecificMeasureDto>();

		for (VmeDto vmeDto : vSearchDao.getVmeByInventoryIdentifier(vmeIdentifier, vmeYear)) {
			vmeSmResponse.setVmeId(vmeDto.getVmeId());
			vmeSmResponse.setInventoryIdentifier(vmeDto.getInventoryIdentifier());
			vmeSmResponse.setVmeType(vmeDto.getVmeType());
			vmeSmResponse.setGeoArea(vmeDto.getGeoArea());
			vmeSmResponse.setLocalName(vmeDto.getLocalName());
			vmeSmResponse.setOwner(vmeDto.getOwner());
			
			for (SpecificMeasure sm : vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList()) {
				if(sm.getYear() == vmeYear || vmeYear == 0){
					resultList.add(translator.doTranslate4Sm(sm));
				}
			}
			
		}

		vmeSmResponse.setResponseList(resultList);
		return vmeSmResponse;

	}

	public VmeResponse findInfo(String owner, String scope, int year) {
		
		VmeResponse vmeResponse = new VmeResponse(UUID.randomUUID());
		
		List<Vme> vmeList = vDao.loadVmes();
		List<Vme> vmeListPerRfmo = new ArrayList<Vme>();

		for (Vme v : vmeList) {
			if (v.getRfmo().getId().equals(owner)) {
				vmeListPerRfmo.add(v);
			}
		}
		
		List<VmeDto> vmeDtoList = new ArrayList<VmeDto>();
		
		for (Vme vme : vmeListPerRfmo) {
			VmeDto vmeDto = translator.doTranslate4Vme(vme, year);
			if(scope.equals("VME")){
				if(vmeDto.getYear() == year && vmeDto.getScope().equals(scope)){
					vmeDtoList.add(vmeDto);
				} else if (year == 0 && vmeDto.getYear() == Calendar.getInstance().get(Calendar.YEAR)) {
					vmeDtoList.add(vmeDto);
				}
			} else if(scope.equals("Regulatory")){
				if(vmeDto.getYear() == year && vmeDto.getScope().equals(scope)){
					vmeDtoList.add(vmeDto);
				} else if (year == 0) {
					vmeDtoList.add(vmeDto);
				}
			}
		}
		
		vmeResponse.setVmeDto(vmeDtoList);
		
		return vmeResponse;
	}
	
}