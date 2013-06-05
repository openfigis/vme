package org.vme.web.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.vme.service.VmeSearchService;
import org.vme.service.dto.VmeDto;

@Path("/fabrizio/{id}")
@RequestScoped
public class VmeSearchWs {

	@Inject
	VmeSearchService service;

	@Path("/somethingvme")
	@GET
	@Produces("json")
	public VmeDto find(@PathParam("id") int id) {

		if (id > 1) {
			return service.retrieveResultsFor(id);
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

	}

}
