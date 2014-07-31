package org.vme.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.Vme;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.service.dto.DtoTranslator;
import org.vme.web.service.search.ObservationsRequest;
import org.vme.web.service.search.ServiceResponse;

public class SearchService extends AbstractService {

	@Inject
	private VmeDao vDao;
	
	@Inject
	private DtoTranslator translator;
	
	public ServiceResponse<?> invoke(ObservationsRequest request) throws Exception {
		
		ServiceResponse<?> result = new ServiceResponse<VmeDto>(request);
		
		List<Vme> vmeList = vDao.loadVmes();
		List<VmeDto> vmeDtoList = new ArrayList<VmeDto>();
		
		if(request.getId() > 0) {
			filterVmePerId(vmeList, request.getId());
		} else if (request.hasInventoryIdentifier()) {
			filterVmePerInventoryIdentifier(vmeList, request.getInventoryIdentifier());
		} else if (request.hasGeographicFeatureId()) {
			filterVmePerGeographFeatureId(vmeList, request.getGeographicFeatureId());
		} else {
			filterByRequestParam(vmeList, request.getAuthority(), request.getType(), request.getCriteria(), request.getText());
		}
		
		for (Vme vme : vmeList) {
			vmeDtoList.add(translator.doTranslate4Vme(vme, request.getYear()));
		}
		
		result.addElements(vmeDtoList);
		
		return result;
		
	}	
}
