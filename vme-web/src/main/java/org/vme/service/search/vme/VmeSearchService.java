package org.vme.service.search.vme;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.fao.fi.vme.dao.config.VmeDB;
import org.fao.fi.vme.domain.Vme;
import org.vme.service.dto.VmeSearchDto;
import org.vme.service.dto.VmeSearchRequestDto;
import org.vme.service.dto.VmeSearchResult;


public class VmeSearchService {


	@Inject
	@VmeDB
	private EntityManager entityManager;
	
	
	
	public VmeSearchResult retrieveResultsFor(VmeSearchRequestDto request) {
		
		Query query = createHibernateQuery();
		loadQueryParameters(query, request);
		List<?> result =   query.getResultList();
		
		
		VmeSearchResult res = convertPersistenceResult(request, (List<Vme>) result);
		return res;
	}
	
	
	private Query createHibernateQuery(){
		Query res = entityManager.createQuery("from Vme where id = :id");
		
		return res;
	}

	
	private void loadQueryParameters(Query query, VmeSearchRequestDto request){
		
		
		
	}
	
	
	private VmeSearchResult convertPersistenceResult(VmeSearchRequestDto request,  List<Vme> result){
		VmeSearchResult res = new VmeSearchResult(request);
		
		
		for (Vme vme : result) {
			res.addElement(getVmeSearchDto(vme));
		}
		
		
		return res;
	}


	private VmeSearchDto getVmeSearchDto(Vme vme) {
		VmeSearchDto res = new VmeSearchDto();
		res.setVmeId(vme.getId());
		
		
		return res;
	}
	
	
	

}
