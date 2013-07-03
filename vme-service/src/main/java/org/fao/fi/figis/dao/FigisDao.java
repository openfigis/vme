package org.fao.fi.figis.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import org.fao.fi.dao.Dao;
import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.rule.DomainRule4ObservationXmlId;
import org.fao.fi.vme.dao.config.FigisDB;
import org.fao.fi.vme.msaccess.component.VmeDaoException;

/**
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */

@Singleton
public class FigisDao extends Dao {

	@Inject
	@FigisDB
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<RefVme> loadRefVmes() {
		return (List<RefVme>) this.generateTypedQuery(em, RefVme.class).getResultList();
	}

	public List<?> loadObjects(Class<?> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

	public Object find(Class<?> clazz, Object id) {
		return em.find(clazz, id);
	}

	public void merge(Object object) {
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();
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

	/**
	 * 
	 * 
	 * 
	 * @param r
	 *            the object to be synced
	 */
	public void syncRefVme(RefVme r) {
		RefVme found = em.find(RefVme.class, r.getId());
		if (found == null) {
			this.persist(r);
		} else {
			this.merge(r);
		}
	}

	public void syncVmeObservation(VmeObservation vo) {
		VmeObservation found = em.find(VmeObservation.class, vo.getObservationId());
		if (found == null) {
			this.persist(vo);
		} else {
			this.merge(vo);
		}

	}

	/**
	 * persists the VmeObservationDomain, assuming the RefVme is pre existing
	 * 
	 * @param vod
	 */
	public void persistVmeObservationDomain(VmeObservationDomain vod) {
		// precondition

		if (vod.getRefVme().getId() == null) {
			throw new VmeDaoException("FigisDao Exception, assuming the RefVme is not registered yet. ");
		}

		// logic
		em.getTransaction().begin();
		List<Observation> oList = vod.getObservationList();
		for (Observation observation : oList) {
			// there should be not yet an id assigned
			if (observation.getId() != 0) {
				throw new VmeDaoException("FigisDao Exception, assuming the RefVme is not registered yet. ");
			}
			em.persist(observation);
			VmeObservation vo = new VmeObservation();
			vo.setObservationId(observation.getId());
			vo.setVmeId(vod.getRefVme().getId());
			vo.setReportingYear(vod.getReportingYear());
			em.persist(vo);
			List<ObservationXml> xmlList = observation.getObservationsPerLanguage();
			for (ObservationXml observationXml : xmlList) {
				DomainRule4ObservationXmlId rule = new DomainRule4ObservationXmlId();
				observationXml.setObservation(observation);
				// genereate the id for the xml, based upon the id of the observation
				rule.composeId(observationXml);
				em.persist(observationXml);
			}
		}
		em.getTransaction().commit();
	}

	public void removeVmeObservationDomain(VmeObservationDomain vod) {
		em.getTransaction().begin();
		List<Observation> oList = vod.getObservationList();
		for (Observation observation : oList) {
			List<ObservationXml> xmlList = observation.getObservationsPerLanguage();
			for (ObservationXml observationXml : xmlList) {
				em.remove(observationXml);
			}
			em.remove(observation);
			Long id = new Long(observation.getId());
			em.remove(em.find(VmeObservation.class, id));
		}
		em.getTransaction().commit();
	}
}
