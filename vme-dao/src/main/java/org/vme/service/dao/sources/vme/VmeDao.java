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
import org.fao.fi.vme.domain.model.Rfmo;
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
			this.doRemove(object);
			em.flush();
			et.commit();
			LOG.debug("Object {} has been removed from persistence", object);
		} catch (Throwable t) {
			LOG.error("Unable to remove object {} from persistence: {} [ {} ]", object, t.getClass().getSimpleName(),
					t.getMessage(), t);

			et.rollback();
		}
	}
	
	private void doRemove(Object object) {
		em.remove(object);
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
			this.doMerge(object);
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
	
	private void doMerge(Object object) {
		em.merge(object);
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
	
	public void delete(Vme toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("Vme cannot be NULL");
	
		if (toDelete.getId() == null)
			throw new IllegalArgumentException("Vme ID cannot be NULL");
	
		if (toDelete.getRfmo() == null)
			throw new IllegalArgumentException("Vme cannot have a NULL parent RFMO");
		
		Rfmo parent = toDelete.getRfmo();
		
		Iterator<Vme> rfmoIterator = parent.getListOfManagedVmes().iterator();
		
		while(rfmoIterator.hasNext())
			if(rfmoIterator.next().getId().equals(toDelete.getId()))
				rfmoIterator.remove();
		
		this.doMerge(parent);
		
		if(toDelete.getProfileList() != null)
			for(Profile profile : toDelete.getProfileList())
				this.delete(profile);
		
		if(toDelete.getGeoRefList() != null)
			for(GeoRef geoRef : toDelete.getGeoRefList())
				this.delete(geoRef);
		
		if(toDelete.getSpecificMeasureList() != null)
			for(SpecificMeasure specificMeasure : toDelete.getSpecificMeasureList())
				this.delete(specificMeasure);
	}
	
	public void delete(InformationSource toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("InformationSource cannot be NULL");
	
		if (toDelete.getId() == null)
			throw new IllegalArgumentException("InformationSource ID cannot be NULL");
	
		if (toDelete.getRfmo() == null)
			throw new IllegalArgumentException("InformationSource cannot have a NULL parent RFMO");
		
		Rfmo parent = toDelete.getRfmo();
		
		Iterator<InformationSource> rfmoIterator = parent.getInformationSourceList().iterator();
		
		while(rfmoIterator.hasNext())
			if(rfmoIterator.next().getId().equals(toDelete.getId()))
				rfmoIterator.remove();
		
		this.doMerge(parent);
		
		for(GeneralMeasure generalMeasure : toDelete.getGeneralMeasureList()) {
			Iterator<InformationSource> generalMeasureIterator = generalMeasure.getInformationSourceList().iterator();
			
			while(generalMeasureIterator.hasNext())
				if(generalMeasureIterator.next().getId().equals(toDelete.getId()))
					generalMeasureIterator.remove();
			
			this.doMerge(generalMeasure);
		}
		
		for(SpecificMeasure specificMeasure : toDelete.getSpecificMeasureList()) {
			specificMeasure.setInformationSource(null);
			
			this.doMerge(specificMeasure);
		}
		
		this.doRemove(toDelete);
	}
	
	public void delete(VMEsHistory toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("VMEsHistory cannot be NULL");
	
		if (toDelete.getId() == null)
			throw new IllegalArgumentException("VMEsHistory ID cannot be NULL");
	
		if (toDelete.getRfmo() == null)
			throw new IllegalArgumentException("VMEsHistory cannot have a NULL parent RFMO");
		
		Rfmo parent = toDelete.getRfmo();
		
		Iterator<VMEsHistory> rfmoIterator = parent.getHasVmesHistory().iterator();
		
		while(rfmoIterator.hasNext())
			if(rfmoIterator.next().getId().equals(toDelete.getId()))
				rfmoIterator.remove();
		
		this.doMerge(parent);
		
		this.doRemove(toDelete);
	}
	
	public void delete(FisheryAreasHistory toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("FisheryAreasHistory cannot be NULL");
	
		if (toDelete.getId() == null)
			throw new IllegalArgumentException("FisheryAreasHistory ID cannot be NULL");
	
		if (toDelete.getRfmo() == null)
			throw new IllegalArgumentException("FisheryAreasHistory cannot have a NULL parent RFMO");
		
		Rfmo parent = toDelete.getRfmo();
		
		Iterator<FisheryAreasHistory> rfmoIterator = parent.getHasFisheryAreasHistory().iterator();
		
		while(rfmoIterator.hasNext())
			if(rfmoIterator.next().getId().equals(toDelete.getId()))
				rfmoIterator.remove();
		
		this.doMerge(parent);
		
		this.doRemove(toDelete);
	}
	
	//These should be used when deleting a VME...
	public void delete(Profile toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("Profile cannot be NULL");

		if (toDelete.getId() == null)
			throw new IllegalArgumentException("Profile ID cannot be NULL");

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

		while(iterator.hasNext())
			if(iterator.next().getId().equals(toDelete.getId()))
				iterator.remove();
		
		this.doMerge(parent);

		this.doRemove(toDelete);
	}

	public void delete(GeoRef toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("GeoRef cannot be NULL");

		if (toDelete.getId() == null)
			throw new IllegalArgumentException("GeoRef ID cannot be NULL");

		if (toDelete.getVme() == null)
			throw new IllegalArgumentException("GeoRef cannot have a NULL Vme reference");

		if (toDelete.getVme().getId() == null)
			throw new IllegalArgumentException("GeoRef cannot have a Vme reference with a NULL id");

		Vme parent = toDelete.getVme();
		
		if (parent.getProfileList() == null)
			throw new IllegalArgumentException("Profile cannot have a parent Vme with a NULL profile list");

		if (parent.getProfileList().isEmpty())
			throw new IllegalArgumentException("Profile cannot have a parent Vme with an empty profile list");

		Iterator<Profile> iterator = parent.getProfileList().iterator();

		while(iterator.hasNext())
			if(iterator.next().getId().equals(toDelete.getId()))
				iterator.remove();
		
		this.doMerge(parent);

		this.doRemove(toDelete);
	}
	
	public void delete(GeneralMeasure toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("GeneralMeasure cannot be NULL");
	
		if (toDelete.getId() == null)
			throw new IllegalArgumentException("GeneralMeasure ID cannot be NULL");
	
		if (toDelete.getRfmo() == null)
			throw new IllegalArgumentException("GeneralMeasure cannot have a NULL parent RFMO");
		
		Iterator<GeneralMeasure> rfmoIterator = toDelete.getRfmo().getGeneralMeasureList().iterator();
		
		while(rfmoIterator.hasNext())
			if(rfmoIterator.next().getId().equals(toDelete.getId()))
				rfmoIterator.remove();
		
		this.doMerge(toDelete.getRfmo());

		for(InformationSource informationSource : toDelete.getInformationSourceList()) {
			Iterator<GeneralMeasure> informationSourceIterator = informationSource.getGeneralMeasureList().iterator();
			
			while(informationSourceIterator.hasNext())
				if(informationSourceIterator.next().getId().equals(toDelete.getId()))
					informationSourceIterator.remove();

			this.doMerge(informationSource);
		}
		
		this.doRemove(toDelete);
	}
	
	public void delete(SpecificMeasure toDelete) {
		if (toDelete == null)
			throw new IllegalArgumentException("SpecificMeasure cannot be NULL");

		if (toDelete.getId() == null)
			throw new IllegalArgumentException("SpecificMeasure ID cannot be NULL");

		if (toDelete.getVmeList() == null)
			throw new IllegalArgumentException("SpecificMeasure cannot have a NULL set of Vme references");

		if (toDelete.getVmeList().isEmpty())
			throw new IllegalArgumentException("SpecificMeasure cannot have an empty set of Vme references");

		for(Vme vme : toDelete.getVmeList()) {
			Iterator<SpecificMeasure> vmeIterator = vme.getSpecificMeasureList().iterator();
			
			while(vmeIterator.hasNext())
				if(vmeIterator.next().getId().equals(toDelete.getId()))
					vmeIterator.remove();
			
			this.doMerge(vme);
		}
		
		if(toDelete.getInformationSource() != null) {
			Iterator<SpecificMeasure> informationSourceIterator = toDelete.getInformationSource().getSpecificMeasureList().iterator();
			
			while(informationSourceIterator.hasNext())
				if(informationSourceIterator.next().getId().equals(toDelete.getId()))
					informationSourceIterator.remove();
			
			this.doMerge(toDelete.getInformationSource());
		}
		
		this.doRemove(toDelete);
	}
}
