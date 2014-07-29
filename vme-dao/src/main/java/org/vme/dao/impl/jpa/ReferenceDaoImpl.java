/**
 * 
 */
package org.vme.dao.impl.jpa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.annotations.ReferenceConceptName;
import org.fao.fi.vme.domain.dto.ref.ReferenceYear;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.reference.InformationSourceType;
import org.fao.fi.vme.domain.model.reference.VmeCriteria;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.fao.fi.vme.domain.model.reference.VmeType;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.AcronymAwareReferenceConcept;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.ReferenceConcept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.ReferenceDAO;
import org.vme.dao.ReferenceServiceException;
import org.vme.dao.config.vme.VmeDB;

/**
 * @author Fabrizio Sibeni
 * 
 */
@ConceptProvider
@Alternative
public class ReferenceDaoImpl implements ReferenceDAO {
	
	private static final String UNDEFINED = "Undefined reference concept";
	private static final String INTERNALERR = "Internal error";
	
	protected static final Logger LOG = LoggerFactory.getLogger(ReferenceDaoImpl.class);

	@VmeDB
	@Inject
	private EntityManager em;

	private Map<Long, ReferenceYear> repYear;

	public ReferenceDaoImpl() {
		this.concepts = new ArrayList<Class<? extends ReferenceConcept>>();

		this.concepts.add(Authority.class);
		this.concepts.add(VmeCriteria.class);
		this.concepts.add(VmeType.class);
		this.concepts.add(ReferenceYear.class);
		this.concepts.add(InformationSourceType.class);
		this.concepts.add(VmeScope.class);
		repYear = this.createYears();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vme.service.reference.ReferenceService#getReference(java.lang.Class,
	 * java.lang.Long)
	 */
	@Override
	public ReferenceConcept getReference(Class<? extends ReferenceConcept> concept, Long id)
			throws ReferenceServiceException {
		if (concept.equals(Authority.class)) {
			return getAuthority(id);
		} else if (concept.equals(VmeCriteria.class)) {
			return getVmeCriteria(id);
		} else if (concept.equals(VmeType.class)) {
			return getVmeType(id);
		} else if (concept.equals(InformationSourceType.class)) {
			return this.getInformationSourceType(id);
		} else if (concept.equals(ReferenceYear.class)) {
			return getYear(id);
		} else if (concept.equals(VmeScope.class)) {
			return getVmeScope(id);
		} else {
			throw new ReferenceServiceException(UNDEFINED);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vme.service.reference.ReferenceService#getAllReferences(java.lang
	 * .Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <R extends ReferenceConcept> List<R> getAllReferences(Class<R> concept) throws ReferenceServiceException {
		if (concept.equals(Authority.class)) {
			return (List<R>) getAllAuthorities();
		} else if (concept.equals(VmeCriteria.class)) {
			return (List<R>) getAllVmeCriterias();
		} else if (concept.equals(VmeType.class)) {
			return (List<R>) getAllVmeTypes();
		} else if (concept.equals(InformationSourceType.class)) {
			return (List<R>) getAllInformationSourceTypes();
		} else if (concept.equals(ReferenceYear.class)) {
			return (List<R>) getAllYears();
		} else if (concept.equals(VmeScope.class)) {
			return (List<R>) getAllVmeScopes();
		} else {
			throw new ReferenceServiceException(UNDEFINED);
		}

	}

	@SuppressWarnings("unchecked")
	private List<VmeScope> getAllVmeScopes() {
		return em.createQuery("from VmeScope").getResultList();
	}

	public VmeScope getVmeScope(Long key) {
		List<?> res = em.createQuery("from VmeScope where id = " + key).getResultList();
		return res.size() > 0 ? (VmeScope) res.get(0) : null;
	}

	public Authority getAuthority(Long key) {
		List<?> res = em.createQuery("from Authority where id = " + key).getResultList();
		return res.size() > 0 ? (Authority) res.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public List<Authority> getAllAuthorities() {

		List<Authority> list = em.createQuery("from Authority").getResultList();

		// hack Erik van Ingen Remove NPFC from the list as long as there is no
		// security
		Authority found = null;
		for (Authority authority : list) {
			if (authority.getAcronym().equals("NPFC")) {
				found = authority;
			}
		}
		if (found != null) {
			list.remove(found);
		}
		return list;
	}

	public VmeCriteria getVmeCriteria(Long key) {
		List<?> res = em.createQuery("from VmeCriteria where id = " + key).getResultList();
		return res.size() > 0 ? (VmeCriteria) res.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public List<VmeCriteria> getAllVmeCriterias() {
		return em.createQuery("from VmeCriteria").getResultList();
	}

	public VmeType getVmeType(Long key) {
		List<?> res = em.createQuery("from VmeType where id = " + key).getResultList();
		return res.size() > 0 ? (VmeType) res.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public List<VmeType> getAllVmeTypes() {
		return em.createQuery("from VmeType").getResultList();
	}

	public InformationSourceType getInformationSourceType(Long key) {
		List<?> res = em.createQuery("from InformationSourceType where id = " + key).getResultList();
		return res.size() > 0 ? (InformationSourceType) res.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public List<InformationSourceType> getAllInformationSourceTypes() {
		return em.createQuery("from InformationSourceType order by id asc").getResultList();
	}

	public ReferenceYear getYear(Long key) {
		return repYear.get(key);
	}

	public List<ReferenceYear> getAllYears() {
		List<ReferenceYear> years = new ArrayList<ReferenceYear>(repYear.values());

		Collections.sort(years);

		return years;
	}

	private Map<Long, ReferenceYear> createYears() {
		Map<Long, ReferenceYear> yearsMap = new LinkedHashMap<Long, ReferenceYear>();

		for (long year = Calendar.getInstance().get(Calendar.YEAR); year >= 2006; year--) {
			yearsMap.put(year, new ReferenceYear(year));
		}

		return yearsMap;
	}

	protected List<Class<? extends ReferenceConcept>> concepts;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vme.service.reference.ReferenceService#getConcepts()
	 */
	@Override
	public final List<Class<? extends ReferenceConcept>> getConcepts() throws ReferenceServiceException {
		return this.concepts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vme.service.reference.ReferenceService#getConcept(java.lang.String)
	 */
	@Override
	public final Class<? extends ReferenceConcept> getConcept(String acronym) throws ReferenceServiceException {
		for (Class<? extends ReferenceConcept> concept : concepts) {
			try {
				String name = concept.getSimpleName();

				if (concept.isAnnotationPresent(ReferenceConceptName.class)) {
					name = concept.getAnnotation(ReferenceConceptName.class).value();
				}

				if (acronym.equalsIgnoreCase(name)) {
					return concept;
				}
			} catch (Exception e) {
				LOG.warn(INTERNALERR, e);

				throw new ReferenceServiceException(INTERNALERR);
			}
		}

		throw new ReferenceServiceException(UNDEFINED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vme.service.reference.ReferenceService#getReferencebyAcronym(java
	 * .lang.Class, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final AcronymAwareReferenceConcept getReferenceByAcronym(
			Class<? extends AcronymAwareReferenceConcept> concept, String acronym) throws ReferenceServiceException {
		try {
			Collection<AcronymAwareReferenceConcept> allReferenceObjects = (Collection<AcronymAwareReferenceConcept>) getAllReferences(concept);

			for (ReferenceConcept reference : allReferenceObjects) {
				if (reference instanceof AcronymAwareReferenceConcept) {
					if (acronym.equals(((AcronymAwareReferenceConcept) reference).getAcronym())) {
						return (AcronymAwareReferenceConcept) reference;
					}
				}
			}

			return null;
		} catch (Exception t) {
			throw new ReferenceServiceException(t);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gcube.application.rsg.support.compiler.bridge.interfaces.reference
	 * .ReferenceConceptProvider#getReferenceByID(java.lang.Class,
	 * java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final <R extends ReferenceConcept> R getReferenceByID(Class<R> concept, Long id)
			throws ReferenceServiceException {
		return (R) this.getReference(concept, id);
	}
}
