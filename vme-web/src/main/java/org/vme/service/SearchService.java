package org.vme.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.vme.dao.VmeSearchDao;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

public class SearchService {
	
	@Inject
	private VmeDao vDao;
	
	@Inject
	private VmeSearchDao vSearchDao;

	@Inject
	@ConceptProvider
	private ReferenceDaoImpl refDao;
	
	public List<Object> findByVmeIdentifier(String vme_Identifier, int vme_Year){
		
		List<VmeDto> vmeDtoList = vSearchDao.getVmeByInventoryIdentifier(vme_Identifier, vme_Year);
		List<SpecificMeasure> specMeasureList = new ArrayList<SpecificMeasure>();


		for (VmeDto vmeDto : vmeDtoList) {
			specMeasureList.addAll(vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList());
		}

		LinkedList<Object> responseList = new LinkedList<Object>(); 
		
		for (VmeDto vmeDto : vmeDtoList) {
			responseList.add(vmeDto.getVmeId());
			for (SpecificMeasure sm : vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList()) {
				responseList.add(sm.getVmeSpecificMeasure());
			}
		}
		responseList.addAll(specMeasureList);

		return responseList;
	}

}