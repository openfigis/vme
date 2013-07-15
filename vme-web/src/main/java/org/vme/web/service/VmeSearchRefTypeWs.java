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

import org.vme.service.dto.VmeSearchRefRequestDto;
import org.vme.service.dto.VmeSearchRefResult;
import org.vme.service.search.reference.VmeSearchRefService;

@Path("/references/{concept}/{lang}/")
@Singleton
public class VmeSearchRefTypeWs {


	private final VmeSearchRefService service;

	@Inject
	public VmeSearchRefTypeWs(VmeSearchRefService a_service) {
		service = a_service;
	}

	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_HTML)
	public Response find(@PathParam("concept") String concept, @PathParam("lang") String lang) {

		VmeSearchRefRequestDto requestDto = new VmeSearchRefRequestDto(UUID.randomUUID());
		
		requestDto.setConcept(concept);
		requestDto.setLang(lang);
		
		try {
			VmeSearchRefResult result =  service.search(requestDto);
			return Response.status(200).entity(result).build();
		} catch (Exception e){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
	}


}