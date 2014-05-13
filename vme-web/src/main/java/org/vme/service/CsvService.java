package org.vme.service;


import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.Vme;
import org.vme.dao.VmeSearchDao;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.web.service.ServiceInvoker;
import org.vme.web.service.io.ObservationsRequest;
import org.vme.web.service.io.ServiceResponse;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.fao.fi.vme.domain.model.GeneralMeasure;

/**
 * 
 * @author Roberto Empiri
 * 
 */

public class CsvService {

	@Inject
	VmeDao dao;

	public static ServiceResponse<?> invoke(VmeDao vmeDao, ObservationsRequest request) {

		List<Vme> vme = vmeDao.loadVmes();
		ServiceResponse<?> result = new ServiceResponse<Vme>(request);
		
		for (Vme v : vme) {
			if (v.getRfmo().getId().equals(String.valueOf(request.getAuthority()))) {
				vme.remove(v);
			}
		}
		
		result.addElements(vme);
		
		return result;
	}
	

}