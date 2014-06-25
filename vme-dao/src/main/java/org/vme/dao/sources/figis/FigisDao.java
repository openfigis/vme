package org.vme.dao.sources.figis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.RefWaterArea;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.VmeObservationPk;
import org.fao.fi.figis.domain.rule.DomainRule4ObservationXmlId;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.vme.VmeException;
import org.vme.dao.config.figis.FigisDB;
import org.vme.dao.impl.AbstractJPADao;

/**
 * The dao in order to dconnect to the Figis database. Connection details to be
 * found in /vme-configuration/src/main/resources/META_VME-INF/persistence.xml
 * 
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */

@Singleton
public class FigisDao extends AbstractJPADao {

	// private final static Logger logger =
	// LoggerFactory.getLogger(FigisDao.class);
	private DomainRule4ObservationXmlId rule = new DomainRule4ObservationXmlId();
	private PrimaryRuleValidator v = new PrimaryRuleValidator();

	/**
	 * Hi Fabrizio,
	 * 
	 * Water area refs for VME are loaded in RTMS from the Access DB, in the
	 * future from iMarine. To be sure that water area refs coming from VME, are
	 * consistently loaded in devel, fiqa and prod, I propose to have a reserved
	 * range from 6000-8000.
	 * 
	 * select max(cd_water_area) from ref_water_area --5075
	 * 
	 * What do you think?
	 * 
	 * Kind Regards, Erik
	 */
	public static final int START_WATER_AREA_REF = 60000;
	public static final int END_WATER_AREA_REF = 80000;

	@Inject
	@FigisDB
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public List<RefVme> loadRefVmes() {
		return (List<RefVme>) this.generateTypedQuery(em, RefVme.class).getResultList();
	}

	public <E> List<E> loadObjects(Class<E> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

	public Object find(Class<?> clazz, Object id) {
		return em.find(clazz, id);
	}

	public void merge(Object object) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.merge(object);
		t.commit();
	}

