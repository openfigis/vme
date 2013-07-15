package org.vme.service.search.reference;

import java.util.Collection;
import java.util.Map;


import org.fao.fi.vme.domain.MultiLingualString;


/**
 * Metadata description of a reference object type. 
 * @author   Fabrizio Sibeni
 */
public interface Concept   {

	/**
	 * Get the concept unique ID.
	 * @return the ID
	 */
	public Long getId();
	/**
	 * Get the acronym.
	 * @return the acronym
	 */
	public String getAcronym();
	/**
	 * Get the name.
	 * @return name
	 * @throws ReferenceServiceException
	 */
	public MultiLingualString getName() throws ReferenceServiceException;
	/**
	 * Set the name.
	 * @param name
	 * @throws ReferenceServiceException
	 */
	public void setName(MultiLingualString name) throws ReferenceServiceException;
	/**
	 * Get the description.
	 * @return description
	 * @throws ReferenceServiceException
	 */
	public MultiLingualString getDescription() throws ReferenceServiceException;
	/**
	 * Set the description.
	 * @param description
	 * @throws ReferenceServiceException
	 */
	public void setDescription(MultiLingualString description) throws ReferenceServiceException;

	/**
	 * Get a reference object given its ID.
	 * @param id
	 * @return the reference object, null if not found
	 * @throws ReferenceServiceException
	 */
	public ReferenceObject getObject(Long id) throws ReferenceServiceException;
	/**
	 * Get the reference object matching the code attribute value.
	 * @param codeAttribute the meta (code) attribute
	 * @param value the value to match
	 * @return the reference object matching the attribute criteria
	 * @throws ReferenceServiceException
	 */

	public ReferenceObject getObject(String codeAttributeAcronym, Object value) throws ReferenceServiceException;
	/**
	 * Get all the reference objects.
	 * @return all the reference objects
	 * @throws ReferenceServiceException
	 */
	public Collection<ReferenceObject> getObjects() throws ReferenceServiceException;
	
	/**
	 * Create a new ReferenceObject instance for this concept.
	 * @return the newly created instance.
	 * @throws ReferenceServiceException
	 */
	public ReferenceObject createObject() throws ReferenceServiceException;
	
	/**
	 * Delete a ReferenceObject instance.
	 * @param object the ReferenceObject to delete.
	 * @throws ReferenceServiceException
	 */
	public void removeObject(ReferenceObject object) throws ReferenceServiceException;
	/**
	 * Delete all the instances for this concept.
	 * @throws ReferenceServiceException
	 */
	public void removeAll() throws ReferenceServiceException;
	/**
	 * Add the ReferenceObject to the instances for this Concept.
	 * @param object the object to add
	 * @return the newly added reference object
	 * @throws ReferenceServiceException
	 */
	public ReferenceObject addObject(ReferenceObject object) throws ReferenceServiceException;
	/**
	 * Add the ReferenceObject with a specific id to the instances for this Concept.
	 * @param object the object to add
	 * @param id the identifier of the object
	 * @return the newly added reference object
	 * @throws ReferenceServiceException
	 */
	public ReferenceObject addObject(ReferenceObject object, Long id) throws ReferenceServiceException;
	
}
