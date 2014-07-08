package org.vme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.vme.dao.VmeSearchDao;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.service.dto.DtoTranslator;
import org.vme.service.dto.SpecificMeasureDto;
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

	public VmeSmResponse findInfo(String vmeIdentifier){

		VmeSmResponse vmeSmResponse = new VmeSmResponse(UUID.randomUUID());

		List<SpecificMeasureDto> resultList = new ArrayList<SpecificMeasureDto>();

		for (VmeDto vmeDto : vSearchDao.getVmeByInventoryIdentifier(vmeIdentifier, 0)) {
			for (SpecificMeasure sm : vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList()) {
				resultList.add(translator.doTranslate4Sm(sm));
			}
		}

		vmeSmResponse.setResponseList(resultList);
		return vmeSmResponse;

	}

	public VmeSmResponse findInfo(String vmeIdentifier, int vmeYear) {

		VmeSmResponse vmeSmResponse = new VmeSmResponse(UUID.randomUUID());

		List<SpecificMeasureDto> resultList = new ArrayList<SpecificMeasureDto>();

		for (VmeDto vmeDto : vSearchDao.getVmeByInventoryIdentifier(vmeIdentifier, vmeYear)) {
			for (SpecificMeasure sm : vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList()) {
				resultList.add(translator.doTranslate4Sm(sm));
			}
		}

		vmeSmResponse.setResponseList(resultList);
		return vmeSmResponse;

	}
	
}