package org.fao.fi.vme.dao.config;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Alternative
public class VmeEntityManagerProducer {

	@Inject
	EntityManagerFactory vmeEmf; // 1

	@Produces
	public EntityManager create() {
		return vmeEmf.createEntityManager();
	}

	public void destroy(@Disposes EntityManager em) {
		em.close();
	}
}