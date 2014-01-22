package org.vme.service.dao.sources.vme;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
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
	@Inject @VmeDB private EntityManager em;

	/**
	 * @return the 'em' value
	 */
	final public EntityManager getEm() {
		return this.em;
	}

	public List<Vme> loadVmes() {
		return (List<Vme>) this.generateTypedQuery(em, Vme.class).getResultList();
	}

	public Vme findVme(Long id) {
		return em.find(Vme.class, id);
	}
	
	public EntityTransaction begin() {
		return this.em.getTransaction();
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

		// try {
		et.begin();

		if(vme.getRfmo() != null) {
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
		}

		if(vme.getGeoRefList() != null)
			for (GeoRef geoRef : vme.getGeoRefList()) {
				em.persist(geoRef);
			}
		
		if(vme.getProfileList() != null)
			for (Profile profile : vme.getProfileList()) {
				em.persist(profile);
			}
		
		if(vme.getSpecificMeasureList() != null)
			for (SpecificMeasure specificMeasure : vme.getSpecificMeasureList()) {
				em.persist(specificMeasure);
			}

		em.persist(vme);
		em.flush();

		et.commit();

		LOG.debug("Vme {} has been saved into persistence", vme);

		return vme;
	}

	public <E> List<E> loadObjects(Class<E> clazz) {
		return super.loadObjects(em, clazz);
	}

	public <E> E merge(E object) {
		return super.merge(em, object);
	}

	public <E> E persist(E object) {
		return super.persist(em, object);
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
		if (toDelete == null)
			throw new IllegalArgumentException("Object to delete cannot be NULL");

		Class<?> entity = toDelete.getClass();
		
		//I know this sucks...
		if(!entity.equals(Object.class)) {
			Method deleteMethod = null;
			
			try {
				deleteMethod = this.getClass().getDeclaredMethod("delete", entity);
			} catch (Throwable t) {
				throw new IllegalArgumentException("Unable to find a proper 'delete' method for " + toDelete.getClass().getSimpleName() + ": " + t.getMessage(), t);
			}
			
			if(deleteMethod != null) {
				try {
					deleteMethod.invoke(this, toDelete);
				} catch (IllegalArgumentException IAe) {
					throw new VmeDaoException("Unable to invoke the 'delete' method for " + toDelete.getClass().getSimpleName() + ": " + IAe.getMessage(), IAe);
				} catch (IllegalAccessException IAe) {
					throw new VmeDaoException("Unable to invoke the 'delete' method for " + toDelete.getClass().getSimpleName() + ": " + IAe.getMessage(), IAe);
				} catch (InvocationTargetException ITe) {
					throw new VmeDaoException("Unable to invoke the 'delete' method for " + toDelete.getClass().getSimpleName() + ": " + ITe.getMessage(), ITe);
				} catch(Throwable t) {
					throw new VmeDaoException(t.getMessage(), t);
				}
			}
		}
	}

	public void delete(Vme toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("Vme cannot be NULL");

		if (toDelete.getId() == null)
			throw new IllegalArgumentException("Vme id cannot be NULL");

		if (toDelete.getRfmo() == null)
			throw new IllegalArgumentException("Vme cannot have a NULL parent RFMO");

		Rfmo parent = toDelete.getRfmo();

		Iterator<Vme> rfmoIterator = parent.getListOfManagedVmes().iterator();

		while (rfmoIterator.hasNext())
			if (rfmoIterator.next().getId().equals(toDelete.getId()))
				rfmoIterator.remove();

		this.doMerge(em, parent);

		if (toDelete.getProfileList() != null)
			for (Profile profile : new ArrayList<Profile>(toDelete.getProfileList()))
				this.delete(profile);

		if (toDelete.getGeoRefList() != null)
			for (GeoRef geoRef : new ArrayList<GeoRef>(toDelete.getGeoRefList()))
				this.delete(geoRef);

		if (toDelete.getSpecificMeasureList() != null)
			for (SpecificMeasure specificMeasure : new ArrayList<SpecificMeasure>(toDelete.getSpecificMeasureList()))
				this.delete(specificMeasure);

		this.doRemove(em, toDelete);
	}

	public void delete(InformationSource toDelete) {
		EntityTransaction et = em.getTransaction();
		et.begin();

		toDelete.getRfmo().getInformationSourceList().remove(toDelete);
		em.merge(toDelete.getRfmo());
		toDelete.setRfmo(null);

		for (GeneralMeasure generalMeasure : toDelete.getGeneralMeasureList()) {
			generalMeasure.getInformationSourceList().remove(toDelete);
			this.doMerge(em, generalMeasure);
		}

		for (SpecificMeasure specificMeasure : toDelete.getSpecificMeasureList()) {
			specificMeasure.setInformationSource(null);
			this.doMerge(em, specificMeasure);
		}

		em.remove(toDelete);
		et.commit();
	}

	public void delete(VMEsHistory toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("VMEsHistory cannot be NULL");

		if (toDelete.getId() == null)
			throw new IllegalArgumentException("VMEsHistory id cannot be NULL");

		if (toDelete.getRfmo() == null)
			throw new IllegalArgumentException("VMEsHistory cannot have a NULL parent RFMO");

		Rfmo parent = toDelete.getRfmo();

		Iterator<VMEsHistory> rfmoIterator = parent.getHasVmesHistory().iterator();

		while (rfmoIterator.hasNext())
			if (rfmoIterator.next().getId().equals(toDelete.getId()))
				rfmoIterator.remove();

		this.doMerge(em, parent);

		this.doRemove(em, toDelete);
	}

	public void delete(FisheryAreasHistory toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("FisheryAreasHistory cannot be NULL");

		if (toDelete.getId() == null)
			throw new IllegalArgumentException("FisheryAreasHistory id cannot be NULL");

		if (toDelete.getRfmo() == null)
			throw new IllegalArgumentException("FisheryAreasHistory cannot have a NULL parent RFMO");

		Rfmo parent = toDelete.getRfmo();

		Iterator<FisheryAreasHistory> rfmoIterator = parent.getHasFisheryAreasHistory().iterator();

		while (rfmoIterator.hasNext())
			if (rfmoIterator.next().getId().equals(toDelete.getId()))
				rfmoIterator.remove();

		this.doMerge(em, parent);

		this.doRemove(em, toDelete);
	}

	public void delete(Profile toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("Profile cannot be NULL");

		if (toDelete.getId() == null)
			throw new IllegalArgumentException("Profile id cannot be NULL");

		if (toDelete.getVme() == null)
			throw new IllegalArgumentException("Profile cannot have a NULL Vme reference");

		if (toDelete.getVme().getId() == null)
			throw new IllegalArgumentException("Profile cannot have a Vme reference with a NULL id");

		Vme parent = toDelete.getVme();

		if (parent.getProfileList() == null)
			throw new IllegalArgumentException("Profile cannot have a parent Vme with a NULL profile list");

		if (parent.getProfileList().isEmpty())
			throw new IllegalArgumentException("Profile cannot have a parent Vme with an empty profile list");

		Iterator<Profile> iterator = parent.getProfileList().iterator();

		while (iterator.hasNext())
			if (iterator.next().getId().equals(toDelete.getId()))
				iterator.remove();

		this.doMerge(em, parent);

		this.doRemove(em, toDelete);
	}

	public void delete(GeoRef toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("GeoRef cannot be NULL");

		if (toDelete.getId() == null)
			throw new IllegalArgumentException("GeoRef id cannot be NULL");

		if (toDelete.getVme() == null)
			throw new IllegalArgumentException("GeoRef cannot have a NULL Vme reference");

		if (toDelete.getVme().getId() == null)
			throw new IllegalArgumentException("GeoRef cannot have a Vme reference with a NULL id");

		Vme parent = toDelete.getVme();

		if (parent.getGeoRefList() == null)
			throw new IllegalArgumentException("GeoRef cannot have a parent Vme with a NULL profile list");

		if (parent.getGeoRefList().isEmpty())
			throw new IllegalArgumentException("GeoRef cannot have a parent Vme with an empty profile list");

		Iterator<GeoRef> iterator = parent.getGeoRefList().iterator();

		while (iterator.hasNext())
			if (iterator.next().getId().equals(toDelete.getId()))
				iterator.remove();

		this.doMerge(em, parent);

		this.doRemove(em, toDelete);
	}

	public void delete(GeneralMeasure toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("GeneralMeasure cannot be NULL");

		if (toDelete.getId() == null)
			throw new IllegalArgumentException("GeneralMeasure id cannot be NULL");

		if (toDelete.getRfmo() == null)
			throw new IllegalArgumentException("GeneralMeasure cannot have a NULL parent RFMO");

		Iterator<GeneralMeasure> rfmoIterator = toDelete.getRfmo().getGeneralMeasureList().iterator();

		while (rfmoIterator.hasNext())
			if (rfmoIterator.next().getId().equals(toDelete.getId()))
				rfmoIterator.remove();

		this.doMerge(em, toDelete.getRfmo());

		for (InformationSource informationSource : toDelete.getInformationSourceList()) {
			Iterator<GeneralMeasure> informationSourceIterator = informationSource.getGeneralMeasureList().iterator();

			while (informationSourceIterator.hasNext())
				if (informationSourceIterator.next().getId().equals(toDelete.getId()))
					informationSourceIterator.remove();

			this.doMerge(em, informationSource);
		}

		this.doRemove(em, toDelete);
	}

	public void delete(SpecificMeasure toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("SpecificMeasure cannot be NULL");

		if (toDelete.getId() == null)
			throw new IllegalArgumentException("SpecificMeasure id cannot be NULL");

		if (toDelete.getVme() == null)
			throw new IllegalArgumentException("SpecificMeasure cannot have a NULL Vme reference");

		if (toDelete.getVme().getId() == null)
			throw new IllegalArgumentException("SpecificMeasure cannot have an Vme reference with a NULL id");

		Vme vme = toDelete.getVme();
		
		if(vme.getSpecificMeasureList() != null) {
			Iterator<SpecificMeasure> vmeIterator = vme.getSpecificMeasureList().iterator();
	
			while(vmeIterator.hasNext())
				if (vmeIterator.next().getId().equals(toDelete.getId()))
					vmeIterator.remove();
	
			this.doMerge(em, vme);
		}

		if (toDelete.getInformationSource() != null) {
			Iterator<SpecificMeasure> informationSourceIterator = toDelete.getInformationSource().getSpecificMeasureList().iterator();

			while (informationSourceIterator.hasNext())
				if (informationSourceIterator.next().getId().equals(toDelete.getId()))
					informationSourceIterator.remove();

			this.doMerge(em, toDelete.getInformationSource());
		}

		this.doRemove(em, toDelete);
	}
	
	public Vme oldupdate(Vme vme) throws Throwable {
		if(vme == null)
			throw new IllegalArgumentException("Updated Vme cannot be NULL");
		
		if(vme.getId() == null)
			throw new IllegalArgumentException("Updated Vme cannot have a NULL id");

		if(vme.getRfmo() == null)
			throw new IllegalArgumentException("Updated Vme cannot have a NULL Rfmo");

		if(vme.getRfmo().getId() == null)
			throw new IllegalArgumentException("Updated Vme cannot have a Rfmo with a NULL id");

		Vme current = this.getEntityById(this.em, Vme.class, vme.getId());

		Rfmo rfmo = this.getEntityById(this.em, Rfmo.class, vme.getRfmo().getId());
		Rfmo currentRfmo = current.getRfmo();
		
		vme.setRfmo(rfmo);

		//Vme has changed RFMO (should happen rarely...)
		if(!currentRfmo.getId().equals(rfmo.getId())) {
			//Unlink updated Vme from its previous Rfmo...
			Iterator<Vme> vmeIterator = currentRfmo.getListOfManagedVmes().iterator();
			
			while(vmeIterator.hasNext())
				if(vmeIterator.next().getId().equals(vme.getId()))
					vmeIterator.remove();
			
			this.doMerge(em, currentRfmo);

			//Unlink updated vme from previous Rfmo...
			vmeIterator = rfmo.getListOfManagedVmes().iterator();
			
			while(vmeIterator.hasNext())
				if(vmeIterator.next().getId().equals(vme.getId()))
					vmeIterator.remove();
			
			rfmo.getListOfManagedVmes().add(vme);
			
			this.doMerge(em, rfmo);
		}
		
		if(vme.getGeoRefList() != null)
			for(GeoRef geoRef : vme.getGeoRefList()) {
				geoRef.setVme(vme);
				
				if(geoRef.getId() == null)
					geoRef.setId(this.create(geoRef).getId());
				else
					geoRef.setId(this.update(geoRef).getId());
			}
		
		if(vme.getProfileList() != null)
			for(Profile profile : vme.getProfileList()) {
				profile.setVme(vme);
				
				if(profile.getId() == null)
					profile.setId(this.create(profile).getId());
				else
					profile.setId(this.update(profile).getId());
			}
		
		SpecificMeasure currentSM;
		
		List<SpecificMeasure> specificMeasures = new ArrayList<SpecificMeasure>();

		if(vme.getSpecificMeasureList() != null)
			for(SpecificMeasure specificMeasure : vme.getSpecificMeasureList()) {
				specificMeasure.setVme(vme);
	
				if(specificMeasure.getId() == null) {
					specificMeasure.setId(this.create(specificMeasure).getId());
					
					specificMeasures.add(specificMeasure);
				} else {
					currentSM = this.getEntityById(this.em, SpecificMeasure.class, specificMeasure.getId());
	
					currentSM.setValidityPeriod(specificMeasure.getValidityPeriod());
					currentSM.setYear(specificMeasure.getYear());
					currentSM.setVmeSpecificMeasure(specificMeasure.getVmeSpecificMeasure());
					
					specificMeasures.add(currentSM);
				}
			}
		
		vme.setSpecificMeasureList(specificMeasures);
		
		return this.doMerge(em, vme);
	}
	
	public Vme update(Vme vme) throws Throwable {
		if(vme == null)
			throw new IllegalArgumentException("Updated Vme cannot be NULL");
		
		if(vme.getId() == null)
			throw new IllegalArgumentException("Updated Vme cannot have a NULL id");

		if(vme.getRfmo() == null)
			throw new IllegalArgumentException("Updated Vme cannot have a NULL Rfmo");

		if(vme.getRfmo().getId() == null)
			throw new IllegalArgumentException("Updated Vme cannot have a Rfmo with a NULL id");
			
		//Link the information source (by ID) to the specific measure
		if(vme.getSpecificMeasureList() != null)
			for(SpecificMeasure specificMeasure : vme.getSpecificMeasureList()) {
				if(specificMeasure.getInformationSource() != null) {
					specificMeasure.setInformationSource(this.getEntityById(this.em, InformationSource.class, specificMeasure.getInformationSource().getId()));
				}
			}

		//Link the GeoRefs to the Vme
		if(vme.getGeoRefList() != null)
			for (GeoRef geoRef : vme.getGeoRefList()) {
				geoRef.setVme(vme);
			}

		//Link the Profiles to the Vme
		if(vme.getProfileList() != null)
			for (Profile profile : vme.getProfileList()) {
				profile.setVme(vme);
			}
		
		//Link the SpecificMeasures to the Vme
		if(vme.getSpecificMeasureList() != null)
			for (SpecificMeasure specificMeasure : vme.getSpecificMeasureList()) {
				specificMeasure.setVme(vme);
			}
		
		//Get the current Vme status (before the update)
		Vme existing = this.getEntityById(this.em, Vme.class, vme.getId());
		
		//Build the list of IDs for GeoRef / Profile / SpecificMeasure for the Vme in its current status.
		Set<Long> currentGeoRefs = new HashSet<Long>();
		if(existing.getGeoRefList() != null)
			for(GeoRef geoRef : existing.getGeoRefList())
				currentGeoRefs.add(geoRef.getId());

		Set<Long> currentProfiles = new HashSet<Long>();
		if(existing.getProfileList() != null)
			for(Profile profile : existing.getProfileList())
				currentProfiles.add(profile.getId());

		Set<Long> currentSpecificMeasures = new HashSet<Long>();
		if(existing.getSpecificMeasureList() != null)
			for(SpecificMeasure specificMeasure : existing.getSpecificMeasureList())
				currentSpecificMeasures.add(specificMeasure.getId());

		//Update the Vme: this will unlink GeoRefs / Profiles / SpecificMeasures but not remove them.
		Vme updated = this.doMerge(em, vme);

		//Check if any GeoRef / Profile / Specific Measure is missing between the updated VME and its current (previous) status.
		if(updated.getGeoRefList() != null)
			for(GeoRef geoRef : updated.getGeoRefList())
				currentGeoRefs.remove(geoRef.getId());

		if(updated.getProfileList() != null)
			for(Profile profile : updated.getProfileList())
				currentProfiles.remove(profile.getId());

		if(updated.getSpecificMeasureList() != null)
			for(SpecificMeasure specificMeasure : updated.getSpecificMeasureList())
				currentSpecificMeasures.remove(specificMeasure.getId());

		//If any GeoRef / Profile / Specific Measure is missing, it must be deleted in order not to leave 'orphan' children.
		for(Long id : currentGeoRefs) this.doRemove(em, this.getEntityById(this.em, GeoRef.class, id));
		for(Long id : currentProfiles) this.doRemove(em, this.getEntityById(this.em, Profile.class, id));
		for(Long id : currentSpecificMeasures) this.doRemove(em, this.getEntityById(this.em, SpecificMeasure.class, id));
		
		return updated;
	}
	
	public Vme create(Vme vme) throws Throwable {
		if(vme == null)
			throw new IllegalArgumentException("Updated Vme cannot be NULL");
				
		//Link the information source (by ID) to the specific measure
		if(vme.getSpecificMeasureList() != null)
			for(SpecificMeasure specificMeasure : vme.getSpecificMeasureList()) {
				if(specificMeasure.getInformationSource() != null) {
					specificMeasure.setInformationSource(this.getEntityById(this.em, InformationSource.class, specificMeasure.getInformationSource().getId()));
				}
			}
		
		//Link the GeoRefs to the Vme
		if(vme.getGeoRefList() != null)
			for (GeoRef geoRef : vme.getGeoRefList()) {
				geoRef.setVme(vme);
			}
		
		//Link the Profiles to the Vme
		if(vme.getProfileList() != null)
			for (Profile profile : vme.getProfileList()) {
				profile.setVme(vme);
			}

		//Link the SpecificMeasures to the Vme
		if(vme.getSpecificMeasureList() != null)
			for (SpecificMeasure specificMeasure : vme.getSpecificMeasureList()) {
				specificMeasure.setVme(vme);
			}
	
		return this.doPersistAndFlush(em, vme);
	}
	
	public GeoRef update(GeoRef geoRef) throws Throwable {
		if(geoRef == null)
			throw new IllegalArgumentException("Updated GeoRef cannot be NULL");
		
		if(geoRef.getId() == null)
			throw new IllegalArgumentException("Updated GeoRef cannot have a NULL id");
		
		if(geoRef.getVme() == null)
			throw new IllegalArgumentException("Updated GeoRef cannot have a NULL Vme");
		
		if(geoRef.getVme().getId() == null)
			throw new IllegalArgumentException("Updated GeoRef cannot have a NULL Vme id");
		
		GeoRef current = this.getEntityById(this.em, GeoRef.class, geoRef.getId());
		current.setGeographicFeatureID(geoRef.getGeographicFeatureID());
		current.setYear(geoRef.getYear());
		
		return this.doMerge(em, current);
	}
	
	public GeoRef create(GeoRef geoRef) throws Throwable {
		if(geoRef == null)
			throw new IllegalArgumentException("Updated GeoRef cannot be NULL");
		
		if(geoRef.getVme() == null)
			throw new IllegalArgumentException("Updated GeoRef cannot have a NULL Vme");
		
		if(geoRef.getVme().getId() == null)
			throw new IllegalArgumentException("Updated GeoRef cannot have a NULL Vme id");
		
		return this.doPersistAndFlush(em, geoRef);
	}
	
	public Profile update(Profile profile) throws Throwable {
		if(profile == null)
			throw new IllegalArgumentException("Updated Profile cannot be NULL");
		
		if(profile.getId() == null)
			throw new IllegalArgumentException("Updated Profile cannot have a NULL id");
		
		if(profile.getVme() == null)
			throw new IllegalArgumentException("Updated Profile cannot have a NULL Vme");
		
		if(profile.getVme().getId() == null)
			throw new IllegalArgumentException("Updated Profile cannot have a NULL Vme id");
		
		Profile current = this.getEntityById(this.em, Profile.class, profile.getId());
		current.setDescriptionBiological(profile.getDescriptionBiological());
		current.setDescriptionImpact(profile.getDescriptionImpact());
		current.setDescriptionPhisical(profile.getDescriptionPhisical());
		current.setYear(profile.getYear());
		current.setGeoform(profile.getGeoform());
		
		return this.doMerge(em, current);
	}
	
	public Profile create(Profile profile) throws Throwable {
		if(profile == null)
			throw new IllegalArgumentException("Updated Profile cannot be NULL");
		
		if(profile.getVme() == null)
			throw new IllegalArgumentException("Updated Profile cannot have a NULL Vme");
		
		if(profile.getVme().getId() == null)
			throw new IllegalArgumentException("Updated Profile cannot have a NULL Vme id");
		
		return this.doPersistAndFlush(em, profile);
	}
	
	public SpecificMeasure update(SpecificMeasure specificMeasure) throws Throwable {
		if(specificMeasure == null)
			throw new IllegalArgumentException("Updated SpecificMeasure cannot be NULL");
		
		if(specificMeasure.getId() == null)
			throw new IllegalArgumentException("Updated SpecificMeasure cannot have a NULL id");
		
		if(specificMeasure.getVme() == null)
			throw new IllegalArgumentException("Updated SpecificMeasure cannot have a NULL Vme reference");

		if(specificMeasure.getVme().getId() == null)
			throw new IllegalArgumentException("Updated SpecificMeasure cannot have a Vme reference with a NULL id");
		
		SpecificMeasure current = this.getEntityById(this.em, SpecificMeasure.class, specificMeasure.getId());
		current.setValidityPeriod(specificMeasure.getValidityPeriod());
		current.setVmeSpecificMeasure(specificMeasure.getVmeSpecificMeasure());
		current.setInformationSource(specificMeasure.getInformationSource());
		current.setYear(specificMeasure.getYear());
		
		return this.doMerge(em, specificMeasure);
	}
	
	public SpecificMeasure create(SpecificMeasure specificMeasure) throws Throwable {
		if(specificMeasure == null)
			throw new IllegalArgumentException("SpecificMeasure to create cannot be NULL");
				
		if(specificMeasure.getVme() == null)
			throw new IllegalArgumentException("SpecificMeasure to create cannot have a NULL Vme reference");

		if(specificMeasure.getVme().getId() == null)
			throw new IllegalArgumentException("SpecificMeasure to create cannot have a Vme reference with a NULL id");
		
		return this.doPersistAndFlush(em, specificMeasure);
	}
	
	//Reference reports types creation / update
	public FisheryAreasHistory update(FisheryAreasHistory fisheryAreasHistory) throws Throwable {
		if(fisheryAreasHistory == null)
			throw new IllegalArgumentException("Updated FisheryAreasHistory cannot be NULL");
				
		if(fisheryAreasHistory.getId() == null)
			throw new IllegalArgumentException("Updated FisheryAreasHistory cannot have a NULL id");
		
		if(fisheryAreasHistory.getRfmo() == null)
			throw new IllegalArgumentException("Updated FisheryAreasHistory cannot have a NULL Rfmo reference");

		if(fisheryAreasHistory.getRfmo().getId() == null)
			throw new IllegalArgumentException("Updated FisheryAreasHistory cannot have a Rfmo reference with a NULL id");
		
		return this.doMerge(em, fisheryAreasHistory);
	}
	
	public FisheryAreasHistory create(FisheryAreasHistory fisheryAreasHistory) throws Throwable {
		if(fisheryAreasHistory == null)
			throw new IllegalArgumentException("FisheryAreasHistory to create cannot be NULL");
				
		if(fisheryAreasHistory.getRfmo() == null)
			throw new IllegalArgumentException("FisheryAreasHistory to create cannot have a NULL Rfmo reference");

		if(fisheryAreasHistory.getRfmo().getId() == null)
			throw new IllegalArgumentException("SpecificMeasure to create cannot have a Rfmo reference with a NULL id");
		
		return this.doPersistAndFlush(em, fisheryAreasHistory);
	}
	
	public VMEsHistory update(VMEsHistory VMEsHistory) throws Throwable {
		if(VMEsHistory == null)
			throw new IllegalArgumentException("Updated VMEsHistory cannot be NULL");
				
		if(VMEsHistory.getId() == null)
			throw new IllegalArgumentException("Updated VMEsHistory cannot have a NULL id");
		
		if(VMEsHistory.getRfmo() == null)
			throw new IllegalArgumentException("Updated VMEsHistory cannot have a NULL Rfmo reference");

		if(VMEsHistory.getRfmo().getId() == null)
			throw new IllegalArgumentException("Updated VMEsHistory cannot have a Rfmo reference with a NULL id");
		
		return this.doMerge(em, VMEsHistory);
	}
	
	public VMEsHistory create(VMEsHistory VMEsHistory) throws Throwable {
		if(VMEsHistory == null)
			throw new IllegalArgumentException("VMEsHistory to create cannot be NULL");
				
		if(VMEsHistory.getRfmo() == null)
			throw new IllegalArgumentException("VMEsHistory to create cannot have a NULL Rfmo reference");

		if(VMEsHistory.getRfmo().getId() == null)
			throw new IllegalArgumentException("VMEsHistory to create cannot have a Rfmo reference with a NULL id");
		
		return this.doPersistAndFlush(em, VMEsHistory);
	}
	
	public InformationSource update(InformationSource informationSource) throws Throwable {
		if(informationSource == null)
			throw new IllegalArgumentException("Updated InformationSource cannot be NULL");
				
		if(informationSource.getId() == null)
			throw new IllegalArgumentException("Updated InformationSource cannot have a NULL id");
		
		if(informationSource.getRfmo() == null)
			throw new IllegalArgumentException("Updated InformationSource cannot have a NULL Rfmo reference");

		if(informationSource.getRfmo().getId() == null)
			throw new IllegalArgumentException("Updated InformationSource cannot have a Rfmo reference with a NULL id");
		
		return this.doMerge(em, informationSource);
	}
	
	public InformationSource create(InformationSource informationSource) throws Throwable {
		if(informationSource == null)
			throw new IllegalArgumentException("InformationSource to create cannot be NULL");
				
		if(informationSource.getRfmo() == null)
			throw new IllegalArgumentException("InformationSource to create cannot have a NULL Rfmo reference");

		if(informationSource.getRfmo().getId() == null)
			throw new IllegalArgumentException("InformationSource to create cannot have a Rfmo reference with a NULL id");
		
		return this.doPersistAndFlush(em, informationSource);
	}
	
	public GeneralMeasure update(GeneralMeasure generalMeasure) throws Throwable {
		if(generalMeasure == null)
			throw new IllegalArgumentException("Updated GeneralMeasure cannot be NULL");
				
		if(generalMeasure.getId() == null)
			throw new IllegalArgumentException("Updated GeneralMeasure cannot have a NULL id");
		
		if(generalMeasure.getRfmo() == null)
			throw new IllegalArgumentException("Updated GeneralMeasure cannot have a NULL Rfmo reference");

		if(generalMeasure.getRfmo().getId() == null)
			throw new IllegalArgumentException("Updated GeneralMeasure cannot have a Rfmo reference with a NULL id");
		
		return this.doMerge(em, generalMeasure);
	}
	
	public GeneralMeasure create(GeneralMeasure generalMeasure) throws Throwable {
		if(generalMeasure == null)
			throw new IllegalArgumentException("GeneralMeasure to create cannot be NULL");
				
		if(generalMeasure.getRfmo() == null)
			throw new IllegalArgumentException("GeneralMeasure to create cannot have a NULL Rfmo reference");

		if(generalMeasure.getRfmo().getId() == null)
			throw new IllegalArgumentException("GeneralMeasure to create cannot have a Rfmo reference with a NULL id");
		
		return this.doPersistAndFlush(em, generalMeasure);
	}
}