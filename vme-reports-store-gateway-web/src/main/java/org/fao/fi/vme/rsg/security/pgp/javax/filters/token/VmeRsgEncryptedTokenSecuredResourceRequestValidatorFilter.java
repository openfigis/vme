/**
 * (c) 2014 FAO / UN (project: fi-security-server)
 */
package org.fao.fi.vme.rsg.security.pgp.javax.filters.token;

import javax.inject.Inject;

import org.fao.fi.security.server.javax.filters.token.EncryptedTokenSecuredResourceRequestValidatorFilter;
import org.fao.fi.security.server.javax.filters.token.support.EncryptedTokenSecuredResource;
import org.fao.fi.security.server.javax.filters.token.support.EncryptedTokenSecurer;
import org.fao.fi.security.server.providers.validators.token.impl.encrypted.EncryptedTokenConsumer;

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
@EncryptedTokenSecuredResource
public class VmeRsgEncryptedTokenSecuredResourceRequestValidatorFilter extends EncryptedTokenSecuredResourceRequestValidatorFilter {
	/**
	 * Class constructor
	 *
	 * @param validator
	 */
	@Inject public VmeRsgEncryptedTokenSecuredResourceRequestValidatorFilter(@EncryptedTokenSecurer EncryptedTokenConsumer validator) {
		super(validator);
	}
}