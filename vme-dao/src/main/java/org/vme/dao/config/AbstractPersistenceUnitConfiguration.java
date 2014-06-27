/**
 * (c) 2014 FAO / UN (project: vme-dao)
 */
package org.vme.dao.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 19 Feb 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 19 Feb 2014
 */
public abstract class AbstractPersistenceUnitConfiguration implements PersistenceUnitConfiguration {
	protected static Logger LOG = LoggerFactory.getLogger(AbstractPersistenceUnitConfiguration.class);
	
	public final String getPersistenceUnitName() {
		String persistanceUnitName = this.doGetPersistenceUnitName();
		
		LOG.info("Returning {} as persistance unit name for {}", persistanceUnitName, this.getClass().getSimpleName());
		
		return persistanceUnitName;
	}
	
	protected abstract String doGetPersistenceUnitName();
}
