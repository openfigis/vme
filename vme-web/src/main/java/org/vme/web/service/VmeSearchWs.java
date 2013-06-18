package org.vme.web.service;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.vme.service.VmeSearchService;
import org.vme.service.dto.VmeDto;
import org.vme.service.dto.VmeSearchRequestDto;
import org.vme.service.dto.VmeServiceResult;

@Path("/search-params/{id_authority}/{id_areatype}/{id_status}/{id_vmecriteria}/{year}")
@Singleton
public class VmeSearchWs {


	private final VmeSearchService service;

	@Inject
	public VmeSearchWs(VmeSearchService serv) {
		service = serv;
	}

	@Path("/search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_HTML)
	public Response find(@PathParam("id_authority") int id_authority, 
			@PathParam("id_areatype") int id_areatype, 
			@PathParam("id_status") int id_status,
			@PathParam("id_criteria") int id_criteria,
			@PathParam("year") int year) {

		
		
		
		VmeSearchRequestDto requestDto = new VmeSearchRequestDto(UUID.randomUUID());
		
		requestDto.setAuthority(id_authority);
		requestDto.setAreatype(id_areatype);
		requestDto.setStatus(id_status);
		requestDto.setCriteria(id_criteria);
		requestDto.setYear(year);
		
		if (id_authority > 0) {
			System.out.println("FIRST REST: Service was called with param id: [" + id_authority + "] ");
			VmeServiceResult result =  service.retrieveResultsFor(requestDto);
			return Response.status(200).entity(result).build();
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}


	private String produceHtmlReport(VmeSearchRequestDto dto)	{
		return 
				"<html> " + "<title>" + "Hello Jersey" + "</title>" + 
						"<body><h1>" + "Hello Jersey" + "</body></h1>" + dto.getUuid()  + "</br>"  
						+ "Id Authority....:" + dto.getAuthority() + "</br>"
						+ "Id areatype.....:" + dto.getAreatype() + "</br>"
						+ "Id status.......:" + dto.getStatus() + "</br>"
						+ "Id criteria.....:" + dto.getCriteria() + "</br>"
						+ "Year............:" + dto.getYear() + "</br>"
						+"</html> ";
	}
	
	
	

}