package org.vme.dao.sources.vme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import org.fao.fi.vme.domain.model.ObjectId;

public class DummyEm implements EntityManager {

	Object removedObject;

	Map<String, Object> store = new HashMap<String, Object>();
	long id = 1l;

	@Override
	public void persist(Object entity) {
		@SuppressWarnings("unchecked")
		ObjectId<Long> o = (ObjectId<Long>) entity;
		o.setId(id);
		String key = o.getClass().getSimpleName() + id++;
		store.put(key, entity);
	}

	public Object getRemovedObject() {
		return removedObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object merge(Object entity) {
		return entity;
	}

	@Override
	public void remove(Object entity) {
		@SuppressWarnings("unchecked")
		ObjectId<Long> o = (ObjectId<Long>) entity;
		this.removedObject = entity;
		store.remove(entity.getClass().getSimpleName() + o.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey) {

		String key = entityClass.getSimpleName() + primaryKey;
		return (T) store.get(key);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {

		return null;
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {

		return null;
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {

		return null;
	}

	@Override
	public <T> T getReference(Class<T> entityClass, Object primaryKey) {

		return null;
	}

	@Override
	public void flush() {

	}

	@Override
	public void setFlushMode(FlushModeType flushMode) {

	}

	@Override
	public FlushModeType getFlushMode() {

		return null;
	}

	@Override
	public void lock(Object entity, LockModeType lockMode) {

	}

	@Override
	public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {

	}

	@Override
	public void refresh(Object entity) {

	}

	@Override
	public void refresh(Object entity, Map<String, Object> properties) {

	}

	@Override
	public void refresh(Object entity, LockModeType lockMode) {

	}

	@Override
	public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {

	}

	@Override
	public void clear() {

	}

	@Override
	public void detach(Object entity) {

	}

	@Override
	public boolean contains(Object entity) {

		return false;
	}

	@Override
	public LockModeType getLockMode(Object entity) {

		return null;
	}

	@Override
	public void setProperty(String propertyName, Object value) {

	}

	@Override
	public Map<String, Object> getProperties() {

		return null;
	}

	@Override
	public Query createQuery(String qlString) {

		return null;
	}

	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {

		return null;
	}

	@Override
	public Query createQuery(CriteriaUpdate updateQuery) {

		return null;
	}

	@Override
	public Query createQuery(CriteriaDelete deleteQuery) {

		return null;
	}

	@Override
	public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {

		return null;
	}

	@Override
	public Query createNamedQuery(String name) {

		return null;
	}

	@Override
	public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {

		return null;
	}

	@Override
	public Query createNativeQuery(String sqlString) {

		return null;
	}

	@Override
	public Query createNativeQuery(String sqlString, Class resultClass) {

		return null;
	}

	@Override
	public Query createNativeQuery(String sqlString, String resultSetMapping) {

		return null;
	}

	@Override
	public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {

		return null;
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {

		return null;
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {

		return null;
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {

		return null;
	}

	@Override
	public void joinTransaction() {

	}

	@Override
	public boolean isJoinedToTransaction() {

		return false;
	}

	@Override
	public <T> T unwrap(Class<T> cls) {

		return null;
	}

	@Override
	public Object getDelegate() {

		return null;
	}

	@Override
	public void close() {

	}

	@Override
	public boolean isOpen() {

		return false;
	}

	@Override
	public EntityTransaction getTransaction() {

		return null;
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {

		return null;
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {

		return null;
	}

	@Override
	public Metamodel getMetamodel() {

		return null;
	}

	@Override
	public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {

		return null;
	}

	@Override
	public EntityGraph<?> createEntityGraph(String graphName) {

		return null;
	}

	@Override
	public EntityGraph<?> getEntityGraph(String graphName) {

		return null;
	}

	@Override
	public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
		return null;
	}

}
