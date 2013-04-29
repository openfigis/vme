package org.fao.fi.vme.dao.msaccess;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.Vme;

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

			if (object instanceof Vme) {
				Vme vme = (Vme) object;
				System.out.println("vme.getId()" + vme.getId());
				if (vme.getSpecificMeasuresList().size() == 0) {
					throw new VmeDaoException("Detected a vme.getSpecificMeasuresList().size()==0");
				}
				for (SpecificMeasures specificMeasures : vme.getSpecificMeasuresList()) {
					System.out.println("specificMeasures.getId()" + specificMeasures.getId());
				}
			}

			entityManager.getTransaction().begin();
			entityManager.merge(object);
			entityManager.getTransaction().commit();
		}
	}

}
