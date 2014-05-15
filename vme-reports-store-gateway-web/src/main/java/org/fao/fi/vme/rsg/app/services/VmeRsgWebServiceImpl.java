/**
 * (c) 2014 FAO / UN (project: vme-reports-store-gateway-web)
 */
package org.fao.fi.vme.rsg.app.services;

import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.fao.fi.security.server.javax.filters.bandwidth.support.BandwidthLimitedResource;
import org.fao.fi.security.server.javax.filters.origin.support.IPRestrictedResource;
import org.fao.fi.security.server.javax.filters.token.support.EncryptedTokenSecuredResource;
import org.gcube.application.rsg.webservice.RSGWebService;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 9 May 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 9 May 2014
 */
@Singleton 
@EncryptedTokenSecuredResource 
@IPRestrictedResource 
@BandwidthLimitedResource 
@Path("/")
public class VmeRsgWebServiceImpl extends RSGWebService {
}