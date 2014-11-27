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
 * * The dao in order to dconnect to the vme database. Connection details to be found in
 * /vme-configuration/src/main/resources/META_VME-INF/persistence.xml
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
		return this.generateTypedQuery(em, Vme.class).getResultList();
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

		et.commit();

		LOG.debug("Vme {} has been saved into persistence", vme);

		return vme;
	}

	public <E> List<E> loadObjects(Class<E> clazz) {
		System.out.println(em);
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

	public Vme update(Vme vmeDto) throws Throwable {

		if (vmeDto == null) {
			throw new IllegalArgumentException("Updated Vme cannot be NULL");
		}

		if (vmeDto.getId() == null) {
			throw new IllegalArgumentException("Updated Vme cannot have a NULL identifier");
		}

		if (vmeDto.getRfmo() == null) {
			throw new IllegalArgumentException("Updated Vme cannot have a NULL authority");
		}

		if (vmeDto.getRfmo().getId() == null) {
			throw new IllegalArgumentException("Updated Vme cannot have a Rfmo with a NULL identifier");
		}
		Vme vmeManaged = em.find(Vme.class, vmeDto.getId());
		if (vmeManaged.getSpecificMeasureList() != null) {
			logggg("before Update1nCardinality vmeManaged", vmeManaged.getSpecificMeasureList());
		}

		Update1nCardinality u1n = new Update1nCardinality();
		u1n.update(em, vmeManaged, vmeDto.getGeoRefList(), vmeManaged.getGeoRefList());
		u1n.update(em, vmeManaged, vmeDto.getMediaReferenceList(), vmeManaged.getMediaReferenceList());
		u1n.update(em, vmeManaged, vmeDto.getProfileList(), vmeManaged.getProfileList());

		if (vmeDto.getSpecificMeasureList() != null) {
			logggg("vmeDto", vmeDto.getSpecificMeasureList());
		}
		if (vmeManaged.getSpecificMeasureList() != null) {
			logggg("vmeManaged", vmeManaged.getSpecificMeasureList());
		}
		WorkAroundSpecificMeasureFilter w = new WorkAroundSpecificMeasureFilter();
		List<SpecificMeasure> filtered = w.filter(vmeManaged.getSpecificMeasureList());
		vmeManaged.setSpecificMeasureList(filtered);
		logggg("vmeManaged filtered", filtered);

		u1n.update(em, vmeManaged, vmeDto.getSpecificMeasureList(), filtered);

		u.copyMultiLingual(vmeDto, vmeManaged);
		vmeManaged.setAreaType(vmeDto.getAreaType());
		vmeManaged.setCriteria(vmeDto.getCriteria());
		vmeManaged.setInventoryIdentifier(vmeDto.getInventoryIdentifier());
		vmeManaged.setScope(vmeDto.getScope());
		vmeManaged.setValidityPeriod(vmeDto.getValidityPeriod());

		vmeManaged = em.merge(vmeManaged);
		return vmeManaged;
	}

	private void logggg(String type, List<SpecificMeasure> specificMeasureList) {
		if (specificMeasureList != null) {
			LOG.info(type + ".getSpecificMeasureList().size = " + specificMeasureList.size());
			for (SpecificMeasure specificMeasure : specificMeasureList) {
				LOG.info("id = " + specificMeasure.getId());
			}
		}
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

		em.persist(vme);
		return vme;
	}

	public GeoRef update(GeoRef geoRefDto) {

		if (geoRefDto == null) {
			throw new IllegalArgumentException("The updated VME Map Reference cannot be NULL");
		}

		if (geoRefDto.getId() == null) {
			throw new IllegalArgumentException("The updated VME Map Reference cannot have a NULL identifier");
		}

		if (geoRefDto.getVme() == null) {
			throw new IllegalArgumentException("The updated VME Map Reference cannot have a NULL Vme");
		}

		if (geoRefDto.getVme().getId() == null) {
			throw new IllegalArgumentException("The updated VME Map Reference cannot have a NULL Vme identifier");
		}

		GeoRef geoRefEm = em.find(GeoRef.class, geoRefDto.getId());
		geoRefEm.setGeographicFeatureID(geoRefDto.getGeographicFeatureID());
		geoRefEm.setYear(geoRefDto.getYear());
		em.merge(geoRefEm);
		return geoRefEm;
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

	public Profile update(Profile profileDto) {

		if (profileDto == null) {
			throw new IllegalArgumentException("The updated VME Profile cannot be NULL");
		}

		if (profileDto.getId() == null) {
			throw new IllegalArgumentException("The updated VME Profile cannot have a NULL identifier");
		}

		if (profileDto.getVme() == null) {
			throw new IllegalArgumentException("The updated VME Profile cannot have a NULL Vme");
		}

		if (profileDto.getVme().getId() == null) {
			throw new IllegalArgumentException("The updated VME Profile cannot have a NULL Vme identifier");
		}

		Profile profileEm = em.find(Profile.class, profileDto.getId());
		Vme vmeEm = em.find(Vme.class, profileDto.getVme().getId());
		u.copyMultiLingual(profileDto, profileEm);
		profileEm.setYear(profileDto.getYear());
		profileEm.setVme(vmeEm);
		em.merge(profileEm);
		return profileEm;
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

	public MediaReference update(MediaReference mediaDto) {

		if (mediaDto == null) {
			throw new IllegalArgumentException("The updated VME media cannot be NULL");
		}

		if (mediaDto.getId() == null) {
			throw new IllegalArgumentException("The updated VME media cannot have a NULL identifier");
		}

		if (mediaDto.getVme() == null) {
			throw new IllegalArgumentException("The updated VME media cannot have a NULL Vme");
		}

		if (mediaDto.getVme().getId() == null) {
			throw new IllegalArgumentException("The updated VME media cannot have a NULL Vme identifier");
		}

		MediaReference mediaEm = em.find(MediaReference.class, mediaDto.getId());
		Vme vmeEm = em.find(Vme.class, mediaDto.getVme().getId());
		u.copyMultiLingual(mediaDto, mediaEm);
		mediaEm.setType(mediaDto.getType());
		mediaEm.setUrl(mediaDto.getUrl());
		mediaEm.setVme(vmeEm);
		em.merge(mediaEm);
		return mediaEm;
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
			throw new IllegalArgumentException(
					"Media reference cannot have a parent Vme with a NULL media reference list");
		}

		if (parent.getMediaReferenceList().isEmpty()) {
			throw new IllegalArgumentException(
					"Media reference cannot have a parent Vme with an empty media reference list");
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
			InformationSource existing = em.find(InformationSource.class, specificMeasure.getInformationSource()
					.getId());
			specificMeasure.setInformationSource(existing);
			existing.getSpecificMeasureList().add(specificMeasure);
		}

		return this.doPersistAndFlush(em, specificMeasure);
	}

	public FisheryAreasHistory update(FisheryAreasHistory fisheryAreasHistoryDto) throws Throwable {

		if (fisheryAreasHistoryDto == null) {
			throw new IllegalArgumentException("The updated Fishing Footprint cannot be NULL");
		}

		if (fisheryAreasHistoryDto.getId() == null) {
			throw new IllegalArgumentException("The updated Fishing Footprint cannot have a NULL identifier");
		}

		if (fisheryAreasHistoryDto.getRfmo() == null) {
			throw new IllegalArgumentException("The updated Fishing Footprint cannot have a NULL Authority");
		}

		if (fisheryAreasHistoryDto.getRfmo().getId() == null) {
			throw new IllegalArgumentException(
					"The updated Fishing Footprint cannot have an Authority with a NULL identifier");
		}

		Rfmo rfmoEm = em.find(Rfmo.class, fisheryAreasHistoryDto.getRfmo().getId());
		FisheryAreasHistory fisheryAreasHistoryEm = em.find(FisheryAreasHistory.class, fisheryAreasHistoryDto.getId());
		u.copyMultiLingual(fisheryAreasHistoryDto, fisheryAreasHistoryEm);
		fisheryAreasHistoryEm.setRfmo(rfmoEm);
		fisheryAreasHistoryEm.setYear(fisheryAreasHistoryDto.getYear());
		em.merge(fisheryAreasHistoryEm);
		return fisheryAreasHistoryEm;
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

		return this.doPersistAndFlush(em, fisheryAreasHistory);
	}

	public VMEsHistory update(VMEsHistory vmesHistoryDto) throws Throwable {

		if (vmesHistoryDto == null) {
			throw new IllegalArgumentException("The updated Regional History of VME cannot be NULL");
		}

		if (vmesHistoryDto.getId() == null) {
			throw new IllegalArgumentException("The updated Regional History of VME cannot have a NULL identifier");
		}

		if (vmesHistoryDto.getRfmo() == null) {
			throw new IllegalArgumentException("The updated Regional History of VME cannot have a NULL Authority");
		}

		if (vmesHistoryDto.getRfmo().getId() == null) {
			throw new IllegalArgumentException(
					"The updated Regional History of VME cannot have an Authority with a NULL identifier");
		}

		Rfmo rfmoEm = em.find(Rfmo.class, vmesHistoryDto.getRfmo().getId());
		VMEsHistory vmesHistoryEm = em.find(VMEsHistory.class, vmesHistoryDto.getId());
		vmesHistoryEm.setRfmo(rfmoEm);
		u.copyMultiLingual(vmesHistoryDto, vmesHistoryEm);
		vmesHistoryEm.setYear(vmesHistoryDto.getYear());
		em.merge(vmesHistoryEm);
		return vmesHistoryEm;
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

		InformationSource current = this.em.find(InformationSource.class, informationSource.getId());
		u.copyMultiLingual(informationSource, current);
		current.setSourceType(informationSource.getSourceType());
		current.setMeetingEndDate(informationSource.getMeetingEndDate());
		current.setMeetingStartDate(informationSource.getMeetingStartDate());
		current.setPublicationYear(informationSource.getPublicationYear());
		current.setUrl(informationSource.getUrl());

		current.setRfmo(this.em.find(Rfmo.class, informationSource.getRfmo().getId()));

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

		Rfmo parent = em.find(Rfmo.class, informationSource.getRfmo().getId());
		informationSource.setRfmo(parent);

		return this.doPersistAndFlush(em, informationSource);
	}

	/**
	 * This method does not foresee a change in the related Vme
	 * 
	 * @param specificMeasureDto
	 * @return SpecificMeasure
	 * @throws Throwable
	 */
	public SpecificMeasure update(SpecificMeasure specificMeasureDto) throws Throwable {

		if (specificMeasureDto == null) {
			throw new IllegalArgumentException("The updated VME Specific Measure cannot be NULL");
		}

		if (specificMeasureDto.getId() == null) {
			throw new IllegalArgumentException("The updated VME Specific Measure cannot have a NULL identifier");
		}

		if (specificMeasureDto.getVme() == null) {
			throw new IllegalArgumentException("The updated VME Specific Measure cannot have a NULL Vme reference");
		}

		if (specificMeasureDto.getVme().getId() == null) {
			throw new IllegalArgumentException(
					"The updated VME Specific Measure cannot have a Vme reference with a NULL identifier");
		}

		SpecificMeasure specificMeasureEm = em.find(SpecificMeasure.class, specificMeasureDto.getId());
		InformationSource informationSourceEm = null;

		if (specificMeasureDto.getInformationSource() == null) {
			specificMeasureEm.setInformationSource(null);
		} else {
			informationSourceEm = em.find(InformationSource.class, specificMeasureDto.getInformationSource().getId());
		}

		u.copyMultiLingual(specificMeasureDto, specificMeasureEm);
		specificMeasureEm.setValidityPeriod(specificMeasureDto.getValidityPeriod());
		specificMeasureEm.setYear(specificMeasureDto.getYear());
		specificMeasureEm.setReviewYear(specificMeasureDto.getReviewYear());

		if (specificMeasureEm.getInformationSource() != null
				&& specificMeasureDto.getInformationSource() != null
				&& !specificMeasureEm.getInformationSource().getId()
						.equals(specificMeasureDto.getInformationSource().getId())) {
			// the informationsource has been changed
			InformationSource oldInformationSource = specificMeasureEm.getInformationSource();
			oldInformationSource.getSpecificMeasureList().remove(specificMeasureEm);
			em.merge(oldInformationSource);

			specificMeasureEm.setInformationSource(informationSourceEm);
			specificMeasureEm.getInformationSource().getSpecificMeasureList().add(specificMeasureEm);
			em.merge(informationSourceEm);
		}

		if (specificMeasureEm.getInformationSource() != null && specificMeasureDto.getInformationSource() == null) {
			// new to null
			specificMeasureEm.getInformationSource().getSpecificMeasureList().remove(specificMeasureEm);
			em.merge(specificMeasureEm.getInformationSource());
			specificMeasureEm.setInformationSource(null);
		}

		if (specificMeasureEm.getInformationSource() == null && specificMeasureDto.getInformationSource() != null) {
			// null to new
			// new information source on the specific measure
			specificMeasureEm.setInformationSource(informationSourceEm);
			if (informationSourceEm.getSpecificMeasureList() == null) {
				informationSourceEm.setSpecificMeasureList(new ArrayList<SpecificMeasure>());
			}
			informationSourceEm.getSpecificMeasureList().add(specificMeasureEm);
			em.merge(informationSourceEm);
		}

		em.merge(specificMeasureEm);
		return specificMeasureEm;
	}

	/**
	 * The generalMeasure here needs to be considered as a DTO, not as a domain object.
	 * 
	 * 
	 * @param generalMeasureDto
	 * @return
	 * @throws Throwable
	 */
	public GeneralMeasure update(GeneralMeasure generalMeasureDto) throws Throwable {

		LOG.info("EntityManager VmeDao" + em);

		if (generalMeasureDto == null) {
			throw new IllegalArgumentException("The updated VME General Measure cannot be NULL");
		}

		if (generalMeasureDto.getId() == null) {
			throw new IllegalArgumentException("The updated VME General Measure cannot have a NULL identifier");
		}

		GeneralMeasure generalMeasureEm = em.find(GeneralMeasure.class, generalMeasureDto.getId());
		u.copyMultiLingual(generalMeasureDto, generalMeasureEm);
		generalMeasureEm.setValidityPeriod(generalMeasureDto.getValidityPeriod());
		generalMeasureEm.setYear(generalMeasureDto.getYear());

		Set<Long> informationSourceEmIds = new HashSet<Long>();
		List<InformationSource> toBeDeletedEm = new ArrayList<InformationSource>();

		if (generalMeasureEm.getInformationSourceList() != null) {
			// create list of current informationSources
			if (generalMeasureEm.getInformationSourceList() != null) {
				for (InformationSource informationSourceEm : generalMeasureEm.getInformationSourceList()) {
					informationSourceEmIds.add(informationSourceEm.getId());
					toBeDeletedEm.add(informationSourceEm);
				}
			}
		}

		if (generalMeasureDto.getInformationSourceList() != null) {
			Iterator<InformationSource> isIterator = generalMeasureDto.getInformationSourceList().iterator();
			while (isIterator.hasNext()) {
				InformationSource informationSourceDto = isIterator.next();
				InformationSource informationSourceEm = em.find(InformationSource.class, informationSourceDto.getId());

				// toBeDeleted can be adjusted also in this loop, by deleting all those ones which do not need to be
				// deleted.
				toBeDeletedEm.remove(informationSourceEm);

				// make sure it has a list, even an empty one
				if (informationSourceEm.getGeneralMeasureList() == null) {
					informationSourceEm.setGeneralMeasureList(new ArrayList<GeneralMeasure>());
				}

				// if the currentIds do not have the id, it means it is a new one. In the sense that it is a new in this
				// list, but it already exists because it had been created before. Here we only detect it as a new
				// reference.
				if (!informationSourceEmIds.contains(informationSourceDto.getId())) {
					// this is a new InformationSource for this General Measure!
					// make sure the bidirectional relation is respected
					generalMeasureEm.getInformationSourceList().add(informationSourceEm);
					informationSourceEm.getGeneralMeasureList().add(generalMeasureEm);
					// this.doMerge(em, informationSourceEm); this should not be necessary, since it is cascaded?
				}
			}
		}

		for (InformationSource delete : toBeDeletedEm) {
			// make sure the bidirectional relation is respected
			delete.getGeneralMeasureList().remove(generalMeasureEm);

			// after Found shared references to a collection exceptions, adding this merge:
			em.merge(delete);

			generalMeasureEm.getInformationSourceList().remove(delete);
		}

		generalMeasureEm = em.merge(generalMeasureEm);
		// Flush during cascade is dangerous, but it needs to be done anyway in order to make sure that changes are
		// reflected. I now have removed concurrent issues in the code above. Hopefully this allows me to do flushes (at
		// any time)?
		// em.flush();
		return generalMeasureEm;
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
		Rfmo rfmoEm = em.find(Rfmo.class, generalMeasure.getRfmo().getId());
		generalMeasure.setRfmo(rfmoEm);
		em.persist(generalMeasure);

		return generalMeasure;

	}
}
