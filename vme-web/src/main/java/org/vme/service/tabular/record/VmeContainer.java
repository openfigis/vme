package org.vme.service.tabular.record;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.domain.model.Vme;
import org.vme.dao.sources.figis.FigisDao;

public class VmeContainer {
	
	@Inject
	FigisDao fDao;
	
	public List<VmeObservation> findVmeObservationByVme(Vme v){
		
		return fDao.findVmeObservationByVme(v.getId());
		
	}

}
