package org.fao.fi.vme.msaccess.component;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.msaccess.model.ObjectCollection;

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
