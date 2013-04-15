package org.fao.fi.vme.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.Vme;

@RequestScoped
public class VmeDao {

	@Inject
	private EntityManager em;

	public void persistVme(Vme vme) {
		em.persist(vme);
	}
}
