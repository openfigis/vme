/**
 * The reference data service.
 * @author Fabrizio Sibeni
 *
 */

package org.vme.service.dao;


import java.util.List;

import org.gcube.application.rsg.support.compiler.bridge.reference.interfaces.AcronymAwareReferenceConcept;
import org.gcube.application.rsg.support.compiler.bridge.reference.interfaces.ReferenceConcept;
import org.gcube.application.rsg.support.compiler.bridge.reference.interfaces.ReferenceConceptProvider;




public interface ReferenceDAO extends ReferenceConceptProvider {
	/**
	 * Return all the defined reference classes.
	 * @return the existing ReferenceConcepts if any, an empty list otherwise
	 * @throws ReferenceServiceException
	 */
	public List<Class<? extends ReferenceConcept>> getConcepts() throws ReferenceServiceException;
	
	
	/**
	 * Return a ReferenceConcept via its acronym.
	 * @param acronym the ReferenceConcept acronym
	 * @return the requested ReferenceConcept if existing, null otherwise 
	 * @throws ReferenceServiceException
	 */
	public Class<? extends ReferenceConcept> getConcept(String acronym) throws ReferenceServiceException;

	/**
	 * Return a defined ReferenceConcept class via its name.
	 * @param id the ReferenceConcept class name
	 * @return the requested ReferenceConcept class if existing
	 * @throws ReferenceServiceException
	 */
	public ReferenceConcept getReference(Class<? extends ReferenceConcept> concept, Long id) throws ReferenceServiceException;	
	
	/**
	 * Return a defined ReferenceConcept class via its name.
	 * @param id the ReferenceConcept class name
	 * @return the requested ReferenceConcept class if existing
	 * @throws ReferenceServiceException
	 */
	public AcronymAwareReferenceConcept getReferenceByAcronym(Class<? extends AcronymAwareReferenceConcept> concept, String acronym) throws ReferenceServiceException;
	
}
