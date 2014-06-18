/**
 * (c) 2014 FAO / UN (project: reports-store-gateway-web-base)
 */
package org.fao.fi.vme.rsg.app.configuration;

import org.fao.fi.security.server.javax.filters.origin.IPRestrictedResourceRequestValidatorFilter;
import org.fao.fi.security.server.javax.interceptors.EncryptedResourceWriterInterceptor;
import org.fao.fi.vme.rsg.security.pgp.javax.filters.token.VmeRsgEncryptedTokenSecuredResourceRequestValidatorFilter;
import org.fao.fi.vme.rsg.security.pgp.javax.filters.token.VmeRsgPlainTokenSecuredResourceRequestValidatorFilter;
import org.gcube.application.rsg.webservice.support.JAXBContextProvider;
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
public class VmeRsgServicesConfiguration extends ResourceConfig {
	/**
	 * Class constructor
	 */
	public VmeRsgServicesConfiguration() {
		//Security interceptors and filters (server-side)
		register(EncryptedResourceWriterInterceptor.class);
		register(IPRestrictedResourceRequestValidatorFilter.class);
		register(VmeRsgPlainTokenSecuredResourceRequestValidatorFilter.class);
		register(VmeRsgEncryptedTokenSecuredResourceRequestValidatorFilter.class);
		
		//JAXB mappers
		register(JAXBContextProvider.class);
		register(JacksonFeature.class);
	}
}
