/**
 * (c) 2014 FAO / UN (project: vme-reports-store-gateway-web)
 */
package org.fao.fi.vme.rsg.security.pgp.configuration;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.naming.NamingException;

import org.fao.fi.security.common.pgp.configuration.impl.JNDIKeyringConfigurator;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 1 May 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 1 May 2014
 */
@Singleton @Named("pgp.public.krcfg")
public class IMarineKeyringConfigurator extends JNDIKeyringConfigurator {
	/**
	 * Class constructor
	 *
	 * @throws NamingException
	 */
	public IMarineKeyringConfigurator() throws NamingException {
		super("java:comp/env/pgp/keyring/imarine");
	}
}
