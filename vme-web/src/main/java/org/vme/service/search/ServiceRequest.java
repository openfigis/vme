/**
 * 
 */
package org.vme.service.search;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Fabrizio Sibeni
 * 
 */
public class ServiceRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 385472728994930669L;
	/**
	 * UUID of the request. Generated by the system on request action
	 */
	private UUID uuid;

	/**
	 * @param uuid
	 */
	public ServiceRequest(UUID uuid) {
		super();
		this.uuid = uuid;
	}

	/**
	 * @return the uuid of the request
	 */
	public String getUuid() {
		return uuid.toString();
	}

}
