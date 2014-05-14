package org.vme.web.service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.service.CsvService;

/**
 * 
 * @author Roberto Empiri; Fabio Fiorellato
 * 
 */

@Path ("/rfmo")
@Singleton
public class CsvWs {
	private Logger _log = LoggerFactory.getLogger(this.getClass());

	@Inject private CsvService _csvService;
	
	public CsvWs() {
		this._log.info("Initializing {} as a response handler", this.getClass().getSimpleName());
	}

	@GET
	@Path ("/{authority}")
	@Produces("text/csv")
	public Response name(@PathParam("authority") String id_authority){
		try {
			String csv;
			return Response.
						status(200).
						header("Content-Disposition","attachment;filename=VME_" + id_authority + ".csv").
						header("Content-Length", (csv = this._csvService.createCsvFile(id_authority)).length()).
						entity(csv).build();
		} catch(Throwable t) {
			this._log.error("Unexpected error caught: {}", t.getMessage(), t);
			return Response.status(500).entity(t.getMessage()).build();
		}
	}	
	
}
