package org.vme.service.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public abstract class Dao {

	protected TypedQuery<?> generateTypedQuery(EntityManager em, Class<?> clazz) {
		String queryString = " select o from  " + clazz.getCanonicalName() + " o ";
		return generateTypedQuery(em, clazz, queryString);
	}

	protected List<?> selectFrom(EntityManager em, Class<?> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

	protected List<?> loadObjects(EntityManager em, Class<?> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

	protected TypedQuery<?> generateTypedQuery(EntityManager em, Class<?> clazz, String queryString) {
		TypedQuery<?> query = em.createQuery(queryString, clazz);
		return query;
	}

	protected Long count(EntityManager em, Class<?> clazz) {
		String queryString = " select count(o) from  " + clazz.getCanonicalName() + " o ";
		Query query = em.createQuery(queryString);
		return (Long) query.getSingleResult();
	}
}
