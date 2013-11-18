/**
 * (c) 2013 FAO / UN (project: vme-dao)
 */
package org.vme.service.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.fao.fi.vme.domain.annotations.ReferenceConceptName;
import org.fao.fi.vme.domain.dto.ref.Year;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.VmeCriteria;
import org.fao.fi.vme.domain.model.VmeType;
import org.gcube.application.rsg.support.interfaces.AcronymAwareReferenceConcept;
import org.gcube.application.rsg.support.interfaces.ReferenceConcept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.service.dao.ReferenceDAO;
import org.vme.service.dao.ReferenceServiceException;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 13/nov/2013   Fabio     Creation.
 *
 * @version 1.0
 * @since 13/nov/2013
 */
abstract public class AbstractReferenceDAO implements ReferenceDAO {
	final static protected Logger LOG = LoggerFactory.getLogger(AbstractReferenceDAO.class);
	
	protected List<Class<? extends ReferenceConcept>> concepts;

	public AbstractReferenceDAO() {
		super();
	
		this.concepts = new ArrayList<Class<? extends ReferenceConcept>>();

		this.concepts.add(Authority.class);
		this.concepts.add(VmeCriteria.class);
		this.concepts.add(VmeType.class);
		this.concepts.add(Year.class);
	}	
	
	/* (non-Javadoc)
	 * @see org.vme.service.reference.ReferenceService#getConcepts()
	 */
	@Override
	final public List<Class<? extends ReferenceConcept>> getConcepts() throws ReferenceServiceException {
		return this.concepts;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vme.service.reference.ReferenceService#getConcept(java.lang.String)
	 */
	@Override
	final public Class<? extends ReferenceConcept> getConcept(String acronym) throws ReferenceServiceException {
		for (Class<? extends ReferenceConcept> concept : concepts) {
			try {
				String name = concept.getSimpleName();
				
				if(concept.isAnnotationPresent(ReferenceConceptName.class))
					name = concept.getAnnotation(ReferenceConceptName.class).value();
				
				if (acronym.equalsIgnoreCase(name))
					return concept;
			} catch (Exception e) {
				LOG.warn("Internal error", e);

				throw new ReferenceServiceException("Internal error");
			}
		}
		
		throw new ReferenceServiceException("Undefined reference ReferenceConcept");
	}
	
	/* (non-Javadoc)
	 * @see org.vme.service.reference.ReferenceService#getReferencebyAcronym(java.lang.Class, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	final public AcronymAwareReferenceConcept getReferenceByAcronym(Class<? extends AcronymAwareReferenceConcept> concept, String acronym) throws ReferenceServiceException {
		try {
			Collection<? extends ReferenceConcept> allReferenceObjects = getAllReferences((Class<ReferenceConcept>)concept);
	
			for (ReferenceConcept reference : allReferenceObjects) {
				if(reference instanceof AcronymAwareReferenceConcept) {
					if(acronym.equals(((AcronymAwareReferenceConcept)reference).getAcronym()))
						return (AcronymAwareReferenceConcept)reference;
				}
			}
			
			return null;
		} catch (Throwable t) {
			throw new ReferenceServiceException(t);
		}
	}
}