	public void persist(Object object) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(object);
		t.commit();
	}

	public void remove(Object object) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.remove(object);
		t.commit();
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
	 * Persists the VmeObservationDomain, assuming the RefVme is pre existing
	 * 
	 * TODO There is a problem here. In case a obs for a certain year was
	 * created in a previous run but not needs to be created anymore, it needs
	 * to be deleted.
	 * 
	 * 
	 * @param vod
	 */
	public void syncVmeObservationDomain(VmeObservationDomain vod) {
		// precondition

		if (vod.getRefVme().getId() == null) {
			throw new VmeException("FigisDao Exception, detected a non registered RefVme.");
		}
		v.validate(vod);

		EntityTransaction t = em.getTransaction();
		t.begin();

		// logic
		List<ObservationDomain> oList = vod.getObservationDomainList();

		for (ObservationDomain od : oList) {

			// find VmeObservation
			VmeObservation vo = findVmeObservationByVme(vod.getRefVme().getId(),
					Integer.parseInt(od.getReportingYear()));
			if (vo == null) {
				// create VmeObservation plus the derived objects.
				persistObservationDomain(od, vod.getRefVme().getId());
			} else {
				// VmeObservation exists. sync it plus the derived objects.
				updateObservationDomain(od, vo.getId().getObservationId());
			}

		}
		cleanOutdated(vod);
		t.commit();
	}

	private void cleanOutdated(VmeObservationDomain vod) {
		Set<String> years = new HashSet<String>();
		// find the valid years
		for (ObservationDomain o : vod.getObservationDomainList()) {
			years.add(o.getReportingYear());
		}
		// get all years
		List<VmeObservation> l = findVmeObservationByVme(vod.getRefVme().getId());
		for (VmeObservation vmeObservation : l) {
			if (!years.contains(vmeObservation.getId().getReportingYear())) {
				// if the year is out dated, remove it
				em.remove(vmeObservation);
				Observation o = em.find(Observation.class, vmeObservation.getId().getObservationId());
				List<ObservationXml> xs = o.getObservationsPerLanguage();
				for (ObservationXml observationXml : xs) {
					em.remove(observationXml);
				}
				em.remove(o);
			}
		}
	}

	/**
	 * The observation may need to be updated or needs to be inserted because it
	 * is new.
	 * 
	 * 
	 * @param od
	 * @param vmeId
	 */
	private void updateObservationDomain(ObservationDomain od, Long observationId) {

		Observation o = em.find(Observation.class, observationId);

		if (o == null) {
			// it can be a new observation, which first needs to be stored.
			o = new Observation(od);
			em.persist(o);
		}
		o.setCollection(od.getCollection());
		o.setObservationsPerLanguage(od.getObservationsPerLanguage());
		o.setOrder(od.getOrder());
		o.setPrimary(od.isPrimary());

		List<ObservationXml> xmlList = od.getObservationsPerLanguage();
		for (ObservationXml xml : xmlList) {
			xml.setObservation(o);
			rule.composeId(xml);
			ObservationXml xmlFound = em.find(ObservationXml.class, xml.getId());
			if (xmlFound == null) {
				xml.setObservation(o);
				// genereate the id for the xml, based upon the id of the
				// observation
				rule.composeId(xml);
				em.persist(xml);
			} else {
				xmlFound.setCreationDate(xml.getCreationDate());
				xmlFound.setLanguage(xml.getLanguage());
				xmlFound.setLastEditDate(xml.getLastEditDate());
				xmlFound.setObservation(xml.getObservation());
				xmlFound.setStatus(xml.getStatus());
				xmlFound.setXml(xml.getXml());
				em.merge(xmlFound);
			}
		}
		o.setObservationsPerLanguage(xmlList);
		em.merge(o);
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
		od.setId(o.getId());

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
			observationXml.setObservation(o);
			// genereate the id for the xml, based upon the id of the
			// observation
			rule.composeId(observationXml);
			em.persist(observationXml);
		}
		o.setObservationsPerLanguage(xmlList);
		em.merge(o);

	}

	public Long count(Class<?> clazz) {
		return count(em, clazz);
	}

	/**
	 * find the VmeObservation by the vme id and reporting year.
	 * 
	 * @param id
	 * @return
	 */
	public VmeObservation findVmeObservationByVme(Long vmeId, int reportingYear) {
		Query query = em
				.createQuery("select vo from VmeObservation vo where vo.id.vmeId = :vmeId and vo.id.reportingYear = :reportingYear");
		query.setParameter("vmeId", vmeId);
		query.setParameter("reportingYear", Integer.toString(reportingYear));
		VmeObservation vo = null;
		if (query.getResultList().size() == 1) {
			vo = (VmeObservation) query.getSingleResult();
		}
		return vo;
	}

	/**
	 * find the VmeObservation by the vme id and reporting year.
	 * 
	 * @param id
	 * @return
	 */
	public VmeObservation findFirstVmeObservation(Long vmeId, String year) {
		Query query = em
				.createQuery("select vo from VmeObservation vo where vo.id.vmeId = :vmeId and vo.id.reportingYear <= :year order by vo.id.reportingYear desc");
		query.setParameter("vmeId", vmeId);
		query.setParameter("year", year);
		VmeObservation vo = null;
		List<?> list = query.getResultList();
		if (list.size() > 0) {
			vo = (VmeObservation) list.get(0);
		}
		return vo;
	}

	/**
	 * find the VmeObservation by the vme id
	 * 
	 * Wrong, you can find more VmeObservation by the vmeId
	 * 
	 * 
	 * 
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VmeObservation> findVmeObservationByVme(Long vmeId) {
		Query query = em.createQuery("select vo from VmeObservation vo where vo.id.vmeId = :vmeId ");
		query.setParameter("vmeId", vmeId);
		List<VmeObservation> r = null;
		r = query.getResultList();

		return r;
	}

	public VmeObservationDomain findVod(Long vmeId) {
		VmeObservationDomain vod = new VmeObservationDomain();
		List<ObservationDomain> vodList = new ArrayList<ObservationDomain>();
		List<VmeObservation> vmeObservationList = findVmeObservationByVme(vmeId);
		for (VmeObservation vmeObservation : vmeObservationList) {
			Observation observation = em.find(Observation.class, vmeObservation.getId().getObservationId());
			String reportingYear = vmeObservation.getId().getReportingYear();
			ObservationXml xml = findEnglishXml(vmeObservation.getId().getObservationId());
			List<ObservationXml> observationsPerLanguage = new ArrayList<ObservationXml>();
			observationsPerLanguage.add(xml);
			ObservationDomain od = new ObservationDomain(observation, reportingYear, observationsPerLanguage);
			vodList.add(od);
		}
		RefVme refVme = em.find(RefVme.class, vmeId);
		vod.setRefVme(refVme);
		vod.setObservationDomainList(vodList);
		return vod;
	}

	/**
	 * This one removes the whole vme, except for the reference data.
	 * 
	 * This only works that a Vme in FIGIS consists of a VmeObservation, one or
	 * more Observation(s) and one or more ObaservationXml(s)
	 * 
	 * 
	 * @param vmeId
	 */
	public void removeVme(Long vmeId) {
		em.getTransaction().begin();
		List<VmeObservation> voList = findVmeObservationByVme(vmeId);
		for (VmeObservation vo : voList) {
			Observation o = (Observation) this.find(Observation.class, vo.getId().getObservationId());
			ObservationXml xmlFound = findEnglishXml(o.getId());
			if (xmlFound != null) {
				em.remove(xmlFound);
				em.remove(o);
				em.remove(vo);
			}
		}
		em.getTransaction().commit();

	}

	private ObservationXml findEnglishXml(Long id) {
		DomainRule4ObservationXmlId rule = new DomainRule4ObservationXmlId();
		String xmlId = rule.composeId(id, Figis.EN);
		ObservationXml xmlFound = (ObservationXml) this.find(ObservationXml.class, xmlId);
		return xmlFound;
	}

	public void syncRefWaterArea(String externalId, RefWaterArea refWaterArea) {

		if (refWaterArea.getExternalId() == null) {
			throw new VmeException("The external id of refWaterArea is null, not correct. ");
		}

		EntityTransaction t = em.getTransaction();
		t.begin();

		String queryString2 = " select r from RefWaterArea r where r.externalId = :externalId ";
		Query query = em.createQuery(queryString2);
		query.setParameter("externalId", externalId);
		RefWaterArea found = null;
		try {
			if (query.getResultList().size() == 1) {
				found = (RefWaterArea) query.getSingleResult();
			}
		} catch (NonUniqueResultException e) {
			// elements are not unique!
			throw new VmeException(externalId + " is not unique, data is not consisten!");
		}
		if (found != null) {
			found.setName(refWaterArea.getName());
			em.merge(found);
		} else {
			String queryString1 = " select max(id) from RefWaterArea  where id >= " + START_WATER_AREA_REF
					+ " and id <= " + END_WATER_AREA_REF;
			Object object = em.createQuery(queryString1).getSingleResult();
			if (object == null) {
				refWaterArea.setId(START_WATER_AREA_REF);
			} else {
				long l = ((Long) object).intValue();
				l++;
				refWaterArea.setId(l);
			}
			em.persist(refWaterArea);
		}
		t.commit();
	}
}
