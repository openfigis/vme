package org.vme.web.service;

import java.util.Calendar;

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


@Path("/vme")
@Singleton
public class VmeGetInfoWS {

	@Inject
	public GetInfoService getInfoService;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public VmeGetInfoWS() {
		this.log.info("Initializing {} as a response handler", this.getClass().getSimpleName());
	}
	
	@GET
	@Path("/specificmeasure/{vmeIdentifier}/{vmeYear}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("vmeIdentifier") String vmeIdentifier, @PathParam("vmeYear") String vmeYear) throws Exception {
		
		return Response.status(200).entity(getInfoService.findInfo(vmeIdentifier, Integer.parseInt(vmeYear))).build();
		
	}
	
	@GET
	@Path("/specificmeasure/{vmeIdentifier}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("vmeIdentifier") String vmeIdentifier) throws Exception {
		
		return Response.status(200).entity(getInfoService.findInfo(vmeIdentifier, 0)).build();
		
	}
	
	@GET
	@Path("/owner/{owner}/{scope}/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("owner") String owner, @PathParam("scope") String scope,
			@PathParam("year") String year) {
		
		return Response.status(200).entity(getInfoService.findInfo(owner, scope, Integer.parseInt(year))).build();
		
	}
	
	@GET
	@Path("/owner/{owner}/{scope}/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByOwner(@PathParam("owner") String owner, @PathParam("scope") String scope,
			@PathParam("year") String year) {
		
		return Response.status(200).entity(getInfoService.findInfo(owner, scope, Calendar.getInstance().get(Calendar.YEAR))).build();
		
	}
	
}
