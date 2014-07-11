package org.vme.service;

import java.util.ArrayList;
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

		if(vmeYear<= 2005){
			vmeSmResponse.setNote("No observation available for "+vmeYear+", here follows the most recent one found from the selected year");
		}

		while(resultList.isEmpty() && vmeYear>2005 || vmeYear == 0){
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
			if(resultList.isEmpty() && vmeSmResponse.getNote() == null){
				vmeSmResponse.setNote("No observation available for "+vmeYear+", here follows the most recent one found from the selected year");
			}
			vmeYear--;
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

		if(year<= 2005){
			vmeResponse.setNote("No observation available for "+year+", here follows the most recent one found from the selected year");
		}

		while(vmeDtoList.isEmpty() && year>2005 || year == 0){
			for (Vme vme : vmeListPerRfmo) {
				VmeDto vmeDto = translator.doTranslate4Vme(vme, year);
				if(vmeDto.getYear() == year && vmeDto.getScope().equals(scope)){
					vmeDtoList.add(vmeDto);
				} else if (year == 0 && vmeDto.getScope().equals(scope)) {
					vmeDtoList.add(vmeDto);
				}
			}
			if(vmeDtoList.isEmpty() && vmeResponse.getNote() == null){
				vmeResponse.setNote("No observation available for "+year+", here follows the most recent one found from the selected year");
			}
			year--;
		}

		vmeResponse.setVmeDto(vmeDtoList);

		return vmeResponse;
	}

}