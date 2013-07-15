package org.vme.web.service;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.vme.service.dto.VmeSearchRequestDto;
import org.vme.service.dto.VmeSearchResult;
import org.vme.service.search.vme.SearchService;
import org.vme.service.search.vme.VmeSearchService;

@Path("/search-params/{id_authority}/{id_vme_type}/{id_vme_criteria}/{year}")
@Singleton
public class VmeSearchWs {


	private final SearchService service;

	@Inject
	public VmeSearchWs(VmeSearchService serv) {
		service = serv;
	}

	@Path("/search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_HTML)
	public Response find(
			@QueryParam("text") String text,
			@PathParam("id_authority") String id_authority, 
			@PathParam("id_vme_type") String id_vme_type, 
			@PathParam("id_vme_criteria") String id_vme_criteria,
			@PathParam("year") String year) {
		
		VmeSearchRequestDto requestDto = new VmeSearchRequestDto(UUID.randomUUID());
		if (!("*").equals(id_authority.trim())){
			requestDto.setAuthority(Integer.parseInt(id_authority));
		}
		if (!("*").equals(id_vme_type.trim())){
			requestDto.setType(Integer.parseInt(id_vme_type));	
		}
		if (!("*").equals(id_vme_criteria.trim())){
			requestDto.setCriteria(Integer.parseInt(id_vme_criteria));
		}
		if (!("*").equals(year.trim())){
			requestDto.setCriteria(Integer.parseInt(year));
		}
		if (requestDto.getAuthority() > 0) {
			System.out.println("REST SERVICE: Service was called with param id: [" + id_authority + "] - text param:  " + text);
			VmeSearchResult result =  service.search(requestDto);
			return Response.status(200).entity(result).build();
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
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