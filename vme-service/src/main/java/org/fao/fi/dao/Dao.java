package org.fao.fi.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.fao.fi.vme.dao.config.VmeDB;

public abstract class Dao {

	@Inject
	@VmeDB
	protected EntityManager em;

	protected TypedQuery<?> generateTypedQuery(EntityManager em, Class<?> clazz) {
		String queryString = " select o from  " + clazz.getCanonicalName() + " o ";
		System.out.println(queryString);
		TypedQuery<?> query = em.createQuery(queryString, clazz);
		return query;
	}

	protected List<?> selectFrom(EntityManager em, Class<?> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

	public List<?> loadObjects(Class<?> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

}
