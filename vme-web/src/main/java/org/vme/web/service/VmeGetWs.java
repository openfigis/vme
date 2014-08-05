package org.vme.web.service;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.fao.fi.vme.VmeException;
import org.vme.service.SearchService;
import org.vme.service.search.ObservationsRequest;
import org.vme.service.search.ServiceResponse;

@Path("/get")
@Singleton
public class VmeGetWs {
	
	@Inject
	private SearchService service;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@QueryParam("id") String id, @QueryParam("year") String year,
			@QueryParam("inventoryIdentifier") String inventoryIdentifier,
			@QueryParam("geographicFeatureId") String geographicFeatureId) {

		ObservationsRequest request = new ObservationsRequest(UUID.randomUUID());

		if ((id != null) && !("*").equals(id.trim())) {
			request.setId(Integer.parseInt(id));
		} else {
			request.setId(0);
		}

		if ((year != null) && !("*").equals(year.trim())) {
			request.setYear(Integer.parseInt(year));
		} else {
			request.setYear(0);
		}

		if ((inventoryIdentifier != null) && !("*").equals(inventoryIdentifier.trim())) {
			request.setInventoryIdentifier(inventoryIdentifier);
		} else {
			request.setInventoryIdentifier(null);
		}

		if ((geographicFeatureId != null) && !("*").equals(geographicFeatureId.trim())) {
			request.setGeographicFeatureId(geographicFeatureId);
		} else {
			request.setGeographicFeatureId(null);
		}
		ServiceResponse<?> result;
		
		try {
			result = service.invoke(request);
		} catch (Exception e) {
			throw new VmeException(e);
		}
		
		return Response.status(200).entity(result).build();
	}
}