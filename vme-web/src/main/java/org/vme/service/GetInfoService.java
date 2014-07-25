package org.vme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.fao.fi.vme.webservice.SpecificMeasureList;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.vme.dao.ReferenceServiceException;
import org.vme.dao.VmeSearchDao;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.service.dto.DtoTranslator;
import org.vme.service.dto.SpecificMeasureDto;
import org.vme.service.dto.VmeResponse;
import org.vme.service.dto.VmeSmResponse;

public class GetInfoService extends AbstractService {

	@Inject
	private VmeDao vDao;

	@Inject
	private VmeSearchDao vSearchDao;

	@Inject
	@ConceptProvider
	private ReferenceDaoImpl refDao;

	@Inject
	private DtoTranslator translator;

	public VmeSmResponse vmeIdentifier2SpecificMeasure(String vmeIdentifier, int vmeYear){

		VmeSmResponse vmeSmResponse = new VmeSmResponse(UUID.randomUUID());
		List<SpecificMeasureDto> specificMeasureList = new ArrayList<SpecificMeasureDto>();
		VmeDto vmeDto = vSearchDao.getVmeByInventoryIdentifier(vmeIdentifier, vmeYear).get(0);

		vmeSmResponse.setVmeId(vmeDto.getVmeId());
		vmeSmResponse.setInventoryIdentifier(vmeDto.getInventoryIdentifier());
		vmeSmResponse.setVmeType(vmeDto.getVmeType());
		vmeSmResponse.setGeoArea(vmeDto.getGeoArea());
		vmeSmResponse.setLocalName(vmeDto.getLocalName());
		vmeSmResponse.setOwner(vmeDto.getOwner());

		if (vmeYear <= 2004 && vmeYear != 0) {
			vmeSmResponse.setNoObsNote(vmeYear);
		}

		if(vmeYear == 0){
			vmeSmResponse.setNote("These are all the Specific Measure for the Vme");
			for (SpecificMeasure sm : vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList()) {
				specificMeasureList.add(translator.doTranslate4Sm(sm));
			}
		}

		if(vmeYear > 2004){
			while (specificMeasureList.isEmpty() && vmeYear > 2004){
				for (SpecificMeasure sm : vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList()) {
					if (sm.getYear() == vmeYear){
						specificMeasureList.add(translator.doTranslate4Sm(sm));
					}
				}

				if(specificMeasureList.isEmpty() && vmeSmResponse.getNote() == null){
					vmeSmResponse.setNoObsNote(vmeYear);
				}
				vmeYear--;
			}
		}
		
		if(specificMeasureList.isEmpty()){
			specificMeasureList.add(new SpecificMeasureDto());
		}
		
		vmeSmResponse.setResponseList(specificMeasureList);
		return vmeSmResponse;
	}

//	public VmeResponse ownerScope2Vmes(String owner, String scope, int year){
//		
//		VmeResponse vmeResponse = new VmeResponse(UUID.randomUUID());
//		
//		return vmeResponse;
//	}
	
	public VmeResponse findInfo(String owner, String scope, int year) {

		VmeResponse vmeResponse = new VmeResponse(UUID.randomUUID());
		
		List<Vme> vmeListPerRfmo = filterVmePerRfmo(vDao.loadVmes(), owner);
		
		List<Vme> vmeListPerScope = new ArrayList<Vme>();
		
		List<VmeDto> vmeDtoList = new ArrayList<VmeDto>();

		if (year <= 2005 && year != 0) {
			vmeResponse.setNoObsNote(year);
		}

		if (scope.equals("VME")) {
			while (vmeDtoList.isEmpty() && year > 2004 || year == 0) {
				for (Vme vme : vmeListPerRfmo) {
					VmeDto vmeDto = translator.doTranslate4Vme(vme, year);
					if (vmeDto.getYear() == year) {
						vmeDtoList.add(vmeDto);
					} else if (year == 0) {
						vmeDtoList.add(vmeDto);
					}
				}
				if (vmeDtoList.isEmpty() && vmeResponse.getNote() == null) {
					vmeResponse.setNoObsNote(year);
				}
				year--;
			}
		}

		if (scope.equals("Regulatory")) {
			for (Vme vme : vmeListPerRfmo) {
				try {
					if (refDao.getReferenceByID(VmeScope.class, vme.getScope()).getName().equals("Regulatory")) {
						vmeListPerScope.add(vme);
					}
				} catch (ReferenceServiceException e) {
					throw new VmeException(e);
				}
			}
			while (vmeDtoList.isEmpty() && year > 2004 || year == 0) {
				for (Vme vme : vmeListPerScope) {
					VmeDto vmeDto = translator.doTranslate4Vme(vme, year);
					if (vmeDto.getYear() == year) {
						vmeDtoList.add(vmeDto);
					} else if (year == 0) {
						vmeDtoList.add(vmeDto);
					}
				}
				if (vmeDtoList.isEmpty() && vmeResponse.getNote() == null) {
					vmeResponse.setNoObsNote(year);
				}
				year--;
			}
		}

		vmeResponse.setVmeDto(vmeDtoList);
		return vmeResponse;
	}

	public SpecificMeasureList vmeIdentifierSpecificmeasures(String vmeIdentifier, int year) {

		SpecificMeasureList smList = new SpecificMeasureList();

		for (VmeDto vmeDto : vSearchDao.getVmeByInventoryIdentifier(vmeIdentifier, year)) {

			for (SpecificMeasure sm : vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList()) {

				smList.getSpecificMeasures().add(translator.doTranslate4SmType(sm));

			}
		}

		return smList;
	}

}