/**
 * (c) 2014 FAO / UN (project: vme-dao)
 */
package org.vme.dao.config.figis;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.vme.dao.config.AbstractDataBaseProducer;
import org.vme.dao.config.PersistenceUnitConfiguration;

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
@Alternative
public class FigisDataBaseProducer extends AbstractDataBaseProducer {
	@Inject @FigisDB protected PersistenceUnitConfiguration _config;
	
	@Produces
	@FigisDB
	@ApplicationScoped
	public EntityManager produceEntityManager() {
		return create().createEntityManager();
	}
	
	// Marks create() as a producer methods using its return type to determine
	// what type of beans it can produce.
	@Produces
	public EntityManagerFactory create() {
		return Persistence.createEntityManagerFactory(this._config.getPersistenceUnitName());
	}
}