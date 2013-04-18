package org.fao.fi.vme.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class VmeDao {

	@Inject
	private EntityManager em;

	// public void persistVme(Vme vme) {
	// EntityTransaction t = em.getTransaction();
	// t.begin();
	// em.persist(vme);
	// t.commit();
	// em.close();
	// }
}
