/**
 * 
 */
package org.vme.service.reference;


import java.util.Map;



/**
 * Concept instance, gives access to its attribute and relationship instances.
 * @author   Fabrizio Sibeni
 */
public interface ReferenceObject {

	/**
	 * Accessor method.
	 * @return the unique object identifier
	 */
	public Long getId();
	/**
	 * Accessor method.
	 * @return the concept this object is an instance of
	 */
	public Concept getConcept();
	/**
	 * Return the list of attribute instances for this object.
	 * @return the list of reference attribute instances
	 * @throws ReferenceServiceException
	 */
	public Map<Attribute, Object> getAttributes() throws ReferenceServiceException;
	/**
	 * Set the attribute instance values for this object.
	 * @param attributes the attribute instance values
	 * @throws ReferenceServiceException
	 */
	public void setAttributes(Map<Attribute, Object> attributes) throws ReferenceServiceException;
	/**
	 * Return the attribute instance value.
	 * @param acronym the concept attribute acronym
	 * @return the attribute instance value
	 * @throws ReferenceServiceException
	 */
	public Object getAttribute(String acronym) throws ReferenceServiceException;
	/**
	 * Return the attribute instance value.
	 * @param meta the concept attribute
	 * @return the attribute instance value
	 * @throws ReferenceServiceException
	 */
	public Object getAttribute(Attribute meta) throws ReferenceServiceException;
	/**
	 * Set the attribute instance value.
	 * @param meta the meta attribute to set
	 * @param value the attribute value
	 * @throws ReferenceServiceException
	 */
	public void setAttribute(Attribute meta, Object value) throws ReferenceServiceException;
	/**
	 * Set the attribute instance value.
	 * @param acronym the acronym of the attribute to set
	 * @param value the attribute value
	 * @throws ReferenceServiceException
	 */
	public void setAttribute(String acronym, Object value) throws ReferenceServiceException;
}
