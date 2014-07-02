package org.vme.web.service;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.vme.service.dto.VmeSmResponse;


@Path("/vme")
@Singleton
public class VmeGetInfoWS {

	@GET
	@Path("/{vmeIdentifier}/specificmeasure/{vmeYear}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("vmeIdentifier") String vmeIdentifier, @PathParam("vmeYear") String vmeYear) throws Exception {
		
		if(vmeIdentifier != null && vmeYear == null){
			return Response.status(200).entity(new VmeSmResponse(vmeIdentifier)).build();
		} else if(vmeIdentifier != null && vmeYear != null) {
			return Response.status(200).entity(new VmeSmResponse(vmeIdentifier, Integer.parseInt(vmeYear))).build();
		} else return Response.status(500).build();		
	}
	
}
