package org.vme.web.service;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.fao.fi.vme.domain.model.Authority;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.ReferenceDAO;
import org.vme.dao.ReferenceServiceException;
import org.vme.service.XlsService;

/**
 * 
 * @author Roberto Empiri
 * 
 */

@Path("/rfmo")
@Singleton
public class XlsWs {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	private XlsService xlsService;

	@Inject
	@ConceptProvider
	ReferenceDAO referenceDAO;

	public XlsWs() {
		this.log.info("Initializing {} as a response handler", this.getClass().getSimpleName());
	}

	@GET
	@Path("/{authority}")
	@Produces("application/vnd.ms-excel")
	public synchronized Response name(@PathParam("authority") String idAuthority) {

		boolean cool = true;

		try {
			if (referenceDAO.getReferenceByAcronym(Authority.class, idAuthority) == null) {
				log.warn("authority == null");
				cool = false;
			}
		} catch (ReferenceServiceException e) {
			throw new WebApplicationException(e);
		}

		if (cool) {
			StreamingOutput stream = null;
			final ByteArrayInputStream in = this.xlsService.createXlsFile(idAuthority);
			stream = new StreamingOutput() {
				public void write(OutputStream out) {
					try {
						int read = 0;
						byte[] bytes = new byte[1024];

						while ((read = in.read(bytes)) != -1) {
							out.write(bytes, 0, read);
						}
					} catch (Exception e) {
						throw new WebApplicationException(e);
					}
				}
			};
			return Response
					.ok(stream)
					.header("content-disposition",
							"attachment; filename = " + idAuthority + "_VME-DataBase_Summary_"
									+ xlsService.dataString() + ".xls").build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("No XLS found for this Rfmo").build();
		}
	}
}