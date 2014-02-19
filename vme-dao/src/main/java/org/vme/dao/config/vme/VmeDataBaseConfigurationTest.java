package org.vme.dao.config.vme;

import javax.enterprise.inject.Alternative;

import org.vme.dao.config.AbstractDataBaseConfiguration;

/**
 * 
 * Produces a link to the vme DB.
 * 
 * @author Erik van Ingen
 * 
 */
@Alternative @VmeDB
public class VmeDataBaseConfigurationTest extends AbstractDataBaseConfiguration {
	/* (non-Javadoc)
	 * @see org.vme.dao.config.AbstractDataBaseConfiguration#doGetPersistanceUnitName()
	 */
	@Override
	protected String doGetPersistenceUnitName() {
		return "vme-persistence-test";
	}
}