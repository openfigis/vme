/**
 * (c) 2014 FAO / UN (project: vme-dao)
 */
package org.vme.dao.config.figis;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.config.AbstractDataBaseProducer;
import org.vme.dao.config.PersistenceUnitConfiguration;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- ----------------------- Date Author Comment ------------- ---------------
 * ----------------------- 19 Feb 2014 Fiorellato Creation.
 *
 * @version 1.0
 * @since 19 Feb 2014
 */

public class FigisDataBaseProducer extends AbstractDataBaseProducer {

	@Inject
	@FigisDB
	protected PersistenceUnitConfiguration config;

	@Produces
	@FigisDB
	@ApplicationScoped
	public EntityManager produceEntityManager() {
		return Persistence.createEntityManagerFactory(this.config.getPersistenceUnitName()).createEntityManager();
	}

	protected static Logger LOG = LoggerFactory.getLogger(FigisDataBaseProducer.class);

}