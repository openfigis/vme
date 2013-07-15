/**
 * 
 */
package org.vme.service.reference;

import org.fao.fi.vme.domain.MultiLingualString;



/**
 * Metadata description of a reference object type attribute.
 * @author   Fabrizio Sibeni
 */
public interface Attribute {
	/**
	 * Return the unique ID.
	 * @return the unique ID
	 */
	public Long getId();
	/**
	 * Return the acronym.
	 * @return the acronym
	 */
	public String getAcronym();
	/**
	 * Return the multilingual name.
	 * @return the name
	 */
	public MultiLingualString getName();
	/**
	 * Set the multilingual name.
	 * @param name
	 * @throws ReferenceServiceException
	 */
	public void setName(MultiLingualString name) throws ReferenceServiceException;
	/**
	 * Return the multilingual description.
	 * @return the description
	 */
	public MultiLingualString getDescription();
	/**
	 * Set the multilingual description
	 * @param description
	 * @throws ReferenceServiceException
	 */
	public void setDescription(MultiLingualString description) throws ReferenceServiceException;
	/**
	 * Return the attribute type
	 * @return the type
	 */
	public AttributeType getType();
	/**
	 * Set the attribute type.
	 * @param type the attribute type
	 * @throws ReferenceServiceException
	 */
	public void setType(AttributeType type) throws ReferenceServiceException;
	/**
	 * Return the attribute size (length, for character types) or precision (total number of digits, for decimal types)
	 * @return the attribute size or precision
	 */
	public short getSize();
	/**
	 * Return the attribute scale (number of decimal digits, only for decimal types)
	 * @return the attribute scale
	 */
	public short getScale();
	/**
	 * Return the concept associated to the attribute.
	 * @return the concept
	 * @throws ReferenceServiceException
	 */
	public Concept getConcept() throws ReferenceServiceException;
	/**
	 * Check whether the attribute is a code.
	 * @return true iff the attribute is a code
	 */
	public boolean isCodeAttribute();
	/**
	 * Check whether the attribute is a name.
	 * @return true iff the attribute is a name
	 */
	public boolean isNameAttribute();
	/**
	 * Set the code qualifier of the attribute.
	 * @param value the code qualifier of the attribute
	 * @throws ReferenceServiceException
	 */
	public void setCodeAttribute(boolean value) throws ReferenceServiceException;
	/**
	 * Set the name qualifier of the attribute.
	 * @param value the name qualifier of the attribute
	 * @throws ReferenceServiceException
	 */
	public void setNameAttribute(boolean value) throws ReferenceServiceException;
}
