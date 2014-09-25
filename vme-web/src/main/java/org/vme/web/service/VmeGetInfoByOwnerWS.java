package org.vme.web.service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.service.GetInfoService;

@Path("/owner")
@Singleton
public class VmeGetInfoByOwnerWS {

	@Inject
	public GetInfoService getInfoService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public VmeGetInfoByOwnerWS() {
		this.log.info("Initializing {} as a response handler", this.getClass().getSimpleName());
	}

	@GET
	@Path("/{owner}/scope/{scope}/year/{year}/vmes")
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized Response find(@PathParam("owner") String owner, @PathParam("scope") String scope,
			@PathParam("year") String year) {

		return Response.status(200).entity(getInfoService.ownerScope2Vmes(owner, scope, Integer.parseInt(year))).build();

	}

	@GET
	@Path("/{owner}/scope/{scope}/vmes")
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized Response findByOwner(@PathParam("owner") String owner, @PathParam("scope") String scope,
			@PathParam("year") String year) {

		return Response.status(200).entity(getInfoService.ownerScope2Vmes(owner, scope, 0)).build();

	}
}
