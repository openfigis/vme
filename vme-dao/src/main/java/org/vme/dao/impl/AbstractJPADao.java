package org.vme.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.fao.fi.vme.domain.model.ObjectId;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.Dao;
import org.vme.dao.VmeDaoException;
import org.vme.dao.sources.vme.VmeDao;

/**
 * 
 * @author Fabrizio Sibeni
 * 
 */
public abstract class AbstractJPADao implements Dao {
	static final protected Logger LOG = LoggerFactory.getLogger(VmeDao.class);

	public <E> E getEntityById(EntityManager em, Class<E> entity, Object id) {
		Map<String, Object> idCriteria = new HashMap<String, Object>();
		idCriteria.put("id", id);
		// try {
		// return (E) this.generateFilteringTypedQuery(em, entity,
		// idCriteria).getSingleResult();
		// } catch (NoResultException NRe) {
		// return null;
		// }
		// Sonar laments about the above (Exception handlers should provide some
		// context and preserve the original exception ), therefore:
		E result = null;
		if (this.generateFilteringTypedQuery(em, entity, idCriteria).getResultList().size() == 1) {
			result = this.generateFilteringTypedQuery(em, entity, idCriteria).getSingleResult();
		}
		return result;

	}

	@Override
	public <E> Collection<E> filterEntities(EntityManager em, Class<E> entity, Map<String, Object> criteria) {
		return (Collection<E>) this.generateFilteringTypedQuery(em, entity, criteria).getResultList();
	}

	protected <E> TypedQuery<E> generateFilteringTypedQuery(EntityManager em, Class<E> entity,
			Map<String, Object> criteria) {
		StringBuilder q = new StringBuilder("select E from ");
		q.append(entity.getName()).append(" as E ");

		final boolean setCriteria = criteria != null && !criteria.isEmpty();

		if (setCriteria) {
			q.append("where ");

			for (String key : criteria.keySet()) {
				q.append("E.").append(key).append(" = :").append(key).append(" and ");
			}
		}

		Field field;

		for (String key : criteria.keySet()) {
			try {
				field = entity.getDeclaredField(key);
			} catch (Throwable t) {
				throw new RuntimeException("Unexpected " + t.getClass().getSimpleName() + " caught: " + t.getMessage(),
						t);
			}

			if (field == null) {
				throw new IllegalArgumentException("Unknown field / parameter '" + key + "' for "
						+ entity.getSimpleName());
			}
		}

		String qs = q.toString().replaceAll(" and $", "");

		TypedQuery<E> tq = this.generateTypedQuery(em, entity, qs);

		if (setCriteria) {
			Object value;

			try {
				for (Entry<String, Object> parameter : criteria.entrySet()) {
					field = entity.getDeclaredField(parameter.getKey());

					value = parameter.getValue();

					if (value != null && value instanceof String && field.isAnnotationPresent(RSGConverter.class)) {
						value = field.getAnnotation(RSGConverter.class).value().newInstance()
								.fromString((String) value);
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

	public <E> List<E> selectFrom(EntityManager em, Class<E> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

	public <E> List<E> loadObjects(EntityManager em, Class<E> clazz) {
		return this.selectFrom(em, clazz);
	}

	protected <E> TypedQuery<E> generateTypedQuery(EntityManager em, Class<E> clazz, String queryString) {
		return em.createQuery(queryString, clazz);
	}

	protected Long count(EntityManager em, Class<?> clazz) {
		return (Long) em.createQuery(" select count(o) from  " + clazz.getCanonicalName() + " o ").getSingleResult();
	}

	public void detach(EntityManager em, Object object) {
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			em.detach(object);
			em.flush();
			et.commit();

			LOG.debug("Object {} has been removed from persistence", object);
		} catch (Throwable t) {
			LOG.error("Unable to remove object {} from persistence: {} [ {} ]", object, t.getClass().getSimpleName(),
					t.getMessage(), t);

			et.rollback();
		}
	}

	protected <E> E doMerge(EntityManager em, E object) {
		return em.merge(object);
	}

	protected <E> E doPersist(EntityManager em, E object) {
		em.persist(object);

		return object;
	}

	protected <E extends ObjectId<? extends Serializable>> E doPersistAndFlush(EntityManager em, E object) {
		em.persist(object);
		em.flush();

		return object;
	}

	protected void doRemove(EntityManager em, Object object) {
		em.remove(object);
	}

	protected void zdoRefresh(EntityManager em, Object object) {
		// em.refresh(object);
		em.flush();
	}

	public <E> E merge(EntityManager em, E object) {
		EntityTransaction et = em.getTransaction();

		E merged;

		try {
			et.begin();
			merged = this.doMerge(em, object);
			et.commit();

			LOG.debug("Object {} has been merged into persistence", object);

			return merged;
		} catch (Throwable t) {
			LOG.error("Unable to merge object {} into persistence: {} [ {} ]", object, t.getClass().getSimpleName(),
					t.getMessage());

			et.rollback();

			return null;
		}
	}

	public <E> E persist(EntityManager em, E object) {
		try {
			EntityTransaction et = em.getTransaction();
			et.begin();
			em.persist(object);
			em.flush();
			et.commit();
			LOG.debug("Object {} has been stored into persistence", object);
		} catch (Exception e) {
			LOG.error("Unable to store object {} into persistence: {} [ {} ]", object, e.getClass().getSimpleName(), e.getMessage());
			throw new VmeDaoException(e);
		}

		return object;
	}
	
	public <E> E persistNoTx(EntityManager em, E object) {
		em.persist(object);
		em.flush();
		
		return object;
	}

	public <E> List<E> persist(EntityManager em, List<E> objectList) {
		try {
			EntityTransaction et = em.getTransaction();
			et.begin();

			for (E object : objectList) {
				em.persist(object);
			}

			et.commit();

			LOG.debug("Object list {} has been stored into persistence", objectList);

			return objectList;
		} catch (Exception e) {
			LOG.error("Unable to store object list {} into persistence: {} [ {} ]", objectList, e.getClass()
					.getSimpleName(), e.getMessage());

			throw new VmeDaoException(e);
		}
	}

	public void remove(EntityManager em, Object object) {
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			this.doRemove(em, object);
			em.flush();
			et.commit();
			LOG.debug("Object {} has been removed from persistence", object);
		} catch (Throwable t) {
			LOG.error("Unable to remove object {} from persistence: {} [ {} ]", object, t.getClass().getSimpleName(),
					t.getMessage(), t);
			et.rollback();
			throw new VmeDaoException(t);
		}
	}
}