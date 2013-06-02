package org.fao.fi.vme.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.dao.Dao;
import org.fao.fi.vme.dao.config.VmeDB;
import org.fao.fi.vme.domain.Vme;

public class VmeDao extends Dao {

	@Inject
	@VmeDB
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Vme> loadVmes() {
		return (List<Vme>) this.generateTypedQuery(em, Vme.class).getResultList();
	}

	public void persist(Object object) {
		em.getTransaction().begin();
		em.persist(object);
		em.getTransaction().commit();
	}

	public void remove(Object object) {
		em.getTransaction().begin();
		em.remove(object);
		em.getTransaction().commit();
	}

}
