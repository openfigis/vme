package org.vme.web.service;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.VmeSearchDao;
import org.vme.service.SearchService;
import org.vme.web.service.io.ObservationsRequest;
import org.vme.web.service.io.ServiceResponse;

@Path("/search")
@Singleton
public class VmeSearchWs {

	@Inject
	private VmeSearchDao vmeSearchDao;
	
	@Inject
	private SearchService searchService;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	protected MultiLingualStringUtil u = new MultiLingualStringUtil();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@QueryParam("text") String text, @QueryParam("authority") String idAuthority,
			@QueryParam("vme_type") String idVmeType, @QueryParam("vme_criteria") String idVmeCriteria,
			@QueryParam("year") String year) throws Exception {

		ObservationsRequest request = new ObservationsRequest(UUID.randomUUID());
		request.setText(text);
		if ((idAuthority != null) && !("*").equals(idAuthority.trim())) {
			request.setAuthority(Integer.parseInt(idAuthority));
		} else {
			request.setAuthority(0);
		}
		if ((idVmeType != null) && !("*").equals(idVmeType.trim())) {
			request.setType(Integer.parseInt(idVmeType));
		} else {
			request.setType(0);
		}
		if ((idVmeCriteria != null) && !("*").equals(idVmeCriteria.trim())) {
			request.setCriteria(Integer.parseInt(idVmeCriteria));
		} else {
			request.setCriteria(0);
		}
		if ((year != null) && !("*").equals(year.trim())) {
			request.setYear(Integer.parseInt(year));
		} else {
			request.setYear(0);
		}
		ServiceResponse<?> result = ServiceInvoker.invoke(vmeSearchDao, request);
		return Response.status(200).entity(result).build();
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/{vmeIdentifier}/{vmeYear}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("vmeIdentifier") String vme_Identifier, @PathParam("vmeYear") String vme_Year) throws Exception {
		
		List<SpecificMeasure> responseList;
//		JSONArray jsonArray = new JSONArray();
		
		if(vme_Identifier != null && vme_Year != null){
			responseList = (List<SpecificMeasure>) searchService.findByVmeIdentifier(vme_Identifier, Integer.valueOf(vme_Year));
			
//			for (int j = 0; j< responseList.size(); j++) {
//				jsonArray.put(getJSONObject(responseList.get(j)));
//			}
			
			return Response.status(200).entity(responseList).build();
			
		} else if(vme_Identifier != null && vme_Year == null) {
			responseList = (List<SpecificMeasure>) searchService.findByVmeIdentifier(vme_Identifier, 0);
			
//			for (int j = 0; j< responseList.size(); j++) {
//				jsonArray.put(getJSONObject(responseList.get(j)));
//			}
			
			return Response.status(200).entity(responseList).build();
			
		} else return Response.status(500).build();		
	}
	
//	public JSONObject getJSONObject(SpecificMeasure specificMeasure) {
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("Id", specificMeasure.getId());
//            obj.put("Text", u.getEnglish(specificMeasure.getVmeSpecificMeasure()));
//            obj.put("Year", specificMeasure.getYear());
//            obj.put("Validity period start", specificMeasure.getValidityPeriod().getBeginDate());
//            obj.put("Validity period end", specificMeasure.getValidityPeriod().getEndDate());
//        } catch (JSONException e) {
//        	this.log.error("DefaultListItem.toString JSONException: "+e.getMessage(), e);
//        }
//        return obj;
//    }

	@SuppressWarnings("unused")
	private String produceHtmlReport(ObservationsRequest dto) {
		return "<html> " + "<title>" + "Hello Jersey" + "</title>" + "<body><h1>" + "Hello Jersey" + "</body></h1>"
				+ dto.getUuid() + "</br>" + "ObjectId Authority....:" + dto.getAuthority() + "</br>"
				+ "ObjectId areatype.....:" + dto.getType() + "</br>" + "ObjectId criteria.....:" + dto.getCriteria()
				+ "</br>" + "Year............:" + dto.getYear() + "</br>" + "</html> ";
	}

}