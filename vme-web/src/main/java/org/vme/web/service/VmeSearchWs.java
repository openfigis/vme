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

import org.vme.service.dao.VmeSearchDao;
import org.vme.web.service.io.ObservationsRequest;
import org.vme.web.service.io.ServiceResponse;

@Path("/search")
@Singleton
public class VmeSearchWs {

	@Inject
	private VmeSearchDao vmeSearchDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@QueryParam("text") String text, @QueryParam("authority") String id_authority,
			@QueryParam("vme_type") String id_vme_type, @QueryParam("vme_criteria") String id_vme_criteria,
			@QueryParam("year") String year) throws Exception {

		ObservationsRequest request = new ObservationsRequest(UUID.randomUUID());
		request.setText(text);
		if ((id_authority != null) && !("*").equals(id_authority.trim())) {
			request.setAuthority(Integer.parseInt(id_authority));
		} else {
			request.setAuthority(0);
		}
		if ((id_vme_type != null) && !("*").equals(id_vme_type.trim())) {
			request.setType(Integer.parseInt(id_vme_type));
		} else {
			request.setType(0);
		}
		if ((id_vme_criteria != null) && !("*").equals(id_vme_criteria.trim())) {
			request.setCriteria(Integer.parseInt(id_vme_criteria));
		} else {
			request.setCriteria(0);
		}
		if ((year != null) && !("*").equals(year.trim())) {
			request.setYear(Integer.parseInt(year));
		} else {
			request.setYear(0);
		}
		ServiceResponse<?> result = ServiceInvoker.invoke(vmeSearchDao, request);
		return Response.status(200).entity(result).build();
	}

	@SuppressWarnings("unused")
	private String produceHtmlReport(ObservationsRequest dto) {
		return "<html> " + "<title>" + "Hello Jersey" + "</title>" + "<body><h1>" + "Hello Jersey" + "</body></h1>"
				+ dto.getUuid() + "</br>" + "ObjectId Authority....:" + dto.getAuthority() + "</br>"
				+ "ObjectId areatype.....:" + dto.getType() + "</br>" + "ObjectId criteria.....:" + dto.getCriteria()
				+ "</br>" + "Year............:" + dto.getYear() + "</br>" + "</html> ";
	}

}