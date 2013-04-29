package org.fao.fi.vme.dao.msaccess;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TableWriter {

	@Inject
	EntityManager entityManager;

	public void write(ObjectCollection objectCollection) {
		for (Object object : objectCollection.getObjectList()) {
			entityManager.getTransaction().begin();
			entityManager.persist(object);
			entityManager.getTransaction().commit();
		}
	}

	public void merge(ObjectCollection objectCollection) {
		for (Object object : objectCollection.getObjectList()) {
			entityManager.getTransaction().begin();
			entityManager.merge(object);
			entityManager.getTransaction().commit();
		}
	}

}
