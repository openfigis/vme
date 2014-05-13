package org.vme.web.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.vme.dao.VmeSearchDao;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.service.CsvService;
import org.vme.web.service.io.ObservationsRequest;
import org.vme.web.service.io.ServiceRequest;
import org.vme.web.service.io.ServiceResponse;

import com.ning.http.client.Response.ResponseBuilder;

/**
 * 
 * @author Roberto Empiri
 * 
 */

@Path ("/rfmo")
@Singleton
public class CsvWs {
	
	private static String MESSAGE = "Hello web-app";

	@Inject
	CsvService ws;
	@Inject
	VmeDao vmeDao;
	
	@Path ("/ciao")
	@GET
	@Produces(MediaType.TEXT_PLAIN) //http://stackoverflow.com/questions/18342895/jersey-rest-and-csv-response
	public String ciao(){
		String ciao = MESSAGE;
		
		return ciao;
	}
	
	@Path ("/try")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response name(@QueryParam("authority") String id_authority){
		
		ObservationsRequest request = new ObservationsRequest(UUID.randomUUID());
		request.setAuthority(Integer.parseInt(id_authority));
		
		ServiceResponse<?> result = CsvService.invoke(vmeDao, request);
		
		return Response.status(200).entity(result).build();
		
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
