package org.vme.service.dao.sources.vme;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.service.dao.VmeDaoException;
import org.vme.service.dao.config.vme.VmeDB;
import org.vme.service.dao.impl.AbstractJPADao;

/**
 * * The dao in order to dconnect to the vme database. Connection details to be
 * found in /vme-configuration/src/main/resources/META_VME-INF/persistence.xml
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeDao extends AbstractJPADao {
	static final private Logger LOG = LoggerFactory.getLogger(VmeDao.class);

	@Inject
	@VmeDB
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public List<Vme> loadVmes() {
		return (List<Vme>) this.generateTypedQuery(em, Vme.class).getResultList();
	}

	public List<?> loadObjects(Class<?> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

	public <T> List<T> loadObjectsGeneric(Class<?> clazz) {
		@SuppressWarnings("unchecked")
		List<T> l = (List<T>) generateTypedQuery(em, clazz).getResultList();
		return l;
	}

	public Vme findVme(Long id) {
		return em.find(Vme.class, id);
	}

	public Object persist(Object object) {
		try {
			EntityTransaction et = em.getTransaction();
			et.begin();
			em.persist(object);
			em.flush();
			et.commit();
			LOG.debug("Object {} has been stored into persistence", object);
		} catch (Exception e) {
			LOG.error("Unable to store object {} into persistence: {} [ {} ]", object, e.getClass().getSimpleName(),
					e.getMessage());
			throw new VmeDaoException(e);
		}
		return object;
	}

	public void remove(Object object) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.remove(object);
			em.flush();
			et.commit();
			LOG.debug("Object {} has been removed from persistence", object);
		} catch (Throwable t) {
			LOG.error("Unable to remove object {} from persistence: {} [ {} ]", object, t.getClass().getSimpleName(),
					t.getMessage(), t);

			et.rollback();
		}
	}

	public void detach(Object object) {
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

	public Object merge(Object object) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.merge(object);
			et.commit();
			LOG.debug("Object {} has been merged into persistence", object);
			return object;
		} catch (Throwable t) {
			LOG.error("Unable to merge object {} into persistence: {} [ {} ]", object, t.getClass().getSimpleName(),
					t.getMessage());
			et.rollback();
			return null;
		}
	}

	public Vme saveVme(Vme vme) {
		EntityTransaction et = em.getTransaction();

		// try {
		et.begin();

		for (GeneralMeasure o : vme.getRfmo().getGeneralMeasureList()) {
			em.persist(o);
		}

		for (FisheryAreasHistory h : vme.getRfmo().getHasFisheryAreasHistory()) {
			em.persist(h);
		}

		for (VMEsHistory h : vme.getRfmo().getHasVmesHistory()) {
			em.persist(h);
		}

		for (InformationSource informationSource : vme.getRfmo().getInformationSourceList()) {
			em.persist(informationSource);
		}

		em.persist(vme.getRfmo());

		for (GeoRef geoRef : vme.getGeoRefList()) {
			em.persist(geoRef);
		}

		em.persist(vme);
		em.flush();

		et.commit();

		LOG.debug("Vme {} has been saved into persistence", vme);

		return vme;

	}

	public Long count(Class<?> clazz) {
		return count(em, clazz);
	}

	public void deleteGeoRef(GeoRef toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("GeoRef cannot be NULL");

		if (toDelete.getId() == null)
			throw new IllegalArgumentException("GeoRef ID cannot be NULL");

		if (toDelete.getVme() == null)
			throw new IllegalArgumentException("GeoRef cannot have a NULL Vme reference");

		if (toDelete.getVme().getId() == null)
			throw new IllegalArgumentException("GeoRef cannot have a Vme reference with a NULL id");

		// According to JPA plain specs, you can't automatically remove orphan
		// child elements
		// so you've to do it explicitly...
		Vme vme = this.getEntityById(this.em, Vme.class, toDelete.getVme().getId());

		GeoRef current = null;
		Iterator<GeoRef> iterator = vme.getGeoRefList() != null ? vme.getGeoRefList().iterator() : null;

		if (iterator != null) {
			while (iterator.hasNext()) {
				current = iterator.next();

				if (current.getId().equals(toDelete.getId()))
					iterator.remove();
			}

			if (current != null) {
				this.remove(current);

				this.merge(vme);
			}
		}
	}

	public void deleteProfile(Profile toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("Profile cannot be NULL");

		if (toDelete.getId() == null)
			throw new IllegalArgumentException("Profile ID cannot be NULL");

		if (toDelete.getVme() == null)
			throw new IllegalArgumentException("Profile cannot have a NULL Vme reference");

		if (toDelete.getVme().getId() == null)
			throw new IllegalArgumentException("Profile cannot have a Vme reference with a NULL id");

		// According to JPA plain specs, you can't automatically remove orphan
		// child elements
		// so you've to do it explicitly...
		Vme vme = this.getEntityById(this.em, Vme.class, toDelete.getVme().getId());

		Profile current = null;
		Iterator<Profile> iterator = vme.getProfileList() != null ? vme.getProfileList().iterator() : null;

		if (iterator != null) {
			while (iterator.hasNext()) {
				current = iterator.next();

				if (current.getId().equals(toDelete.getId()))
					iterator.remove();
			}

			if (current != null) {
				this.remove(current);

				this.merge(vme);
			}
		}
	}

	public void deleteSpecificMeasure(SpecificMeasure toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("SpecificMeasure cannot be NULL");

		if (toDelete.getId() == null)
			throw new IllegalArgumentException("SpecificMeasure ID cannot be NULL");

		if (toDelete.getVmeList() == null)
			throw new IllegalArgumentException("SpecificMeasure cannot have a NULL set of Vme references");

		if (toDelete.getVmeList().isEmpty())
			throw new IllegalArgumentException("SpecificMeasure cannot have an empty set of Vme references");

		for (Vme owner : toDelete.getVmeList())
			if (owner == null)
				throw new IllegalArgumentException("SpecificMeasure cannot have a NULL Vme reference");
			else if (owner.getId() == null)
				throw new IllegalArgumentException("SpecificMeasure cannot have a Vme reference with a NULL ID");

		// According to JPA plain specs, you can't automatically remove orphan
		// child elements
		// so you've to do it explicitly...
		for (Vme owner : toDelete.getVmeList()) {
			Vme vme = this.getEntityById(this.em, Vme.class, owner.getId());

			Profile current = null;
			Iterator<Profile> iterator = vme.getProfileList() != null ? vme.getProfileList().iterator() : null;

			if (iterator != null) {
				while (iterator.hasNext()) {
					current = iterator.next();

					if (current.getId().equals(toDelete.getId()))
						iterator.remove();
				}

				if (current != null) {
					this.remove(current);

					this.merge(vme);
				}
			}
		}
	}

}
