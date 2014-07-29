package org.vme.web.service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.service.GetInfoService;

import com.sun.jersey.spi.MessageBodyWorkers;

@Path("/vme")
@Singleton
public class VmeGetInfoWS {

	@Context
	private MessageBodyWorkers workers;

	@Inject
	public GetInfoService getInfoService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public VmeGetInfoWS() {
		this.log.info("Initializing {} as a response handler", this.getClass().getSimpleName());
	}

	@GET
	@Path("/{vmeIdentifier}/year/{vmeYear}/specificmeasure/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("vmeIdentifier") String vmeIdentifier, @PathParam("vmeYear") String vmeYear)
			throws Exception {

		return Response.status(200).entity(getInfoService.vmeIdentifier2SpecificMeasure(vmeIdentifier, Integer.parseInt(vmeYear))).build();

	}

	@GET
	@Path("/{vmeIdentifier}/specificmeasure/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("vmeIdentifier") String vmeIdentifier) throws Exception {

		return Response.status(200).entity(getInfoService.vmeIdentifier2SpecificMeasure(vmeIdentifier, 0)).build();

	}

	/**
	 * /vme/VME_NAFO_12/factsheet/specificmeasures
	 * 
	 * @param vmeIdentifier
	 * @return
	 * @throws Exception
	 */

	@GET
	@Path("/{vmeIdentifier}/specificmeasure.xml/")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	// @Produces({ MediaType.APPLICATION_JSON })
	public Response vmeIdentifierSpecificmeasures(@PathParam("vmeIdentifier") String vmeIdentifier) throws Exception {

		return Response.status(200).entity(getInfoService.vmeIdentifier2SpecificmeasureXML(vmeIdentifier, 0)).build();

	}

}
