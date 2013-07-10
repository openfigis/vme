package org.fao.fi.figis.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.fao.fi.dao.Dao;
import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.VmeObservationPk;
import org.fao.fi.figis.domain.rule.DomainRule4ObservationXmlId;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.dao.config.FigisDB;

/**
 * The dao in order to dconnect to the Figis database. Connection details to be found in
 * /vme-configuration/src/main/resources/META-INF/persistence.xml
 * 
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

	/**
	 * persists the VmeObservationDomain, assuming the RefVme is pre existing
	 * 
	 * @param vod
	 */
	public void syncVmeObservationDomain(VmeObservationDomain vod) {
		// precondition

		if (vod.getRefVme().getId() == null) {
			throw new VmeException("FigisDao Exception, detected a non registered RefVme.");
		}

		// logic
		em.getTransaction().begin();
		List<ObservationDomain> oList = vod.getObservationDomainList();
		for (ObservationDomain od : oList) {

			// find VmeObservation
			VmeObservation vo = findVmeObservationByVme(vod.getRefVme().getId(), od.getReportingYear());
			if (vo == null) {
				// create VmeObservation plus the derived objects.
				persistObservationDomain(od, vod.getRefVme().getId());
			} else {
				// VmeObservation exists. sync it plus the derived objects.
			}

		}
		em.getTransaction().commit();
	}

	/**
	 * 
	 * @param od
	 * @param vmeId
	 */
	private void persistObservationDomain(ObservationDomain od, Long vmeId) {
		// first the observation needs to be persisted in order to get an id
		Observation o = new Observation(od);
		em.persist(o);

		// then a VmeObservation can be persisted.
		VmeObservation vo = new VmeObservation();
		VmeObservationPk pk = new VmeObservationPk();
		vo.setId(pk);
		pk.setObservationId(o.getId());
		pk.setVmeId(vmeId);
		pk.setReportingYear(od.getReportingYear());
		em.persist(vo);

		List<ObservationXml> xmlList = od.getObservationsPerLanguage();
		for (ObservationXml observationXml : xmlList) {
			DomainRule4ObservationXmlId rule = new DomainRule4ObservationXmlId();
			observationXml.setObservation(o);
			// genereate the id for the xml, based upon the id of the observation
			rule.composeId(observationXml);
			em.persist(observationXml);
		}
		o.setObservationsPerLanguage(xmlList);
		em.merge(o);

	}

	// /**
	// * This means removing all the observations related to a certain reference object
	// *
	// *
	// * @param vod
	// */
	// public void removeVmeObservationDomain(RefVme refVme) {
	// em.getTransaction().begin();
	// // TODO
	// // List<VmeObservation> l = findVmeObservationDomainByVme(refVme.getId());
	// // for (VmeObservation vmeObservation : l) {
	// // Observation observation = em.find(Observation.class, vmeObservation.getId().getObservationId());
	// // List<ObservationXml> xmlList = observation.getObservationsPerLanguage();
	// // for (ObservationXml observationXml : xmlList) {
	// // em.remove(observationXml);
	// // }
	// // em.remove(observation);
	// // em.remove(vmeObservation);
	// // }
	// // em.getTransaction().commit();
	// }

	public Long count(Class<?> clazz) {
		return count(em, clazz);
	}

	/**
	 * find the VmeObservationDomain by the observation id
	 * 
	 * 
	 * @param id
	 * @return
	 */
	// public VmeObservationDomain findVmeObservationDomain(Long observationId) {
	// VmeObservation vo = em.find(VmeObservation.class, observationId);
	//
	// VmeObservationDomain vod = vo2Vod(vo);
	//
	// RefVme refVme = em.find(RefVme.class, vo.getVmeId());
	// VmeObservationDomain vod = new VmeObservationDomain();
	// vod.setRefVme(refVme);
	// vod.setReportingYear(vo.getReportingYear());
	// Observation o = em.find(Observation.class, observationId);
	// List<Observation> observationList = new ArrayList<Observation>();
	// observationList.add(o);
	// vod.setObservationList(observationList);
	// return vod;
	// }

	// private VmeObservationDomain vo2Vod(VmeObservation vo) {
	//
	// RefVme refVme = em.find(RefVme.class, vo.getId().getVmeId());
	// VmeObservationDomain vod = new VmeObservationDomain();
	// vod.setRefVme(refVme);
	//
	// vod.setReportingYear(vo.getId().getReportingYear());
	//
	// Observation o = em.find(Observation.class, vo.getId().getObservationId());
	// List<ObservationDomain> observationList = new ArrayList<ObservationDomain>();
	//
	// observationList.add(o);
	// vod.setObservationList(observationList);
	// return vod;
	// }

	/**
	 * find the VmeObservationDomain by the vme id and reporting year.
	 * 
	 * @param id
	 * @return
	 */
	public VmeObservation findVmeObservationByVme(Long vmeId, String reportingYear) {
		Query query = em
				.createQuery("select vo FROM VmeObservation vo where vo.id.vmeId = :vmeId and vo.id.reportingYear = :reportingYear");
		query.setParameter("vmeId", vmeId);
		query.setParameter("reportingYear", reportingYear);
		VmeObservation vo = null;
		try {
			vo = (VmeObservation) query.getSingleResult();
		} catch (NoResultException e) {
			// TODO change this, detect in another way upfront whether the object exist, for instance by calculating the
			// number of objects.
		}
		return vo;
	}

	/**
	 * find the VmeObservationDomain by the vme id and reporting year.
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public VmeObservationDomain findVmeObservationDomainByVme(Long vmeId) {
		Query query = em.createQuery("select vo FROM VmeObservation vo where vo.id.vmeId = :vmeId ");
		query.setParameter("vmeId", vmeId);
		List<VmeObservation> voList = query.getResultList();
		for (VmeObservation vo : voList) {
			ObservationDomain od = new ObservationDomain();
			od.setReportingYear(vo.getId().getReportingYear());
			od.setId(vo.getId().getObservationId());
		}
		VmeObservationDomain vod = new VmeObservationDomain();

		return vod;
	}
}
