package org.vme.service.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.vme.service.dao.Dao;

public abstract class AbstractJPADao implements Dao {
	/* (non-Javadoc)
	 * @see org.vme.service.dao.Dao#getEntityById(javax.persistence.EntityManager, java.lang.Class, java.lang.Object)
	 */
	public <E> E getEntityById(EntityManager em, Class<E> entity, Object id) {
		Map<String, Object> idCriteria = new HashMap<String, Object>();
		idCriteria.put("id", id);
		
		return (E)this.generateFilteringTypedQuery(em, entity, idCriteria).getSingleResult();
	}
	
	/* (non-Javadoc)
	 * @see org.vme.service.dao.Dao#filterEntities(javax.persistence.EntityManager, java.lang.Class, java.util.Map<java.lang.String,java.lang.Object>[])
	 */
	@Override
	public <E> Collection<E> filterEntities(EntityManager em, Class<E> entity, Map<String, Object> criteria) {
		return (Collection<E>)this.generateFilteringTypedQuery(em, entity, criteria).getResultList();
	}
	
	protected <E> TypedQuery<E> generateFilteringTypedQuery(EntityManager em, Class<E> entity, Map<String, Object> criteria) {
		StringBuilder q = new StringBuilder("select E from ");
		q.append(entity.getName()).append(" as E ");

		final boolean setCriteria = criteria != null && !criteria.isEmpty();
		
		if(setCriteria) {
			q.append("where ");
			
			for(String key : criteria.keySet()) {
				q.append("E.").append(key).append(" = :").append(key).append(" and ");
			}
		}
		
		String qs = q.toString().replaceAll(" and $", "");
		
		TypedQuery<E> tq = this.generateTypedQuery(em, entity, qs); 

		if(setCriteria) {
			for(Entry<String, Object> parameter : criteria.entrySet())
				tq.setParameter(parameter.getKey(), parameter.getValue());
		}	
		
		return tq;
	}

	protected <E> TypedQuery<E> generateTypedQuery(EntityManager em, Class<E> clazz) {
		return generateTypedQuery(em, clazz, "select o from  " + clazz.getCanonicalName() + " o ");
	}

	protected <E> List<E> selectFrom(EntityManager em, Class<E> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

	protected <E> List<E> loadObjects(EntityManager em, Class<E> clazz) {
		//Same as selectFrom?
		return this.generateTypedQuery(em, clazz).getResultList();
	}

	protected <E> TypedQuery<E> generateTypedQuery(EntityManager em, Class<E> clazz, String queryString) {
		return em.createQuery(queryString, clazz);
	}

	protected Long count(EntityManager em, Class<?> clazz) {
		String queryString = " select count(o) from  " + clazz.getCanonicalName() + " o ";
		Query query = em.createQuery(queryString);
		return (Long) query.getSingleResult();
	}
}
