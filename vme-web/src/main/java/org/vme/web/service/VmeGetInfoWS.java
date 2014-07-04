package org.vme.web.service;

import java.util.UUID;

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

 	public VmeSmResponse vmeSmResponse;

	@GET
	@Path("/specificmeasure/{vmeIdentifier}/{vmeYear}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("vmeIdentifier") String vmeIdentifier, @PathParam("vmeYear") String vmeYear) throws Exception {
		
		vmeSmResponse = new VmeSmResponse(UUID.randomUUID());
		vmeSmResponse.createResponse(vmeIdentifier, Integer.parseInt(vmeYear));
		
		return Response.status(200).entity(vmeSmResponse).build();
		
	}
	
	@GET
	@Path("/specificmeasure/{vmeIdentifier}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("vmeIdentifier") String vmeIdentifier) throws Exception {
		
		vmeSmResponse = new VmeSmResponse(UUID.randomUUID());
		vmeSmResponse.createResponse(vmeIdentifier);
		
		return Response.status(200).entity(vmeSmResponse).build();
		
	}
	
	
}
