/**
 * 
 */
package org.vme.service.dao.impl.hibernate;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.fao.fi.vme.domain.dto.ref.Year;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.VmeCriteria;
import org.fao.fi.vme.domain.model.VmeType;
import org.gcube.application.rsg.support.interfaces.Concept;
import org.vme.service.dao.JpaDaoFactory;
import org.vme.service.dao.ReferenceServiceException;
import org.vme.service.dao.impl.AbstractReferenceDAO;

/**
 * @author Fabrizio Sibeni
 * 
 */
public class ReferenceJpaDao extends AbstractReferenceDAO {
	private JpaDaoFactory factory;

	private Map<Integer, Year> repYear;

	public ReferenceJpaDao(JpaDaoFactory factory) {
		this.factory = factory;

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
	public Concept getReference(Class<? extends Concept> concept, Long id)
			throws ReferenceServiceException {
		if (concept.equals(Authority.class)) {
			return getAuthority(id);
		} else if (concept.equals(VmeCriteria.class)) {
			return getVmeCriteria(id);
		} else if (concept.equals(VmeType.class)) {
			return getVmeType(id);
		} else if (concept.equals(Year.class)) {
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
	@Override
	public List<? extends Concept> getAllReferences(
			Class<? extends Concept> concept) throws ReferenceServiceException {
		if (concept.equals(Authority.class)) {
			return getAllAuthorities();
		} else if (concept.equals(VmeCriteria.class)) {
			return getAllVmeCriterias();
		} else if (concept.equals(VmeType.class)) {
			return getAllVmeTypes();
		} else if (concept.equals(Year.class)) {
			return getAllYears();
		} else {
			throw new ReferenceServiceException("Undefined reference concept");
		}

	}

	public Authority getAuthority(Long key) {
		List<?> res = factory.getEntityManager()
				.createQuery("from Authority where id = " + key)
				.getResultList();
		return res.size() > 0 ? (Authority) res.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public List<Authority> getAllAuthorities() {
		return factory.getEntityManager().createQuery("from Authority")
				.getResultList();
	}

	public VmeCriteria getVmeCriteria(Long key) {
		List<?> res = factory.getEntityManager()
				.createQuery("from VmeCriteria where id = " + key)
				.getResultList();
		return res.size() > 0 ? (VmeCriteria) res.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public List<VmeCriteria> getAllVmeCriterias() {
		return factory.getEntityManager().createQuery("from VmeCriteria")
				.getResultList();
	}

	public VmeType getVmeType(Long key) {
		List<?> res = factory.getEntityManager()
				.createQuery("from VmeType where id = " + key).getResultList();
		return res.size() > 0 ? (VmeType) res.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public List<VmeType> getAllVmeTypes() {
		return factory.getEntityManager().createQuery("from VmeType")
				.getResultList();
	}

	public Year getYear(Long key) {
		return repYear.get(key);
	}

	public List<Year> getAllYears() {
		return new LinkedList<Year>(repYear.values());
	}

	private Map<Integer, Year> createYears() {
		Map<Integer, Year> yearsMap = new HashMap<Integer, Year>();

		for (int year = 2006; year <= Calendar.getInstance().get(Calendar.YEAR); year++)
			yearsMap.put(year, new Year(year));

		return yearsMap;
	}
}
