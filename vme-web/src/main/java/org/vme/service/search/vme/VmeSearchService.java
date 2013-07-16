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
import org.vme.service.reference.ReferenceServiceFactory;
import org.vme.service.reference.domain.Authority;
import org.vme.service.reference.domain.VmeCriteria;
import org.vme.service.reference.domain.VmeType;


public class VmeSearchService implements SearchService {


	@Inject
	@VmeDB
	private EntityManager entityManager;

	
	

	public VmeSearchService() {
		System.out.println("VME search engine 1.0 - Microsoft Access connettor");

	}


	public VmeSearchResult search(VmeSearchRequestDto request)  {

		Query query = createHibernateQuery();
		loadQueryParameters(query, request);
		List<?> result =   query.getResultList();
		VmeSearchResult res = convertPersistenceResult(request, (List<Vme>) result);
		return res;
	}


	private Query createHibernateQuery(){
		Query res = entityManager.createQuery("from Vme");

		return res;
	}


	private void loadQueryParameters(Query query, VmeSearchRequestDto request){
		try {
			String authority = "*";
			if (request.getAuthority()>0){
				Authority vmeAuthority = (Authority) ReferenceServiceFactory.getService().getReference(Authority.class, (long) request.getAuthority());
				authority = vmeAuthority.getAcronym();
			}
			String criteria = "*";
			if (request.getCriteria()>0){
				VmeCriteria vmeCriteria = (VmeCriteria) ReferenceServiceFactory.getService().getReference(VmeCriteria.class, (long) request.getCriteria());
				criteria = vmeCriteria.getName();
			}
			String areaType = "*";
			if (request.getCriteria()>0){
				VmeType vmeType = (VmeType) ReferenceServiceFactory.getService().getReference(VmeType.class, (long) request.getType());
				areaType = vmeType.getName();
			}

			//query.setParameter("authority", authority);
			//query.setParameter("criteria", criteria);
			//query.setParameter("areaType", areaType);


		} catch (Exception e){
			e.printStackTrace();
		}


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
