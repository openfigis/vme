package org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb;

/**
 * This class represents the vme-web application, deployed for qa in
 * http://hqldvfigis1:6010/figis/ws/vme/, not yet deployed in production.
 * 
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeWebServer {
	private String server;

	public void setServer(String server) {
		this.server = server;
	}

	public String getServer() {

		return server;
	}

}
