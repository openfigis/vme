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

import org.vme.service.hardcoded.impl.ReferenceHarcodedDao;
import org.vme.web.service.io.ReferencesRequest;
import org.vme.web.service.io.ServiceResponse;

@Path("/references/{concept}/{lang}/")
@Singleton
public class VmeSearchRefTypeWs {


	private final ReferenceHarcodedDao service;

	@Inject
	public VmeSearchRefTypeWs(ReferenceHarcodedDao a_service) {
		service = a_service;
	}

	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_HTML)
	public Response find(@PathParam("concept") String concept, @PathParam("lang") String lang) {
		try {
			ReferencesRequest refRequest = new ReferencesRequest(UUID.randomUUID());
			refRequest.setConcept(concept);
			ServiceResponse<?> result = ServiceInvoker.invoke(service, refRequest);
			return Response.status(200).entity(result).build();
		} catch (Exception e){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
	}


}