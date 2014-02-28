package org.fao.fi.vme.msaccess.component;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.vme.dao.VmeDaoException;
import org.vme.dao.config.vme.VmeDB;

public class VmeDao4Msaccess {

	// static final private Logger LOG =
	// LoggerFactory.getLogger(VmeDao4Msaccess.class);

	@Inject
	@VmeDB
	private EntityManager em;

	public void persistObject(Object object) {
		EntityTransaction et = em.getTransaction();
		
		try {
			et.begin();
			em.persist(object);
			et.commit();
		} catch(Throwable t) {
			et.rollback();
			
			throw new RuntimeException(t);
		}
	}
	
	/**
	 * This is a specific method for loading the data from MsAccess. The whole
	 * object graph is prepared and then saved at once.
	 * 
	 * @param objectCollectionList
	 */
	public void persistObjectCollection(List<ObjectCollection> objectCollectionList) {
		try {

			EntityTransaction et = em.getTransaction();
			et.begin();
			for (ObjectCollection objectCollection : objectCollectionList) {

				for (Object object : objectCollection.getObjectList()) {
					if (em.contains(object)) {
						// it can happen that the object already was persisted
						// because it is a shared object, like
						// InformationSource.
						em.merge(object);
					} else {
						em.persist(object);
					}

					if (object instanceof ObjectId) {
						@SuppressWarnings("unchecked")
						ObjectId<Serializable> objectId = (ObjectId<Serializable>) object;

						Serializable id = objectId.getId();

						if (id == null)
							throw new VmeDaoException("object id cannot be NULL");

						if (Number.class.isAssignableFrom(id.getClass()))
							if (((Number) id).intValue() == 0) {
								throw new VmeDaoException("Numeric object id cannot be 0");
							} else if (String.class.isAssignableFrom(id.getClass()))
								if ("".equals(((String) id).trim()))
									throw new VmeDaoException("String object id cannot be blank");
					}

				}
			}
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new VmeDaoException(e);
		}
	}
}
