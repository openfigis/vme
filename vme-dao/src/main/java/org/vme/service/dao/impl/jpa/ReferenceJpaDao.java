/**
 * 
 */
package org.vme.service.dao.impl.jpa;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.dto.ref.ReferenceYear;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.VmeCriteria;
import org.fao.fi.vme.domain.model.VmeType;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.ReferenceConcept;
import org.vme.service.dao.ReferenceServiceException;
import org.vme.service.dao.config.vme.VmeDB;
import org.vme.service.dao.impl.AbstractReferenceDAO;

/**
 * @author Fabrizio Sibeni
 * 
 */
public class ReferenceJpaDao extends AbstractReferenceDAO {
	
	@VmeDB
	@Inject
	private EntityManager entityManager;
	
	private Map<Integer, ReferenceYear> repYear;

	public ReferenceJpaDao() {
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
		} else if (concept.equals(ReferenceYear.class)) {
			return getYear(id);
		} else {
			throw new ReferenceServiceException("Undefined reference concept");
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
			return (List<R>)getAllAuthorities();
		} else if (concept.equals(VmeCriteria.class)) {
			return (List<R>)getAllVmeCriterias();
		} else if (concept.equals(VmeType.class)) {
			return (List<R>)getAllVmeTypes();
		} else if (concept.equals(ReferenceYear.class)) {
			return (List<R>)getAllYears();
		} else {
			throw new ReferenceServiceException("Undefined reference concept");
		}

	}

	public Authority getAuthority(Long key) {
		List<?> res = entityManager
				.createQuery("from Authority where id = " + key)
				.getResultList();
		return res.size() > 0 ? (Authority) res.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public List<Authority> getAllAuthorities() {
		return entityManager.createQuery("from Authority")
				.getResultList();
	}

	public VmeCriteria getVmeCriteria(Long key) {
		List<?> res = entityManager
				.createQuery("from VmeCriteria where id = " + key)
				.getResultList();
		return res.size() > 0 ? (VmeCriteria) res.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public List<VmeCriteria> getAllVmeCriterias() {
		return entityManager.createQuery("from VmeCriteria")
				.getResultList();
	}

	public VmeType getVmeType(Long key) {
		List<?> res = entityManager
				.createQuery("from VmeType where id = " + key).getResultList();
		return res.size() > 0 ? (VmeType) res.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public List<VmeType> getAllVmeTypes() {
		return entityManager.createQuery("from VmeType")
				.getResultList();
	}

	public ReferenceYear getYear(Long key) {
		return repYear.get(key);
	}

	public List<ReferenceYear> getAllYears() {
		return new LinkedList<ReferenceYear>(repYear.values());
	}

	private Map<Integer, ReferenceYear> createYears() {
		Map<Integer, ReferenceYear> yearsMap = new LinkedHashMap<Integer, ReferenceYear>();

		for (int year = Calendar.getInstance().get(Calendar.YEAR); year >= 2006; year--)
			yearsMap.put(year, new ReferenceYear(year));

		return yearsMap;
	}
}
