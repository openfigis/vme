/**
 * (c) 2014 FAO / UN (project: reports-store-gateway-web-base)
 */
package org.fao.fi.vme.rsg.app.configuration;

import org.fao.fi.security.server.javax.filters.bandwidth.BandwidthLimitedResourceRequestValidatorFilter;
import org.fao.fi.security.server.javax.filters.origin.IPRestrictedResourceRequestValidatorFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 30 Apr 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 30 Apr 2014
 */
public class VmeRsgSecurityConfiguration extends ResourceConfig {
	/**
	 * Class constructor
	 */
	public VmeRsgSecurityConfiguration() {
//		register(BandwidthLimitedResourceRequestValidatorFilter.class);
//		register(IPRestrictedResourceRequestValidatorFilter.class);
	}
}
