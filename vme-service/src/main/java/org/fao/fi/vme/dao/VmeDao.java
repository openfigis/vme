package org.fao.fi.vme.dao;

import java.util.List;

import org.fao.fi.dao.Dao;
import org.fao.fi.vme.domain.Vme;

public class VmeDao extends Dao {

	@SuppressWarnings("unchecked")
	public List<Vme> loadVmes() {
		return (List<Vme>) this.generateTypedQuery(em, Vme.class).getResultList();
	}

	public Vme findVme(int id) {
		return em.find(Vme.class, id);
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

	public void merge(Object object) {
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();

	}

}
