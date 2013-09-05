package org.fao.fi.vme.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.dao.Dao;
import org.fao.fi.vme.dao.config.VmeDB;
import org.fao.fi.vme.domain.GeneralMeasure;
import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.History;
import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.domain.Vme;

/**
 * * The dao in order to dconnect to the vme database. Connection details to be found in
 * /vme-configuration/src/main/resources/META_VME-INF/persistence.xml
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeDao extends Dao {

	@Inject
	@VmeDB
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@SuppressWarnings("unchecked")
	public List<Vme> loadVmes() {

		return (List<Vme>) this.generateTypedQuery(em, Vme.class).getResultList();
	}

	public List<?> loadObjects(Class<?> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

	public Vme findVme(Long id) {
		return em.find(Vme.class, id);
	}

	public void persist(Object object) {
		em.getTransaction().begin();
		em.persist(object);
		em.getTransaction().commit();
	}

	public void remove(Object object) {
		em.getTransaction().begin();
		em.remove(object);
		em.getTransaction().commit();
	}

	public void merge(Object object) {
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();
	}

	public void saveVme(Vme vme) {

		em.getTransaction().begin();
		for (GeneralMeasure o : vme.getRfmo().getGeneralMeasureList()) {
			em.persist(o);
		}

		for (History h : vme.getRfmo().getHasFisheryAreasHistory()) {
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
		em.getTransaction().commit();

	}

	public Long count(Class<?> clazz) {
		return count(em, clazz);
	}
}
