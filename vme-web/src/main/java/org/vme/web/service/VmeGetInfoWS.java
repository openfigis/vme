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

@Path("/vme")
@Singleton
public class VmeGetInfoWS {

	@Inject
	public GetInfoService getInfoService;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static final String ERROR = "Unable to retrieve the requested information, please contact technical support";

	public VmeGetInfoWS() {
		this.log.info("Initializing {} as a response handler", this.getClass().getSimpleName());
	}

	@GET
	@Path("/{vmeIdentifier}/year/{vmeYear}/specificmeasure/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("vmeIdentifier") String vmeIdentifier, @PathParam("vmeYear") String vmeYear) {

		try {
			return Response.status(200)
					.entity(getInfoService.vmeIdentifier2SpecificMeasure(vmeIdentifier, Integer.parseInt(vmeYear)))
					.build();
		} catch (Exception e) {
			log.error(ERROR, e.getMessage(), e);
			return Response.status(500).entity(ERROR).build();
		}

	}

	@GET
	@Path("/{vmeIdentifier}/specificmeasure/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("vmeIdentifier") String vmeIdentifier) {
		try {
			return Response.status(200).entity(getInfoService.vmeIdentifier2SpecificMeasure(vmeIdentifier, 0)).build();
		} catch (Exception e) {
			log.error(ERROR, e.getMessage(), e);
			return Response.status(500).entity(ERROR).build();
		}
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
	public Response vmeIdentifierSpecificmeasures(@PathParam("vmeIdentifier") String vmeIdentifier) {
		try {
			return Response.status(200).entity(getInfoService.vmeIdentifier2SpecificmeasureXML(vmeIdentifier, 0))
					.build();
		} catch (Exception e) {
			log.error(ERROR, e.getMessage(), e);
			return Response.status(500).entity(ERROR).build();
		}
	}

	/**
	 * Similarly to
	 * http://figisapps.fao.org/figis/ws/vme/webservice/vme/VME_NAFO_12
	 * /specificmeasure.xml there should be another web service which given a
	 * foreignID (e.g.VME_NAFO_12) it returns
	 * http://figisapps.fao.org/figis/ws/vme
	 * /webservice/vme/VME_NAFO_12/generalmeasure.xml with year="xxx" oid="xxx"
	 * id="xxx" lang="xx" validityPeriodStart="xxxxx" validityPeriodEnd="xxxx"
	 * 
	 * @param vmeIdentifier
	 * @return
	 */
	@GET
	@Path("/{vmeIdentifier}/generalmeasure.xml/")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response vmeIdentifierGeneralMeasures(@PathParam("vmeIdentifier") String vmeIdentifier) {
		try {
			return Response.status(200).entity(getInfoService.vmeIdentifierGeneralMeasures(vmeIdentifier, 0)).build();
		} catch (Exception e) {
			log.error(ERROR, e.getMessage(), e);
			return Response.status(500).entity(ERROR).build();
		}
	}

}
