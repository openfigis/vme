package org.vme.web.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.service.XlsService;

/**
 * 
 * @author Roberto Empiri
 * 
 */

@Path ("/rfmoXls")
@Singleton
public class XlsWs {
	private Logger _log = LoggerFactory.getLogger(this.getClass());
	
	@Inject private XlsService _xlsService;
	
	public XlsWs() {
		this._log.info("Initializing {} as a response handler", this.getClass().getSimpleName());
	}
	
	@GET
	@Path ("/{authority}")
	@Produces("application/vnd.ms-excel")
	public Response name(@PathParam("authority") String id_authority){
		
		StreamingOutput stream = null;
		try {
	        final ByteArrayInputStream in = this._xlsService.createXlsFile(id_authority);
	        stream = new StreamingOutput(){
	            public void write(OutputStream out) throws IOException, WebApplicationException{
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
		} catch (Throwable t) {
	        t.printStackTrace();
	    }
		
		
		
		try {
			return Response.ok(stream)
					.header("content-disposition","attachment; filename = "+id_authority+"_VME").build();
		} catch (Throwable t) {
			this._log.error("Unexpected error caught: {}", t.getMessage(), t);
			return Response.status(500).entity(t.getMessage()).build();
		} 
	
}
}