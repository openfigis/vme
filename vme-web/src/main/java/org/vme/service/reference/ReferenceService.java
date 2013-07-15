/**
 * 
 */
package org.vme.service.reference;


import java.util.List;



/**
 * The reference data service.
 * @author Francesco
 *
 */
public interface ReferenceService {
	/**
	 * Return all the defined reference concepts.
	 * @return the existing concepts if any, an empty list otherwise
	 * @throws ReferenceServiceException
	 */
	public List<Concept> getConcepts() throws ReferenceServiceException;
	/**
	 * Return a concept via its acronym.
	 * @param acronym the concept acronym
	 * @return the requested concept if existing, null otherwise 
	 * @throws ReferenceServiceException
	 */
	public Concept getConcept(String acronym) throws ReferenceServiceException;
	/**
	 * Return a concept via its unique ID.
	 * @param id the concept unique identifier
	 * @return the requested concept if existing, null otherwise
	 * @throws ReferenceServiceException
	 */
	public Concept getConcept(Long id) throws ReferenceServiceException;
	
}
