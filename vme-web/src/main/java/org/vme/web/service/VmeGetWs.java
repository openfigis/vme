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

import org.vme.service.dto.VmeGetRequestDto;
import org.vme.service.dto.VmeSearchRequestDto;
import org.vme.service.dto.VmeSearchResult;
import org.vme.service.search.vme.SearchService;
import org.vme.service.search.vme.VmeSearchService;

@Path("/get")
@Singleton
public class VmeGetWs {


	private final SearchService service;

	@Inject
	public VmeGetWs(VmeSearchService serv, DbBootstrapper bootstrapper) {
		service = serv;
			try {
				bootstrapper.bootDb();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	//@Path("/search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_HTML)
	public Response find(
			@QueryParam("id") String id) {

		VmeGetRequestDto requestDto = new VmeGetRequestDto(UUID.randomUUID());
		
		if ((id!=null) &&!("*").equals(id.trim())){
			requestDto.setId(Integer.parseInt(id));
		} else {
			requestDto.setId(0);
		}
		System.out.println("FS: get service called with [" + id  +  "]");
		VmeSearchResult result =  service.get(requestDto);
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