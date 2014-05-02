/**
 * (c) 2014 FAO / UN (project: reports-store-gateway-web-base)
 */
package org.fao.fi.vme.rsg.app;

import org.fao.fi.security.server.filters.javax.SecureResourceRequestValidatorFilter;
import org.fao.fi.security.server.interceptors.javax.EncryptedResourceResponseWriter;
import org.glassfish.jersey.jackson.JacksonFeature;
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
public class WebAppConfiguration extends ResourceConfig {
	/**
	 * Class constructor
	 */
	public WebAppConfiguration() {
		register(EncryptedResourceResponseWriter.class);
		register(SecureResourceRequestValidatorFilter.class);
		register(JacksonFeature.class);
	}
}
