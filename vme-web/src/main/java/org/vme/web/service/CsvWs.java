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
	
	private static String MESSAGE = "Hello web-app";

	@Inject private CsvService _csvService;
	
	public CsvWs() {
		this._log.info("Initializing {} as a response handler", this.getClass().getSimpleName());
	}
	
	@Path ("/ciao")
	@GET
	@Produces(MediaType.TEXT_PLAIN) //http://stackoverflow.com/questions/18342895/jersey-rest-and-csv-response
	public String ciao(){
		String ciao = MESSAGE;
		
		return ciao;
	}

	@GET
	@Path ("/try/{authority}")
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
	
	/*
	@GET
	@Path("/ReportWithoutADEStatus")
	@Produces({ "application/ms-excel"})
	public Response generateQurterlyReport(){
    QuarterlyLabelReport quartLabelReport = new QuarterlyLabelReport();
    String fileLoc=quartLabelReport.generateQurterlyLblRep(false);
    File file=new File(fileLoc);
    return Response.ok(fileLoc,"application/ms-excel")
            .header( "Content-Disposition","attachment;filename=QuarterlyReport_withoutADE.csv")
            .build();
}

	@Path ("/try")
	@GET
	public Response name(@QueryParam("authority") String id_authority){
		
		Method[] methods = GeneralMeasure.class.getMethods();
		
		List<List<String>> csv = new ArrayList<List<String>>(); 
		
		for (List<String> list : csv) {
			for (String string : list) {
				
				GeneralMeasure g = new GeneralMeasure();
				
				if (g.getRfmo().getId().equals(id_authority)) {
					
				}
				
				
				
			}
			
		}
		
		
	}


*/
	
	
}
