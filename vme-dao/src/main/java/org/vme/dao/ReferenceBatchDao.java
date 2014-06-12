package org.vme.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.ReferenceDataObject;
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

	public void syncStoreObject(ReferenceDataObject<?> referenceDataObject) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		if (em.find(referenceDataObject.getClass(), referenceDataObject.getId()) == null) {
			em.persist(referenceDataObject);
		} else {
			em.merge(referenceDataObject);
		}
		et.commit();
	}
}
