/**
 * 
 */
package org.vme.service.dao.impl.hardcoded;

import java.util.List;

import org.fao.fi.vme.domain.dto.ref.ReferenceYear;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.VmeCriteria;
import org.fao.fi.vme.domain.model.VmeType;
import org.gcube.application.rsg.support.reference.concepts.interfaces.ReferenceConcept;
import org.vme.service.dao.ReferenceServiceException;
import org.vme.service.dao.impl.AbstractReferenceDAO;

/**
 * @author Fabrizio Sibeni
 * 
 */
public class ReferenceHarcodedDao extends AbstractReferenceDAO {
	private ReferenceHardcodedDaoHelper helper;

	public ReferenceHarcodedDao() {
		super();
		helper = new ReferenceHardcodedDaoHelper();
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
			return helper.getAuthority(id);
		} else if (concept.equals(VmeCriteria.class)) {
			return helper.getVmeCriteria(id);
		} else if (concept.equals(VmeType.class)) {
			return helper.getVmeType(id);
		} else if (concept.equals(ReferenceYear.class)) {
			return helper.getYear(id);
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
	public List<? extends ReferenceConcept> getAllReferences(
			Class<? extends ReferenceConcept> concept) throws ReferenceServiceException {
		if (concept.equals(Authority.class)) {
			return helper.getAllAuthorities();
		} else if (concept.equals(VmeCriteria.class)) {
			return helper.getAllVmeCriterias();
		} else if (concept.equals(VmeType.class)) {
			return helper.getAllVmeTypes();
		} else if (concept.equals(ReferenceYear.class)) {
			return helper.getAllYears();
		} else {
			throw new ReferenceServiceException("Undefined reference concept");
		}

	}

}
