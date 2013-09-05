package org.vme.service.search.vme;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.dao.config.VmeDB;
import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.Profile;
import org.fao.fi.vme.domain.ValidityPeriod;
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

	protected FigisDao dao;

	private MultiLingualStringUtil u = new MultiLingualStringUtil();


	public VmeSearchService() {
		System.out.println("VME search engine 1.0");

	}


	public VmeSearchResult search(VmeSearchRequestDto request) throws Exception  {
		if (request.hasYear()){
		} else {
			request.setYear( Calendar.getInstance().get(Calendar.YEAR));
		}
		Query query = entityManager.createQuery(createHibernateSearchTextualQuery(request));
		List<Vme> result =  (List<Vme>)query.getResultList();
		List<Vme> toRemove =  postProcessResult(request, result);
		VmeSearchResult res = convertPersistenceResult(request, (List<Vme>) result, toRemove);
		return res;
	}


	public VmeSearchResult get(VmeGetRequestDto request)  {
		if (request.hasYear()){
		} else {
			request.setYear( Calendar.getInstance().get(Calendar.YEAR));
		}		
		String text_query;

		if (request.getId()>0){
			text_query = "from Vme vme where vme.id = " + request.getId();
		} else if (request.hasInventoryIdentifier()) {
			text_query = "from Vme vme where vme.inventoryIdentifier = '" + request.getInventoryIdentifier() + "'";
		} else if (request.hasGeographicFeatureId()) {
			text_query = "SELECT vme from Vme vme, GEO_REF gfl WHERE vme = gfl.vme and gfl IN (SELECT gfl from GEO_REF WHERE gfl.geographicFeatureID = '" + request.getGeographicFeatureId() + "')";
		} else text_query = "";
		Query query = entityManager.createQuery(text_query);
		List<?> result =   query.getResultList();
		VmeSearchResult res = convertPersistenceResult(request, (List<Vme>) result, null);
		return res;
	}



	private String createHibernateSearchTextualQuery(VmeSearchRequestDto request) throws Exception {
		StringBuffer txtQuery = new StringBuffer(200);
		String conjunction;
		txtQuery.append("Select vme from Vme vme");
		if (request.hasAtLeastOneParameter()){
			txtQuery.append(" where");
			conjunction = "";
		} else {
			return txtQuery.toString();
		}



		if (request.hasAuthority()){
			Authority vmeAuthority = (Authority) ReferenceServiceFactory.getService().getReference(Authority.class, (long) request.getAuthority());
			String authority = vmeAuthority.getAcronym();
			txtQuery.append(conjunction);
			txtQuery.append(" vme.rfmo.id = '");
			txtQuery.append(authority);
			txtQuery.append("'");
			conjunction = " AND";
		}

		if (request.hasCriteria()){
			VmeCriteria vmeCriteria = (VmeCriteria) ReferenceServiceFactory.getService().getReference(VmeCriteria.class, (long) request.getCriteria());
			String criteria = vmeCriteria.getName();
			txtQuery.append(conjunction);
			txtQuery.append(" vme.criteria = '");
			txtQuery.append(criteria);
			txtQuery.append("'");
			conjunction = " AND";
		}

		if (request.hasType()){
			VmeType vmeType = (VmeType) ReferenceServiceFactory.getService().getReference(VmeType.class, (long) request.getType());
			String areaType = vmeType.getName();
			txtQuery.append(conjunction);
			txtQuery.append("  vme.areaType = '");
			txtQuery.append(areaType);
			txtQuery.append("'");
			conjunction = " AND";
		}

		txtQuery.append(" AND vme.validityPeriod.beginYear <= ");
		txtQuery.append(request.getYear());
		txtQuery.append(" AND vme.validityPeriod.endYear >= ");
		txtQuery.append(request.getYear());
		

		String res = txtQuery.toString();
		System.out.println("FAB:" + res);
		return res;
	}






	private List<Vme> postProcessResult (VmeSearchRequestDto request,  List<Vme> result){
		int requested_year = request.getYear();
		List<Vme> res = new LinkedList<Vme>();

		if (requested_year>0) {
			for (Vme vme : result) {
				boolean is_good = false;
				List<GeoRef> georef = vme.getGeoRefList();
				for (GeoRef profile : georef) {
					if (profile.getYear()==requested_year) {
						is_good = true;
						break;
					}
				}
				if (!is_good){
					ValidityPeriod validityPeriod =  vme.getValidityPeriod();
					if (validityPeriod.getBeginYear()<= requested_year && validityPeriod.getEndYear()>= requested_year){
						is_good = true;
					}
				}
				if (!is_good){
					res.add(vme);
				}
			}
		}
		return res;
	}






	private VmeSearchResult convertPersistenceResult(VmeRequestDto request,  List<Vme> result, List<Vme> toRemove){
		VmeSearchResult res = new VmeSearchResult(request);
		for (Vme vme : result) {
			if (toRemove==null || (toRemove!=null  && !toRemove.contains(vme))){
				res.addElement(getVmeSearchDto(vme,request.getYear()));
			}
		}
		return res;
	}




	private VmeSearchDto getVmeSearchDto(Vme vme, int year) {
		VmeSearchDto res = new VmeSearchDto();
		res.setVmeId(vme.getId());
		res.setInventoryIdentifier(vme.getInventoryIdentifier());
		res.setLocalName(u.getEnglish(vme.getName()));
		res.setEnvelope("");
		String authority = vme.getRfmo().getId();
		VmeObservation vo = dao.findFirstVmeObservation(vme.getId(), Integer.toString(year));
		if (vo!=null){
			res.setFactsheetUrl("fishery/vme/"+ vo.getId().getVmeId() + "/" + vo.getId().getObservationId() +"/en");
		} else {
			res.setFactsheetUrl("");
		}

		res.setGeoArea(u.getEnglish(vme.getGeoArea()));
		res.setOwner(authority);
		res.setValidityPeriodFrom(vme.getValidityPeriod().getBeginYear());
		res.setValidityPeriodTo(vme.getValidityPeriod().getEndYear());
		res.setVmeType(vme.getAreaType());
		res.setYear(year);
		res.setGeographicFeatureId(vme.getGeoRefList().size()>0?vme.getGeoRefList().get(0).getGeographicFeatureID():"");
		return res;
	}


	/**
	 * @param dao the dao to set
	 */
	@Inject
	public void setDao(FigisDao dao) {
		this.dao = dao;
	}





}
