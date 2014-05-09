/**
 * (c) 2014 FAO / UN (project: fi-security-integration)
 */
package org.fao.fi.vme.rsg.security.services.token;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.naming.NamingException;
import javax.ws.rs.Path;

import org.fao.fi.security.server.javax.filters.bandwidth.support.BandwidthLimitedResource;
import org.fao.fi.security.server.javax.filters.origin.support.IPRestrictedResource;
import org.fao.fi.security.server.providers.validators.token.impl.plain.PlainTokenProducer;
import org.fao.fi.security.server.services.providers.validators.token.impl.LocalTokenProducerServiceImpl;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 5 May 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 5 May 2014
 */
@Singleton @IPRestrictedResource @BandwidthLimitedResource @Path("/token/plain") 
public class PlainTokenProducerServiceImpl extends LocalTokenProducerServiceImpl {
	/**
	 * Class constructor
	 */
	@Inject public PlainTokenProducerServiceImpl(PlainTokenProducer tokenProducer) throws NamingException {
		super(tokenProducer);
	}
}