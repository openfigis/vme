package org.fao.fi.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public abstract class Dao {

	protected TypedQuery<?> generateTypedQuery(EntityManager em, Class<?> clazz) {
		String queryString = " select o from  " + clazz.getCanonicalName() + " o ";
		System.out.println(queryString);
		TypedQuery<?> query = em.createQuery(queryString, clazz);
		return query;
	}

	protected List<?> selectFrom(EntityManager em, Class<?> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

	protected List<?> loadObjects(EntityManager em, Class<?> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

}
