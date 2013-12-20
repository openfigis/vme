package org.vme.service.dao.impl;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.vme.service.dao.Dao;

public abstract class AbstractJPADao implements Dao {
	/* (non-Javadoc)
	 * @see org.vme.service.dao.Dao#getEntityById(javax.persistence.EntityManager, java.lang.Class, java.lang.Object)
	 */
	public <E> E getEntityById(EntityManager em, Class<E> entity, Object id) {
		Map<String, Object> idCriteria = new HashMap<String, Object>();
		idCriteria.put("id", id);
		
		try {
			return (E)this.generateFilteringTypedQuery(em, entity, idCriteria).getSingleResult();
		} catch(NoResultException NRe) {
			return null;
		}
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

		Field field;

		for(String key : criteria.keySet()) {
			try {
				field = entity.getDeclaredField(key);
			} catch (Throwable t) {
				throw new RuntimeException("Unexpected " + t.getClass().getSimpleName() + " caught: " + t.getMessage(), t);
			}
			
			if(field == null) {
				throw new IllegalArgumentException("Unknown field / parameter '" + key + "' for " + entity.getSimpleName());
			}
		}
		
		String qs = q.toString().replaceAll(" and $", "");
		
		TypedQuery<E> tq = this.generateTypedQuery(em, entity, qs); 

		if(setCriteria) {
			Object value;
			
			try {
				for(Entry<String, Object> parameter : criteria.entrySet()) {
					field = entity.getDeclaredField(parameter.getKey());
					
					value = parameter.getValue();
					
					if(value != null && value instanceof String && field.isAnnotationPresent(RSGConverter.class)) {
						value = field.getAnnotation(RSGConverter.class).value().newInstance().fromString((String)value);
					}
					
					tq.setParameter(parameter.getKey(), value);
				}
			} catch (Throwable t) {
				throw new RuntimeException("Unable to build query for " + entity.getSimpleName(), t);
			}
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
