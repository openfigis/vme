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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.service.SearchService;
import org.vme.service.search.ObservationsRequest;
import org.vme.service.search.ServiceResponse;

@Path("/search")
@Singleton
public class VmeSearchWs {

	private static String NUMERICAL_PATTERN = "\\d+";

	@Inject
	private SearchService service;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static final String ERROR = "Unable to retrieve the requested information, please contact technical support";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized Response find(@QueryParam("text") String text, @QueryParam("authority") String idAuthority,
			@QueryParam("vme_type") String idVmeType, @QueryParam("vme_criteria") String idVmeCriteria,
			@QueryParam("year") String year) throws Exception {

		ObservationsRequest request = new ObservationsRequest(UUID.randomUUID());
		request.setText(text);
		// System.out.println(idAuthority.matches(NUMERICAL_PATTERN));
		if (idAuthority != null && !("*").equals(idAuthority.trim()) && idAuthority.matches(NUMERICAL_PATTERN)) {
			request.setAuthority(Integer.parseInt(idAuthority));
		} else {
			request.setAuthority(0);
		}
		if ((idVmeType != null) && !("*").equals(idVmeType.trim()) && idAuthority.matches(NUMERICAL_PATTERN)) {
			request.setType(Integer.parseInt(idVmeType));
		} else {
			request.setType(0);
		}
		if ((idVmeCriteria != null) && !("*").equals(idVmeCriteria.trim()) && idAuthority.matches(NUMERICAL_PATTERN)) {
			request.setCriteria(Integer.parseInt(idVmeCriteria));
		} else {
			request.setCriteria(0);
		}
		if ((year != null) && !("*").equals(year.trim())) {
			request.setYear(Integer.parseInt(year));
		} else {
			request.setYear(0);
		}
		ServiceResponse<?> result = service.invoke(request);
		try {
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			log.error(ERROR, e.getMessage(), e);
			return Response.status(500).entity(ERROR).build();
		}
	}
}