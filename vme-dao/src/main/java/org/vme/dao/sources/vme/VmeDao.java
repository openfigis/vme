package org.vme.dao.sources.vme;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.MediaReference;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.VMEsHistory;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.vme.dao.VmeDaoException;
import org.vme.dao.config.vme.VmeDB;
import org.vme.dao.impl.AbstractJPADao;

/**
 * * The dao in order to dconnect to the vme database. Connection details to be
 * found in /vme-configuration/src/main/resources/META_VME-INF/persistence.xml
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeDao extends AbstractJPADao {

	@Inject
	@VmeDB
	private EntityManager em;

	private MultiLingualStringUtil u = new MultiLingualStringUtil();

	/**
	 * @return the 'em' value
	 */
	public final EntityManager getEm() {
		return this.em;
	}

	public List<Vme> loadVmes() {
		return (List<Vme>) this.generateTypedQuery(em, Vme.class).getResultList();
	}

	public Vme findVme(Long id) {
		return em.find(Vme.class, id);
	}

	public EntityTransaction begin() {
		EntityTransaction et = this.em.getTransaction();
		et.begin();
		return et;
	}

	public void commit(EntityTransaction tx) {
		tx.commit();
		this.em.clear();
	}

	public void rollback(EntityTransaction tx) {
		tx.rollback();
		this.em.clear();
	}

	public Vme saveVme(Vme vme) {
		EntityTransaction et = em.getTransaction();
		et.begin();

		if (vme.getRfmo() != null && vme.getRfmo().getGeneralMeasureList() != null) {
			for (GeneralMeasure g : vme.getRfmo().getGeneralMeasureList()) {
				em.persist(g);
			}

			for (FisheryAreasHistory h : vme.getRfmo().getHasFisheryAreasHistory()) {
				em.persist(h);
			}

			for (VMEsHistory h : vme.getRfmo().getHasVmesHistory()) {
				em.persist(h);
			}

			for (InformationSource informationSource : vme.getRfmo().getInformationSourceList()) {
				if (!em.contains(informationSource.getSourceType())) {
					em.persist(informationSource.getSourceType());
				}
				if (!em.contains(informationSource)) {
					em.persist(informationSource);
				}

			}
			em.persist(vme.getRfmo());
		}

		if (vme.getGeoRefList() != null) {
			for (GeoRef geoRef : vme.getGeoRefList()) {
				LOG.info(String.valueOf(em.contains(geoRef)));
				em.persist(geoRef);
			}
		}

		if (vme.getProfileList() != null) {
			for (Profile profile : vme.getProfileList()) {
				em.persist(profile);
			}
		}

		if (vme.getSpecificMeasureList() != null) {
			for (SpecificMeasure specificMeasure : vme.getSpecificMeasureList()) {
				em.persist(specificMeasure);
			}
		}

		em.persist(vme);
		// em.flush(); Experiment to solve issue
		// "Flush during cascade is dangerous"

		et.commit();

		LOG.debug("Vme {} has been saved into persistence", vme);

		return vme;
	}

	public <E> List<E> loadObjects(Class<E> clazz) {
		return super.loadObjects(em, clazz);
	}

	public <E> E merge(E object) {
		LOG.info("EM" + em);
		return super.merge(em, object);
	}

	public <E> E persist(E object) {
		return super.persist(em, object);
	}

	public <E> E persistNoTx(E object) {
		return super.persistNoTx(em, object);
	}

	public <E> List<E> persist(List<E> object) {
		return super.persist(em, object);
	}

	public void remove(Object object) {
		super.remove(em, object);
	}

	public Long count(Class<?> clazz) {
		return super.count(em, clazz);
	}

	public void delete(Object toDelete) {

		if (toDelete == null) {
			throw new IllegalArgumentException("Cannot delete an NULL or empty data");
		}

		Class<?> entity = toDelete.getClass();

		// I know this sucks...
		if (!entity.equals(Object.class)) {
			Method deleteMethod = null;
			try {
				deleteMethod = this.getClass().getDeclaredMethod("delete", entity);
			} catch (Exception t) {
				String message = "Unable to find a proper 'delete' method for " + toDelete.getClass().getSimpleName()
						+ ": " + t.getMessage();
				LOG.error(message, t);
				throw new IllegalArgumentException(message, t);
			}

			if (deleteMethod != null) {
				try {
					deleteMethod.invoke(this, toDelete);
				} catch (Exception t) {
					String message = "Unable to invoke the 'delete' method for " + toDelete.getClass().getSimpleName()
							+ ": " + t.getMessage();
					LOG.error(message, t);
					throw new VmeDaoException(message, t);
				}
			}
		}
	}

	public void delete(Vme toDelete) {
		if (toDelete == null) {
			throw new IllegalArgumentException("The Vme to delete cannot be NULL");
		}

		if (toDelete.getId() == null) {
			throw new IllegalArgumentException("The Vme to delete cannot have a NULL identifier");
		}

		if (toDelete.getRfmo() == null) {
			throw new IllegalArgumentException("The Vme to delete cannot have a NULL parent authority");
		}

		if (toDelete.getProfileList() != null) {
			for (Profile profile : new ArrayList<Profile>(toDelete.getProfileList())) {
				this.delete(profile);
			}
		}

		if (toDelete.getGeoRefList() != null) {
			for (GeoRef geoRef : new ArrayList<GeoRef>(toDelete.getGeoRefList())) {
				this.delete(geoRef);
			}
		}
		
		if (toDelete.getMediaReferenceList() != null) {
			for (MediaReference media : new ArrayList<MediaReference>(toDelete.getMediaReferenceList())) {
				this.delete(media);
			}
		}

		if (toDelete.getSpecificMeasureList() != null) {
			for (SpecificMeasure specificMeasure : new ArrayList<SpecificMeasure>(toDelete.getSpecificMeasureList())) {
				this.delete(specificMeasure);
			}
		}

		em.remove(toDelete);
	}

	public void delete(InformationSource toDelete) {
		if (toDelete == null) {
			throw new IllegalArgumentException("The Information Source to delete cannot be NULL");
		}

		if (toDelete.getId() == null) {
			throw new IllegalArgumentException("The Information Source to delete cannot have a NULL identifier");
		}

		if (toDelete.getRfmo() == null) {
			throw new IllegalArgumentException("The Information Source to delete cannot have a NULL parent authority");
		}

		Rfmo parent = toDelete.getRfmo();

		if (parent.getInformationSourceList() != null) {
			Iterator<InformationSource> rfmoIterator = parent.getInformationSourceList().iterator();

			while (rfmoIterator.hasNext()) {
				if (rfmoIterator.next().getId().equals(toDelete.getId())) {
					rfmoIterator.remove();
				}
			}
			toDelete.setRfmo(null);

			this.doMerge(em, parent);
		} else {
			LOG.warn(
					"Got a request to delete Information Source #{} belonging to {} but the parent Rfmo domain object seems not to have any Information Source listed...",
					toDelete.getId(), parent == null ? "NULL" : parent.getId());
		}

		Iterator<InformationSource> gmIterator;
		if (toDelete.getGeneralMeasureList() != null) {
			for (GeneralMeasure generalMeasure : toDelete.getGeneralMeasureList()) {
				gmIterator = generalMeasure.getInformationSourceList().iterator();

				while (gmIterator.hasNext()) {
					if (gmIterator.next().getId().equals(toDelete.getId())) {
						gmIterator.remove();
					}
				}

				this.doMerge(em, generalMeasure);
			}
		}

		Iterator<SpecificMeasure> smIterator;
		SpecificMeasure smCurrent;
		if (toDelete.getSpecificMeasureList() != null) {
			smIterator = toDelete.getSpecificMeasureList().iterator();

			while (smIterator.hasNext()) {
				smCurrent = smIterator.next();

				if (smCurrent != null && smCurrent.getInformationSource().getId().equals(toDelete.getId())) {
					smCurrent.setInformationSource(null);
				}

				this.doMerge(em, smCurrent);

				smIterator.remove();
			}
		}

		this.doRemove(em, toDelete);
	}

	public void delete(VMEsHistory toDelete) {

		if (toDelete == null) {
			throw new IllegalArgumentException("VMEsHistory cannot be NULL");
		}

		if (toDelete.getId() == null) {
			throw new IllegalArgumentException("VMEsHistory id cannot be NULL");
		}

		if (toDelete.getRfmo() == null) {
			throw new IllegalArgumentException("VMEsHistory cannot have a NULL parent RFMO");
		}

		Rfmo parent = toDelete.getRfmo();

		Iterator<VMEsHistory> rfmoIterator = parent.getHasVmesHistory().iterator();

		while (rfmoIterator.hasNext()) {
			if (rfmoIterator.next().getId().equals(toDelete.getId())) {
				rfmoIterator.remove();
			}
		}

		this.doMerge(em, parent);

		this.doRemove(em, toDelete);
	}

	public void delete(FisheryAreasHistory toDelete) {

		if (toDelete == null) {
			throw new IllegalArgumentException("FisheryAreasHistory cannot be NULL");
		}

		if (toDelete.getId() == null) {
			throw new IllegalArgumentException("FisheryAreasHistory id cannot be NULL");
		}

		if (toDelete.getRfmo() == null) {
			throw new IllegalArgumentException("FisheryAreasHistory cannot have a NULL parent RFMO");
		}

		Rfmo parent = toDelete.getRfmo();

		Iterator<FisheryAreasHistory> rfmoIterator = parent.getHasFisheryAreasHistory().iterator();

		while (rfmoIterator.hasNext()) {
			if (rfmoIterator.next().getId().equals(toDelete.getId())) {
				rfmoIterator.remove();
			}
		}

		toDelete.setRfmo(null);

		this.doMerge(em, parent);

		this.doRemove(em, toDelete);
	}

	public void delete(Profile toDelete) {

		if (toDelete == null) {
			throw new IllegalArgumentException("Profile cannot be NULL");
		}

		if (toDelete.getId() == null) {
			throw new IllegalArgumentException("Profile id cannot be NULL");
		}

		if (toDelete.getVme() == null) {
			throw new IllegalArgumentException("Profile cannot have a NULL Vme reference");
		}

		if (toDelete.getVme().getId() == null) {
			throw new IllegalArgumentException("Profile cannot have a Vme reference with a NULL identifier");
		}

		Vme parent = toDelete.getVme();

		if (parent.getProfileList() == null) {
			throw new IllegalArgumentException("Profile cannot have a parent Vme with a NULL profile list");
		}

		if (parent.getProfileList().isEmpty()) {
			throw new IllegalArgumentException("Profile cannot have a parent Vme with an empty profile list");
		}

		Iterator<Profile> iterator = parent.getProfileList().iterator();

		while (iterator.hasNext()) {
			if (iterator.next().getId().equals(toDelete.getId())) {
				iterator.remove();
			}
		}

		this.doMerge(em, parent);

		this.doRemove(em, toDelete);
	}

	public void delete(GeoRef toDelete) {

		if (toDelete == null) {
			throw new IllegalArgumentException("GeoRef cannot be NULL");
		}

		if (toDelete.getId() == null) {
			throw new IllegalArgumentException("GeoRef id cannot be NULL");
		}

		if (toDelete.getVme() == null) {
			throw new IllegalArgumentException("GeoRef cannot have a NULL Vme reference");
		}

		if (toDelete.getVme().getId() == null) {
			throw new IllegalArgumentException("GeoRef cannot have a Vme reference with a NULL identifier");
		}

		Vme parent = toDelete.getVme();

		if (parent.getGeoRefList() == null) {
			throw new IllegalArgumentException("GeoRef cannot have a parent Vme with a NULL profile list");
		}

		if (parent.getGeoRefList().isEmpty()) {
			throw new IllegalArgumentException("GeoRef cannot have a parent Vme with an empty profile list");
		}

		Iterator<GeoRef> iterator = parent.getGeoRefList().iterator();

		while (iterator.hasNext()) {
			if (iterator.next().getId().equals(toDelete.getId())) {
				iterator.remove();
			}
		}

		this.doMerge(em, parent);

		this.doRemove(em, toDelete);
	}

	public void delete(GeneralMeasure toDelete) {

		if (toDelete == null) {
			throw new IllegalArgumentException("GeneralMeasure cannot be NULL");
		}

		if (toDelete.getId() == null) {
			throw new IllegalArgumentException("GeneralMeasure id cannot be NULL");
		}

		if (toDelete.getRfmo() == null) {
			throw new IllegalArgumentException("GeneralMeasure cannot have a NULL parent RFMO");
		}

		Rfmo parent = toDelete.getRfmo();

		if (parent.getGeneralMeasureList() != null) {
			Iterator<GeneralMeasure> rfmoIterator = parent.getGeneralMeasureList().iterator();

			while (rfmoIterator.hasNext()) {
				if (rfmoIterator.next().getId().equals(toDelete.getId())) {
					rfmoIterator.remove();
				}
			}

			this.doMerge(em, parent);
		} else {
			LOG.warn(
					"Got a request to delete General Measure #{} belonging to {} but the parent Rfmo domain object seems not to have any General Measure listed...",
					toDelete.getId(), parent == null ? "NULL" : parent.getId());
		}

		if (toDelete.getInformationSourceList() != null) {
			for (InformationSource informationSource : toDelete.getInformationSourceList()) {
				Iterator<GeneralMeasure> informationSourceIterator = informationSource.getGeneralMeasureList()
						.iterator();

				while (informationSourceIterator.hasNext()) {
					if (informationSourceIterator.next().getId().equals(toDelete.getId())) {
						informationSourceIterator.remove();
					}
				}

				this.doMerge(em, informationSource);
			}
		}

		this.doRemove(em, toDelete);
	}

	public void delete(SpecificMeasure toDelete) {

		if (toDelete == null) {
			throw new IllegalArgumentException("SpecificMeasure cannot be NULL");
		}

		if (toDelete.getId() == null) {
			throw new IllegalArgumentException("SpecificMeasure id cannot be NULL");
		}

		if (toDelete.getVme() == null) {
			throw new IllegalArgumentException("SpecificMeasure cannot have a NULL Vme reference");
		}

		if (toDelete.getVme().getId() == null) {
			throw new IllegalArgumentException("SpecificMeasure cannot have an Vme reference with a NULL identifier");
		}

		Vme vme = toDelete.getVme();

		if (vme.getSpecificMeasureList() != null) {
			Iterator<SpecificMeasure> vmeIterator = vme.getSpecificMeasureList().iterator();

			while (vmeIterator.hasNext()) {
				if (vmeIterator.next().getId().equals(toDelete.getId())) {
					vmeIterator.remove();
				}
			}

			this.doMerge(em, vme);
		}

		if (toDelete.getInformationSource() != null && toDelete.getInformationSource().getSpecificMeasureList() != null) {
			Iterator<SpecificMeasure> informationSourceIterator = toDelete.getInformationSource()
					.getSpecificMeasureList().iterator();

			while (informationSourceIterator.hasNext()) {
				if (informationSourceIterator.next().getId().equals(toDelete.getId())) {
					informationSourceIterator.remove();
				}
			}

			this.doMerge(em, toDelete.getInformationSource());
		}

		this.doRemove(em, toDelete);
	}

	public Vme update(Vme updatedVME) throws Throwable {

		if (updatedVME == null) {
			throw new IllegalArgumentException("Updated Vme cannot be NULL");
		}

		if (updatedVME.getId() == null) {
			throw new IllegalArgumentException("Updated Vme cannot have a NULL identifier");
		}

		if (updatedVME.getRfmo() == null) {
			throw new IllegalArgumentException("Updated Vme cannot have a NULL authority");
		}

		if (updatedVME.getRfmo().getId() == null) {
			throw new IllegalArgumentException("Updated Vme cannot have a Rfmo with a NULL identifier");
		}

		// Get the current Vme status (before the update)
		Vme currentVME = this.getEntityById(this.em, Vme.class, updatedVME.getId());

		if (currentVME == null) {
			throw new IllegalArgumentException("Unable to update Vme with id #" + updatedVME.getId()
					+ " as it doesn't exist");
		}

		// Build the list of IDs for GeoRef / Profile / SpecificMeasure for the
		// Vme in its current status.
		Set<Long> geoRefsToDelete = new HashSet<Long>();
		if (currentVME.getGeoRefList() != null) {
			for (GeoRef geoRef : currentVME.getGeoRefList()) {
				geoRefsToDelete.add(geoRef.getId());
			}
		}

		Set<Long> profileToDelete = new HashSet<Long>();
		if (currentVME.getProfileList() != null) {
			for (Profile profile : currentVME.getProfileList()) {
				profileToDelete.add(profile.getId());
			}
		}

		Set<Long> specificMeasuresToDelete = new HashSet<Long>();
		if (currentVME.getSpecificMeasureList() != null) {
			for (SpecificMeasure specificMeasure : currentVME.getSpecificMeasureList()) {
				specificMeasuresToDelete.add(specificMeasure.getId());
			}
		}
		
		Set<Long> mediaToDelete = new HashSet<Long>();
		if (currentVME.getMediaReferenceList() != null) {
			for (MediaReference media : currentVME.getMediaReferenceList()) {
				mediaToDelete.add(media.getId());
			}
		}

		// Check which geoRef / profile / specificMeasure must be deleted...

		if (updatedVME.getGeoRefList() != null) {
			for (GeoRef geoRef : updatedVME.getGeoRefList()) {
				geoRefsToDelete.remove(geoRef.getId());
			}
		}

		if (updatedVME.getProfileList() != null) {
			for (Profile profile : updatedVME.getProfileList()) {
				profileToDelete.remove(profile.getId());
			}
		}

		if (updatedVME.getSpecificMeasureList() != null) {
			for (SpecificMeasure specificMeasure : updatedVME.getSpecificMeasureList()) {
				specificMeasuresToDelete.remove(specificMeasure.getId());
			}
		}
		
		if (updatedVME.getMediaReferenceList() != null) {
			for (MediaReference media : updatedVME.getMediaReferenceList()) {
				mediaToDelete.remove(media.getId());
			}
		}

		// If any GeoRef / Profile / Specific Measure is missing, it must be
		// deleted in order not to leave 'orphan' children.
		for (Long id : geoRefsToDelete) {
			GeoRef g = this.getEntityById(this.em, GeoRef.class, id);

			g.setVme(null);

			this.doRemove(em, g);
		}
		
		for (Long id : profileToDelete) {
			Profile p = this.getEntityById(this.em, Profile.class, id);

			p.setVme(null);

			this.doRemove(em, p);
		}
		
		for (Long id : mediaToDelete) {
			MediaReference m = this.getEntityById(this.em, MediaReference.class, id);

			m.setVme(null);

			this.doRemove(em, m);
		}

		// Unlinks InformationSources from the list of Specific Measures to
		// remove, then unlinks the Specific Measure from the VME
		for (Long id : specificMeasuresToDelete) {
			SpecificMeasure s = this.getEntityById(this.em, SpecificMeasure.class, id);

			InformationSource linked = s.getInformationSource();

			if (linked != null) {
				s.setInformationSource(null);

				Iterator<SpecificMeasure> parents = linked.getSpecificMeasureList().iterator();
				SpecificMeasure current = null;

				while (parents.hasNext()) {
					current = parents.next();

					if (current.getId().equals(s.getId())) {
						parents.remove();

						this.doMerge(em, linked);
					}
				}

				this.doMerge(em, s);
			}

			s.setVme(null);

			this.doRemove(em, s);
		}

		// Link the Current GeoRefs to the Vme
		if (updatedVME.getGeoRefList() != null) {
			for (GeoRef geoRef : updatedVME.getGeoRefList()) {
				geoRef.setVme(updatedVME);
			}
		}

		// Link the Profiles to the Vme
		if (updatedVME.getProfileList() != null) {
			for (Profile profile : updatedVME.getProfileList()) {
				profile.setVme(updatedVME);
			}
		}
		
		// Link the Media to the Vme
		if (updatedVME.getMediaReferenceList() != null) {
			for (MediaReference media : updatedVME.getMediaReferenceList()) {
				media.setVme(updatedVME);
			}
		}

		// Link the SpecificMeasures to the Vme
		List<SpecificMeasure> updatedSMs = new ArrayList<SpecificMeasure>();

		if (updatedVME.getSpecificMeasureList() != null) {
			for (SpecificMeasure specificMeasure : updatedVME.getSpecificMeasureList()) {
				specificMeasure.setVme(updatedVME);

				if (specificMeasure.getId() != null) {
					updatedSMs.add(this.update(specificMeasure));
				} else {
					updatedSMs.add(this.create(specificMeasure));
				}
			}
		}

		updatedVME.setSpecificMeasureList(updatedSMs);

		// Update the Vme: this will unlink GeoRefs / Profiles /
		// SpecificMeasures but not remove them.
		Vme mergedVME = this.doMerge(em, updatedVME);
		em.flush();

		return mergedVME;
	}

	public Vme create(Vme vme) throws Throwable {

		if (vme == null) {
			throw new IllegalArgumentException("The updated Vme cannot be NULL");
		}

		// Link the information source (by ID) to the specific measure
		InformationSource current;
		if (vme.getSpecificMeasureList() != null) {
			for (SpecificMeasure specificMeasure : vme.getSpecificMeasureList()) {
				if (specificMeasure.getInformationSource() != null) {
					current = this.getEntityById(this.em, InformationSource.class, specificMeasure
							.getInformationSource().getId());

					if (current == null) {
						throw new IllegalArgumentException("Unable to identify referenced Information Source with ID #"
								+ specificMeasure.getInformationSource().getId());
					}

					specificMeasure.setInformationSource(current);

					if (current.getSpecificMeasureList() == null) {
						current.setSpecificMeasureList(new ArrayList<SpecificMeasure>());
					}

					current.getSpecificMeasureList().add(specificMeasure);

					if (specificMeasure.getId() == null) {
						this.doPersistAndFlush(em, specificMeasure);
					}
				}
			}
		}

		// Link the GeoRefs to the Vme
		if (vme.getGeoRefList() != null) {
			for (GeoRef geoRef : vme.getGeoRefList()) {
				geoRef.setVme(vme);
			}
		}

		// Link the Profiles to the Vme
		if (vme.getProfileList() != null) {
			for (Profile profile : vme.getProfileList()) {
				profile.setVme(vme);
			}
		}
		
		// Link the Media to the Vme
		if (vme.getMediaReferenceList() != null) {
			for (MediaReference media : vme.getMediaReferenceList()) {
				media.setVme(vme);
			}
		}

		// Link the SpecificMeasures to the Vme
		if (vme.getSpecificMeasureList() != null) {
			for (SpecificMeasure specificMeasure : vme.getSpecificMeasureList()) {
				specificMeasure.setVme(vme);
			}
		}

		// // Link the Criteria to the Vme
		// if (vme.getCriteriaList() != null) {
		// for (Criteria c : vme.getCriteriaList()) {
		// c.setVme(vme);
		// }
		// }

		em.persist(vme);
		return vme;
	}

	public GeoRef update(GeoRef geoRef) {

		if (geoRef == null) {
			throw new IllegalArgumentException("The updated VME Map Reference cannot be NULL");
		}

		if (geoRef.getId() == null) {
			throw new IllegalArgumentException("The updated VME Map Reference cannot have a NULL identifier");
		}

		if (geoRef.getVme() == null) {
			throw new IllegalArgumentException("The updated VME Map Reference cannot have a NULL Vme");
		}

		if (geoRef.getVme().getId() == null) {
			throw new IllegalArgumentException("The updated VME Map Reference cannot have a NULL Vme identifier");
		}

		GeoRef current = this.getEntityById(this.em, GeoRef.class, geoRef.getId());
		current.setGeographicFeatureID(geoRef.getGeographicFeatureID());
		current.setYear(geoRef.getYear());

		GeoRef f = this.doMerge(em, current);
		em.flush();
		return f;
	}

	public GeoRef create(GeoRef geoRef) {

		if (geoRef == null) {
			throw new IllegalArgumentException("The VME Map Reference to create cannot be NULL");
		}

		if (geoRef.getVme() == null) {
			throw new IllegalArgumentException("The VME Map Reference to create cannot have a NULL Vme");
		}

		if (geoRef.getVme().getId() == null) {
			throw new IllegalArgumentException("The VME Map Reference to create cannot have a NULL Vme identifier");
		}

		return this.doPersistAndFlush(em, geoRef);
	}

	public Profile update(Profile profile) {

		if (profile == null) {
			throw new IllegalArgumentException("The updated VME Profile cannot be NULL");
		}

		if (profile.getId() == null) {
			throw new IllegalArgumentException("The updated VME Profile cannot have a NULL identifier");
		}

		if (profile.getVme() == null) {
			throw new IllegalArgumentException("The updated VME Profile cannot have a NULL Vme");
		}

		if (profile.getVme().getId() == null) {
			throw new IllegalArgumentException("The updated VME Profile cannot have a NULL Vme identifier");
		}

		Profile current = this.getEntityById(this.em, Profile.class, profile.getId());
		u.copyMultiLingual(profile, current);
		current.setYear(profile.getYear());
		current.setGeoform(profile.getGeoform());
		current = this.doMerge(em, current);
		em.flush();
		return current;
	}
	
	

	public Profile create(Profile profile) {

		if (profile == null) {
			throw new IllegalArgumentException("The VME Profile to create cannot be NULL");
		}

		if (profile.getVme() == null) {
			throw new IllegalArgumentException("The VME Profile to create cannot have a NULL Vme");
		}

		if (profile.getVme().getId() == null) {
			throw new IllegalArgumentException("The VME Profile to create cannot have a NULL Vme identifier");
		}

		return this.doPersistAndFlush(em, profile);
	}
	
	public MediaReference update(MediaReference media) {

		if (media == null) {
			throw new IllegalArgumentException("The updated VME media cannot be NULL");
		}

		if (media.getId() == null) {
			throw new IllegalArgumentException("The updated VME media cannot have a NULL identifier");
		}

		if (media.getVme() == null) {
			throw new IllegalArgumentException("The updated VME media cannot have a NULL Vme");
		}

		if (media.getVme().getId() == null) {
			throw new IllegalArgumentException("The updated VME media cannot have a NULL Vme identifier");
		}

		MediaReference current = this.getEntityById(this.em, MediaReference.class, media.getId());
		u.copyMultiLingual(media, current);
		current.setType(media.getType());
		current.setUrl(media.getUrl());
		current = this.doMerge(em, current);
		em.flush();
		return current;
	}
	
	public void delete(MediaReference toDelete) {

		if (toDelete == null) {
			throw new IllegalArgumentException("Media reference cannot be NULL");
		}

		if (toDelete.getId() == null) {
			throw new IllegalArgumentException("Media reference id cannot be NULL");
		}

		if (toDelete.getVme() == null) {
			throw new IllegalArgumentException("Media reference cannot have a NULL Vme reference");
		}

		if (toDelete.getVme().getId() == null) {
			throw new IllegalArgumentException("Media reference cannot have a Vme reference with a NULL identifier");
		}

		Vme parent = toDelete.getVme();

		if (parent.getMediaReferenceList() == null) {
			throw new IllegalArgumentException("Media reference cannot have a parent Vme with a NULL media reference list");
		}

		if (parent.getMediaReferenceList().isEmpty()) {
			throw new IllegalArgumentException("Media reference cannot have a parent Vme with an empty media reference list");
		}

		Iterator<MediaReference> iterator = parent.getMediaReferenceList().iterator();

		while (iterator.hasNext()) {
			if (iterator.next().getId().equals(toDelete.getId())) {
				iterator.remove();
			}
		}

		this.doMerge(em, parent);

		this.doRemove(em, toDelete);
	}
	
	public MediaReference create(MediaReference media) {

		if (media == null) {
			throw new IllegalArgumentException("The VME media to create cannot be NULL");
		}

		if (media.getVme() == null) {
			throw new IllegalArgumentException("The VME media to create cannot have a NULL Vme");
		}

		if (media.getVme().getId() == null) {
			throw new IllegalArgumentException("The VME media to create cannot have a NULL Vme identifier");
		}

		return this.doPersistAndFlush(em, media);
	}

	public SpecificMeasure update(SpecificMeasure updatedSM) throws Throwable {

		if (updatedSM == null) {
			throw new IllegalArgumentException("The updated VME Specific Measure cannot be NULL");
		}

		if (updatedSM.getId() == null) {
			throw new IllegalArgumentException("The updated VME Specific Measure cannot have a NULL identifier");
		}

		if (updatedSM.getVme() == null) {
			throw new IllegalArgumentException("The updated VME Specific Measure cannot have a NULL Vme reference");
		}

		if (updatedSM.getVme().getId() == null) {
			throw new IllegalArgumentException(
					"The updated VME Specific Measure cannot have a Vme reference with a NULL identifier");
		}

		InformationSource updatedIS = updatedSM.getInformationSource();

		SpecificMeasure currentSM = this.getEntityById(this.em, SpecificMeasure.class, updatedSM.getId());
		InformationSource currentIS = null;

		if (currentSM.getInformationSource() != null) {
			currentIS = this.getEntityById(this.em, InformationSource.class, currentSM.getInformationSource().getId());
		}

		if (currentIS != null && currentIS.getSpecificMeasureList() != null) {
			currentIS.getSpecificMeasureList().remove(currentSM);
			currentSM.setInformationSource(null);

			em.merge(currentIS);
		}

		currentSM.setValidityPeriod(updatedSM.getValidityPeriod());
		u.copyMultiLingual(updatedSM, currentSM);
		currentSM.setYear(updatedSM.getYear());
		currentSM.setReviewYear(updatedSM.getReviewYear());

		if (updatedIS != null) {
			InformationSource informationSource = this.getEntityById(em, InformationSource.class, updatedIS.getId());

			if (informationSource.getSpecificMeasureList() == null) {
				informationSource.setSpecificMeasureList(new ArrayList<SpecificMeasure>());
			}

			informationSource.getSpecificMeasureList().add(currentSM);

			currentSM.setInformationSource(informationSource);

			em.merge(informationSource);
		}
		currentSM = this.doMerge(em, currentSM);
		em.flush();
		return currentSM;
	}

	public SpecificMeasure create(SpecificMeasure specificMeasure) {

		if (specificMeasure == null) {
			throw new IllegalArgumentException("The VME Specific Measure to create cannot be NULL");
		}

		if (specificMeasure.getVme() == null) {
			throw new IllegalArgumentException("The VME Specific Measure to create cannot have a NULL Vme reference");
		}

		if (specificMeasure.getVme().getId() == null) {
			throw new IllegalArgumentException(
					"The VME Specific Measure to create cannot have a Vme reference with a NULL identifier");
		}

		if (specificMeasure.getInformationSource() != null) {
			InformationSource existing = this.getEntityById(this.em, InformationSource.class, specificMeasure
					.getInformationSource().getId());

			specificMeasure.setInformationSource(existing);
			existing.getSpecificMeasureList().add(specificMeasure);
		}

		return this.doPersistAndFlush(em, specificMeasure);
	}

	// Reference reports types creation / update
	public FisheryAreasHistory update(FisheryAreasHistory fisheryAreasHistory) throws Throwable {

		if (fisheryAreasHistory == null) {
			throw new IllegalArgumentException("The updated Fishing Footprint cannot be NULL");
		}

		if (fisheryAreasHistory.getId() == null) {
			throw new IllegalArgumentException("The updated Fishing Footprint cannot have a NULL identifier");
		}

		if (fisheryAreasHistory.getRfmo() == null) {
			throw new IllegalArgumentException("The updated Fishing Footprint cannot have a NULL Authority");
		}

		if (fisheryAreasHistory.getRfmo().getId() == null) {
			throw new IllegalArgumentException(
					"The updated Fishing Footprint cannot have an Authority with a NULL identifier");
		}

		Rfmo parent = this.getEntityById(em, Rfmo.class, fisheryAreasHistory.getRfmo().getId());
		fisheryAreasHistory.setRfmo(parent);

		fisheryAreasHistory = this.doMerge(em, fisheryAreasHistory);
		em.flush();
		return fisheryAreasHistory;
	}

	public FisheryAreasHistory create(FisheryAreasHistory fisheryAreasHistory) throws Throwable {

		if (fisheryAreasHistory == null) {
			throw new IllegalArgumentException("The Fishing Footprint to create cannot be NULL");
		}

		if (fisheryAreasHistory.getRfmo() == null) {
			throw new IllegalArgumentException("The Fishing Footprint to create cannot have a NULL Authority");
		}

		if (fisheryAreasHistory.getRfmo().getId() == null) {
			throw new IllegalArgumentException(
					"The Fishing Footprint to create cannot have an Authority with a NULL identifier");
		}

		Rfmo parent = this.getEntityById(em, Rfmo.class, fisheryAreasHistory.getRfmo().getId());

		fisheryAreasHistory.setRfmo(parent);

		if (parent.getHasFisheryAreasHistory() == null) {
			parent.setHasFisheryAreasHistory(new ArrayList<FisheryAreasHistory>());
		}

		parent.getHasFisheryAreasHistory().add(fisheryAreasHistory);

		// It seems that merging the parent will cause TWO fishery areas history
		// to be stored in the DB... Commenting this out
		// this.doMerge(em, parent);

		return this.doPersistAndFlush(em, fisheryAreasHistory);
	}

	public VMEsHistory update(VMEsHistory vmesHistory) throws Throwable {

		if (vmesHistory == null) {
			throw new IllegalArgumentException("The updated Regional History of VME cannot be NULL");
		}

		if (vmesHistory.getId() == null) {
			throw new IllegalArgumentException("The updated Regional History of VME cannot have a NULL identifier");
		}

		if (vmesHistory.getRfmo() == null) {
			throw new IllegalArgumentException("The updated Regional History of VME cannot have a NULL Authority");
		}

		if (vmesHistory.getRfmo().getId() == null) {
			throw new IllegalArgumentException(
					"The updated Regional History of VME cannot have an Authority with a NULL identifier");
		}

		Rfmo parent = this.getEntityById(em, Rfmo.class, vmesHistory.getRfmo().getId());

		vmesHistory.setRfmo(parent);

		vmesHistory = this.doMerge(em, vmesHistory);
		em.flush();
		return vmesHistory;
	}

	public VMEsHistory create(VMEsHistory vmesHistory) throws Throwable {

		if (vmesHistory == null) {
			throw new IllegalArgumentException("The Regional History of VME to create cannot be NULL");
		}

		if (vmesHistory.getRfmo() == null) {
			throw new IllegalArgumentException("The Regional History of VME to create cannot have a NULL Authority");
		}

		if (vmesHistory.getRfmo().getId() == null) {
			throw new IllegalArgumentException(
					"The Regional History of VME to create cannot have an Authority with a NULL identifier");
		}

		Rfmo parent = this.getEntityById(em, Rfmo.class, vmesHistory.getRfmo().getId());

		vmesHistory.setRfmo(parent);

		if (parent.getHasVmesHistory() == null) {
			parent.setHasVmesHistory(new ArrayList<VMEsHistory>());
		}

		parent.getHasVmesHistory().add(vmesHistory);

		// It seems that merging the parent will cause TWO fishery areas history
		// to be stored in the DB... Commenting this out
		// this.doMerge(em, parent);

		return this.doPersistAndFlush(em, vmesHistory);
	}

	public InformationSource update(InformationSource informationSource) throws Throwable {

		if (informationSource == null) {
			throw new IllegalArgumentException("The updated Information Source cannot be NULL");
		}

		if (informationSource.getId() == null) {
			throw new IllegalArgumentException("The updated Information Source cannot have a NULL identifier");
		}

		if (informationSource.getRfmo() == null) {
			throw new IllegalArgumentException("The updated Information Source cannot have a NULL Authority");
		}

		if (informationSource.getRfmo().getId() == null) {
			throw new IllegalArgumentException(
					"The updated Information Source cannot have an Authority with a NULL identifier");
		}

		InformationSource current = this.getEntityById(this.em, InformationSource.class, informationSource.getId());
		u.copyMultiLingual(informationSource, current);
		current.setSourceType(informationSource.getSourceType());
		current.setMeetingEndDate(informationSource.getMeetingEndDate());
		current.setMeetingStartDate(informationSource.getMeetingStartDate());
		current.setPublicationYear(informationSource.getPublicationYear());
		current.setUrl(informationSource.getUrl());

		current.setRfmo(this.getEntityById(em, Rfmo.class, informationSource.getRfmo().getId()));

		current = this.doMerge(em, current);
		em.flush();
		return current;
	}

	public InformationSource create(InformationSource informationSource) throws Throwable {

		if (informationSource == null) {
			throw new IllegalArgumentException("The Information Source to create cannot be NULL");
		}

		if (informationSource.getRfmo() == null) {
			throw new IllegalArgumentException("The Information Source to create cannot have a NULL Authority");
		}

		if (informationSource.getRfmo().getId() == null) {
			throw new IllegalArgumentException(
					"The Information Source to create cannot have an Authority with a NULL identifier");
		}

		Rfmo parent = this.getEntityById(em, Rfmo.class, informationSource.getRfmo().getId());
		informationSource.setRfmo(parent);

		return this.doPersistAndFlush(em, informationSource);
	}

	public GeneralMeasure update(GeneralMeasure generalMeasure) throws Throwable {

		if (generalMeasure == null) {
			throw new IllegalArgumentException("The updated VME General Measure cannot be NULL");
		}

		if (generalMeasure.getId() == null) {
			throw new IllegalArgumentException("The updated VME General Measure cannot have a NULL identifier");
		}

		// if (generalMeasure.getRfmo() == null) {
		// throw new
		// IllegalArgumentException("The updated VME General Measure cannot have a NULL Authority");
		// }
		//
		// if (generalMeasure.getRfmo().getId() == null) {
		// throw new IllegalArgumentException(
		// "The updated GeneralMeasure cannot have an Authority with a NULL identifier");
		// }

		GeneralMeasure current = this.getEntityById(this.em, GeneralMeasure.class, generalMeasure.getId());
		u.copyMultiLingual(generalMeasure, current);
		current.setValidityPeriod(generalMeasure.getValidityPeriod());
		current.setYear(generalMeasure.getYear());

		// current.setRfmo(this.getEntityById(em, Rfmo.class,
		// generalMeasure.getRfmo().getId()));

		Set<Long> is2Unlink = new HashSet<Long>();

		if (current.getInformationSourceList() != null) {
			for (InformationSource is : current.getInformationSourceList()) {
				is2Unlink.add(is.getId());
			}
		}

		if (generalMeasure.getInformationSourceList() != null) {
			for (InformationSource is : generalMeasure.getInformationSourceList()) {
				is2Unlink.remove(is.getId());
			}
		}

		if (!is2Unlink.isEmpty()) {
			Iterator<InformationSource> isIterator = current.getInformationSourceList().iterator();

			InformationSource toRemove;
			while (isIterator.hasNext()) {
				toRemove = isIterator.next();
				if (is2Unlink.contains(toRemove.getId())) {
					isIterator.remove();

					toRemove.getGeneralMeasureList().remove(current);

					this.doMerge(em, toRemove);
				}
			}
		}

		if (generalMeasure.getInformationSourceList() != null) {
			InformationSource toAdd;
			List<InformationSource> currentIS = current.getInformationSourceList();

			if (currentIS == null) {
				currentIS = new ArrayList<InformationSource>();

				current.setInformationSourceList(currentIS);
			}

			for (InformationSource is : generalMeasure.getInformationSourceList()) {
				toAdd = this.getEntityById(em, InformationSource.class, is.getId());

				if (!currentIS.contains(toAdd)) {
					if (toAdd.getGeneralMeasureList() == null) {
						toAdd.setGeneralMeasureList(new ArrayList<GeneralMeasure>());
					}

					toAdd.getGeneralMeasureList().add(current);

					currentIS.add(toAdd);
				}
			}
		}

		// //Clear the currently set InformationSources for the GM (if any)
		// if(current.getInformationSourceList() != null) {
		// Iterator<InformationSource> isIterator =
		// current.getInformationSourceList().iterator();
		//
		// InformationSource currentIs;
		// while(isIterator.hasNext()) {
		// currentIs = isIterator.next();
		//
		// currentIs.getGeneralMeasureList().remove(current);
		//
		// em.merge(currentIs);
		//
		// isIterator.remove();
		//
		// em.merge(current);
		// }
		// }
		//
		// //Assign the InformationSources for the GM (if any)
		// if(generalMeasure.getInformationSourceList() != null) {
		// InformationSource existing;
		// for(InformationSource informationSource :
		// generalMeasure.getInformationSourceList()) {
		// existing = this.getEntityById(this.em, InformationSource.class,
		// informationSource.getId());
		//
		// current.getInformationSourceList().add(existing);
		//
		// this.doMerge(em, existing);
		// }
		// }

		current = this.doMerge(em, current);
		// Flush during cascade is dangerous, but it needs to be done anyway in
		// order to make sure that changes are reflected.
		em.flush();
		return current;
	}

	public GeneralMeasure create(GeneralMeasure generalMeasure) throws Throwable {

		if (generalMeasure == null) {
			throw new IllegalArgumentException("The VME General Measure to create cannot be NULL");
		}

		if (generalMeasure.getRfmo() == null) {
			throw new IllegalArgumentException("The VME General Measure to create cannot have a NULL Authority");
		}

		if (generalMeasure.getRfmo().getId() == null) {
			throw new IllegalArgumentException(
					"The VME General Measure to create cannot have an Authority with a NULL identifier");
		}

		Rfmo parent = this.getEntityById(em, Rfmo.class, generalMeasure.getRfmo().getId());
		generalMeasure.setRfmo(parent);

		generalMeasure = this.doPersistAndFlush(em, generalMeasure);

		em.refresh(generalMeasure);

		return generalMeasure;
	}
}