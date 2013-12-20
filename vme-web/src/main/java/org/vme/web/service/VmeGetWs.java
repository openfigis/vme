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

import org.vme.service.dao.DAOFactory;
import org.vme.web.service.io.ObservationsRequest;
import org.vme.web.service.io.ServiceResponse;

@Path("/get")
@Singleton
public class VmeGetWs {

	@Inject
	private DAOFactory factory;

	/*
	 * @Inject public VmeGetWs(VmeSearchService serv, DbBootstrapper
	 * bootstrapper) { service = serv; try { bootstrapper.bootDb(); } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */

	public VmeGetWs() {
		// factory = serv;
	}

	// @Path("/search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.TEXT_HTML)
	public Response find(@QueryParam("id") String id, @QueryParam("year") String year,
			@QueryParam("inventoryIdentifier") String inventoryIdentifier,
			@QueryParam("geographicFeatureId") String geographicFeatureId) throws Exception {

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
		ServiceResponse<?> result = ServiceInvoker.invoke(factory.getObservationDAO(), request);
		return Response.status(200).entity(result).build();
	}

	@SuppressWarnings("unused")
	private String produceHtmlReport(ObservationsRequest dto) {
		return "<html> " + "<title>" + "Hello Jersey" + "</title>" + "<body><h1>" + "Hello Jersey" + "</body></h1>"
				+ dto.getUuid() + "</br>" + "Id Authority....:" + dto.getAuthority() + "</br>" + "Id areatype.....:"
				+ dto.getType() + "</br>" + "Id criteria.....:" + dto.getCriteria() + "</br>" + "Year............:"
				+ dto.getYear() + "</br>" + "</html> ";
	}

}