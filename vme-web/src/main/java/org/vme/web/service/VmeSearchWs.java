package org.vme.web.service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.vme.service.VmeSearchService;
import org.vme.service.dto.VmeDto;

@Path("/fabrizio/{id}")
@Singleton
public class VmeSearchWs {

	@Inject
	private VmeSearchService service;

	@Path("/somethingvme")
	@GET
	@Produces("application/json")
	public Response find(@PathParam("id") int id) {

		if (id > 1) {
			VmeDto vmeDto = service.retrieveResultsFor(id);
			return Response.status(200).entity(vmeDto.getName()).build();
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

	}

}
