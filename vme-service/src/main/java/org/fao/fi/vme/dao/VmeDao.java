package org.fao.fi.vme.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.Vme;

@RequestScoped
public class VmeDao {

	@Inject
	private EntityManager em;

	public void persistVme(Vme vme) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(vme);
		t.commit();
		em.close();
	}
}
