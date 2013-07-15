/**
 * 
 */
package org.vme.service.reference.impl;

import org.fao.fi.vme.domain.MultiLingualString;
import org.vme.service.reference.Attribute;
import org.vme.service.reference.AttributeType;
import org.vme.service.reference.Concept;
import org.vme.service.reference.ReferenceServiceException;



/**
 * @author Fabrizio Sibeni
 */
public class AttributeImpl implements Attribute {
	/**
	 * The meta attribute DTO
	 */

	private Long identifier;
	private Concept concept;
	private String acronym;
	private AttributeType type;
	private Short scale;
	private Short size;
	
	
	private boolean codeAttribute;
	private boolean nameAttribute;
	private MultiLingualString name;
	private MultiLingualString description;

	public AttributeImpl(Object attribute_dto)  {
	}

	@Override
	public String getAcronym() {
		return acronym;
	}

	@Override
	public MultiLingualString getDescription()  {
		return description;
	}

	@Override
	public Long getId() {
		return identifier;
	}

	@Override
	public Concept getConcept() {
		return concept;
	}

	@Override
	public MultiLingualString getName() {
		return name;
	}

	@Override
	public AttributeType getType() {
		return type;
	}

	@Override
	public boolean isCodeAttribute() {
		return codeAttribute;
	}

	@Override
	public void setCodeAttribute(boolean value) throws ReferenceServiceException {
		codeAttribute = value;
	}

	@Override
	public void setDescription(MultiLingualString description) throws ReferenceServiceException {
		this.description = description;
	}

	@Override
	public void setName(MultiLingualString name) throws ReferenceServiceException {
		this.name = name;
	}

	@Override
	public void setType(AttributeType type) throws ReferenceServiceException {
		this.type = type;
	}



	@Override
	public String toString() {
		return name.toString();
	}

	@Override
	public short getScale() {
		return scale;
	}

	@Override
	public short getSize() {
		return size;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Attribute)) {
			return false;
		}
		Attribute attr = (Attribute) obj;
		if (getId() == null) {
			return (attr.getId() == null); 
		}
		return attr.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean isNameAttribute() {
		return nameAttribute;
	}

	@Override
	public void setNameAttribute(boolean value) throws ReferenceServiceException {


	}

}
