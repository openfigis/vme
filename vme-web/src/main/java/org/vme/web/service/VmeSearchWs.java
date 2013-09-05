package org.vme.web.service;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.vme.service.dto.VmeSearchRequestDto;
import org.vme.service.dto.VmeSearchResult;
import org.vme.service.search.vme.SearchService;
import org.vme.service.search.vme.VmeSearchService;

@Path("/search")
@Singleton
public class VmeSearchWs {


	private final SearchService service;
	
	/*
	@Inject
	public VmeSearchWs(VmeSearchService serv, DbBootstrapper bootstrapper) {
		service = serv;
			try {
				bootstrapper.bootDb();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    */

	@Inject
	public VmeSearchWs(VmeSearchService serv) {
		service = serv;
	}

	
	//@Path("/search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_HTML)
	public Response find(
			@QueryParam("text") String text,
			@QueryParam("authority") String id_authority, 
			@QueryParam("vme_type") String id_vme_type, 
			@QueryParam("vme_criteria") String id_vme_criteria,
			@QueryParam("year") String year) throws Exception {

		VmeSearchRequestDto requestDto = new VmeSearchRequestDto(UUID.randomUUID());
		if ((id_authority!=null) &&!("*").equals(id_authority.trim())){
			requestDto.setAuthority(Integer.parseInt(id_authority));
		} else {
			requestDto.setAuthority(0);
		}
		if ((id_vme_type!=null) &&!("*").equals(id_vme_type.trim())){
			requestDto.setType(Integer.parseInt(id_vme_type));	
		} else {
			requestDto.setType(0);
		}
		if ((id_vme_criteria!=null) && !("*").equals(id_vme_criteria.trim())){
			requestDto.setCriteria(Integer.parseInt(id_vme_criteria));
		} else {
			requestDto.setCriteria(0);
		}
		if ((year!=null) &&!("*").equals(year.trim())){
			requestDto.setYear(Integer.parseInt(year));
		} else {
			requestDto.setYear(0);
		}
		System.out.println("FS: called with [" + id_authority + " - " + id_vme_type + " - " + id_vme_criteria +  "] - text param:  " + text);
		VmeSearchResult result =  service.search(requestDto);
		return Response.status(200).entity(result).build();
	}


	@SuppressWarnings("unused")
	private String produceHtmlReport(VmeSearchRequestDto dto)	{
		return 
				"<html> " + "<title>" + "Hello Jersey" + "</title>" + 
				"<body><h1>" + "Hello Jersey" + "</body></h1>" + dto.getUuid()  + "</br>"  
				+ "Id Authority....:" + dto.getAuthority() + "</br>"
				+ "Id areatype.....:" + dto.getType() + "</br>"
				+ "Id criteria.....:" + dto.getCriteria() + "</br>"
				+ "Year............:" + dto.getYear() + "</br>"
				+"</html> ";
	}




}