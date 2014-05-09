/**
 * (c) 2014 FAO / UN (project: fi-security-server)
 */
package org.fao.fi.vme.rsg.security.pgp.javax.filters;

import javax.inject.Inject;

import org.fao.fi.security.server.javax.filters.token.AbstractTokenSecuredResourceRequestValidatorFilter;
import org.fao.fi.security.server.javax.filters.token.support.EncryptedTokenSecuredResource;
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
public class VmeRsgEncryptedTokenSecuredResourceRequestValidatorFilter extends AbstractTokenSecuredResourceRequestValidatorFilter {
	/**
	 * Class constructor
	 *
	 * @param validator
	 */
	@Inject public VmeRsgEncryptedTokenSecuredResourceRequestValidatorFilter(@EncryptedTokenSecuredResource EncryptedTokenConsumer validator) {
		super(validator);
	}
}