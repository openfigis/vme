/**
 * The reference data service.
 * @author Fabrizio Sibeni
 *
 */

package org.vme.service.dao;


import java.util.List;

import org.gcube.application.rsg.support.interfaces.AcronymAwareConcept;
import org.gcube.application.rsg.support.interfaces.Concept;
import org.gcube.application.rsg.support.providers.ConceptProvider;




public interface ReferenceDAO extends ConceptProvider {
	/**
	 * Return all the defined reference classes.
	 * @return the existing concepts if any, an empty list otherwise
	 * @throws ReferenceServiceException
	 */
	public List<Class<? extends Concept>> getConcepts() throws ReferenceServiceException;
	
	
	/**
	 * Return a concept via its acronym.
	 * @param acronym the concept acronym
	 * @return the requested concept if existing, null otherwise 
	 * @throws ReferenceServiceException
	 */
	public Class<? extends Concept> getConcept(String acronym) throws ReferenceServiceException;

	/**
	 * Return a defined concept class via its name.
	 * @param id the concept class name
	 * @return the requested concept class if existing
	 * @throws ReferenceServiceException
	 */
	public Concept getReference(Class<? extends Concept> concept, Long id) throws ReferenceServiceException;	
	
	/**
	 * Return a defined concept class via its name.
	 * @param id the concept class name
	 * @return the requested concept class if existing
	 * @throws ReferenceServiceException
	 */
	public AcronymAwareConcept getReferenceByAcronym(Class<? extends AcronymAwareConcept> concept, String acronym) throws ReferenceServiceException;
	
}
