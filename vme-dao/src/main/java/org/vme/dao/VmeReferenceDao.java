package org.vme.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.vme.service.dao.config.vme.VmeDB;
import org.vme.service.dao.impl.AbstractJPADao;

/**
 * ReferenceData DAO in order to deal with VME reference data
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeReferenceDao extends AbstractJPADao {

	@Inject
	@VmeDB
	private EntityManager em;

	/**
	 * @return the 'em' value
	 */
	final public EntityManager getEm() {
		return this.em;
	}

	public void persist(Object object) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(object);
		et.commit();
	}

	public void syncStoreObject(Object object, Object primaryKey) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		if (em.find(object.getClass(), primaryKey) == null) {
			em.persist(object);
		} else {
			em.merge(object);
		}
		et.commit();
	}
}
