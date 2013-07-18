package org.vme.service.search.vme;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.fao.fi.vme.dao.config.VmeDB;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.vme.service.dto.VmeGetRequestDto;
import org.vme.service.dto.VmeRequestDto;
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


	private MultiLingualStringUtil u = new MultiLingualStringUtil();


	public VmeSearchService() {
		System.out.println("VME search engine 1.0 - Microsoft Access connettor");

	}


	public VmeSearchResult search(VmeSearchRequestDto request)  {

		Query query = createHibernateSearchQuery();
		loadQueryParameters(query, request);
		List<?> result =   query.getResultList();
		VmeSearchResult res = convertPersistenceResult(request, (List<Vme>) result);
		return res;
	}


	public VmeSearchResult get(VmeGetRequestDto request)  {
		String text_query;

		if (request.getId()>0){
			text_query = "from Vme vme where vme.id = " + request.getId();
		} else if (request.hasInventoryIdentifier()) {
			text_query = "from Vme vme where vme.inventoryIdentifier = '" + request.getInventoryIdentifier() + "'";
		} else if (request.hasGeographicFeatureId()) {
			text_query = "SELECT vme from Vme vme, IN (vme.geoRefList) AS gfl WHERE gfl.geographicFeatureID = '" + request.getGeographicFeatureId() + "'";
		} else text_query = "";
		Query query = entityManager.createQuery(text_query);
		List<?> result =   query.getResultList();
		VmeSearchResult res = convertPersistenceResult(request, (List<Vme>) result);
		return res;
	}



	private Query createHibernateSearchQuery(){
		Query res = entityManager.createQuery("from Vme vme where vme.rfmo.id like :authority and vme.areaType like :areaType  ORDER BY vme.id  ");
		return res;
	}



	private void loadQueryParameters(Query query, VmeSearchRequestDto request){
		try {
			String authority = "%";
			if (request.getAuthority()>0){
				Authority vmeAuthority = (Authority) ReferenceServiceFactory.getService().getReference(Authority.class, (long) request.getAuthority());
				authority = vmeAuthority.getAcronym();
			}
			String criteria = "%";
			if (request.getCriteria()>0){
				VmeCriteria vmeCriteria = (VmeCriteria) ReferenceServiceFactory.getService().getReference(VmeCriteria.class, (long) request.getCriteria());
				criteria = vmeCriteria.getName();
			}
			String areaType = "%";
			if (request.getCriteria()>0){
				VmeType vmeType = (VmeType) ReferenceServiceFactory.getService().getReference(VmeType.class, (long) request.getType());
				areaType = vmeType.getName();
			}

			query.setParameter("authority", authority);
			query.setParameter("areaType", areaType);
			//query.setParameter("criteria", criteria);


		} catch (Exception e){
			e.printStackTrace();
		}


	}


	private VmeSearchResult convertPersistenceResult(VmeRequestDto request,  List<Vme> result){
		VmeSearchResult res = new VmeSearchResult(request);
		for (Vme vme : result) {
			res.addElement(getVmeSearchDto(vme));
		}
		return res;
	}




	private VmeSearchDto getVmeSearchDto(Vme vme) {
		VmeSearchDto res = new VmeSearchDto();
		res.setVmeId(vme.getId());
		res.setInventoryIdentifier(vme.getInventoryIdentifier());
		res.setLocalName(u.getEnglish(vme.getName()));
		res.setEnvelope("");
		String authority = vme.getRfmo().getId();
		if (authority.equals("NAFO")){
			res.setFactsheetUrl("fishery/vme/10/en");
		} else 	if (authority.equals("CCAMLR")){
			res.setFactsheetUrl("fishery/vme/11/en");
		} else 	if (authority.equals("GFCM")){
			res.setFactsheetUrl("fishery/vme/12/en");
		} else 	if (authority.equals("NEAFC")){
			res.setFactsheetUrl("fishery/vme/13/en");
		} else 	if (authority.equals("SEAFO")){
			res.setFactsheetUrl("fishery/vme/14/en");
		} 

		//res.setFactsheetUrl("fishery/vme/"+vme.getId()+"/en");
		res.setGeoArea(vme.getGeoArea());
		res.setOwner(authority);
		res.setValidityPeriodFrom(vme.getValidityPeriod().getBeginYear());
		res.setValidityPeriodTo(vme.getValidityPeriod().getEndYear());
		res.setVmeType(vme.getAreaType());
		res.setYear( vme.getGeoRefList().size()>0?vme.getGeoRefList().get(0).getYear():0);
		res.setGeographicFeatureId(vme.getGeoRefList().size()>0?vme.getGeoRefList().get(0).getGeographicFeatureID():"");
		return res;
	}




}
