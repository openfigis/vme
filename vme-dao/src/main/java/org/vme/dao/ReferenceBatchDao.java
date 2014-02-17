package org.vme.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.vme.dao.config.vme.VmeDB;
import org.vme.dao.impl.AbstractJPADao;

/**
 * ReferenceData DAO in order to deal with VME reference data
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class ReferenceBatchDao extends AbstractJPADao {

	@Inject
	@VmeDB
	private EntityManager em;

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