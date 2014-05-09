/**
 * (c) 2014 FAO / UN (project: vme-reports-store-gateway-web)
 */
package org.fao.fi.vme.rsg.security.pgp.configuration;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.naming.NamingException;

import org.fao.fi.security.common.encryption.pgp.PGPBeanConstants;
import org.fao.fi.security.common.utilities.pgp.configuration.impl.jndi.SecretKeyringJNDIConfiguration;

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
@Singleton @Named(PGPBeanConstants.PGP_SECRET_KEYRING_CONFIG_BEAN_NAME)
public class VmeRsgPrivateKeyringConfiguration extends SecretKeyringJNDIConfiguration {
	/**
	 * Class constructor
	 *
	 * @throws NamingException
	 */
	public VmeRsgPrivateKeyringConfiguration() throws NamingException {
		super("pgp/keyring/vme", "pgp/passphrase/vme");
	}
}
