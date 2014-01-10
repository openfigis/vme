package org.fao.fi.vme.msaccess.component;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.vme.service.dao.config.vme.VmeDB;

public class VmeDao4Msaccess {

	// static final private Logger LOG =
	// LoggerFactory.getLogger(VmeDao4Msaccess.class);

	@Inject
	@VmeDB
	private EntityManager em;

	/**
	 * This is a specific method for loading the data from MsAccess. The whole
	 * object graph is prepared and then saved at once.
	 * 
	 * @param objectCollectionList
	 */
	public void persistObjectCollection(List<ObjectCollection> objectCollectionList) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		for (ObjectCollection objectCollection : objectCollectionList) {
			System.out.println(objectCollection.getClazz().getSimpleName());
			for (Object object : objectCollection.getObjectList()) {
				em.persist(object);
			}
		}
		et.commit();
	}

}
