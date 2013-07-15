/**
 * 
 */
package org.vme.service.reference.impl;

import java.util.Collection;

import org.fao.fi.vme.domain.MultiLingualString;
import org.vme.service.reference.Concept;
import org.vme.service.reference.ReferenceObject;
import org.vme.service.reference.ReferenceServiceException;

/**
 * @author Francesco
 */
public class ConceptImpl implements Concept {





	@Override
	public org.vme.service.reference.ReferenceObject getObject(
			String codeAttributeAcronym, Object value)
			throws org.vme.service.reference.ReferenceServiceException {
		// TODO Auto-generated method stub
		return null;
	}







	@Override
	public org.vme.service.reference.ReferenceObject createObject()
			throws org.vme.service.reference.ReferenceServiceException {
		// TODO Auto-generated method stub
		return null;
	}







	@Override
	public org.vme.service.reference.ReferenceObject addObject(
			org.vme.service.reference.ReferenceObject object, Long id)
			throws org.vme.service.reference.ReferenceServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * The unique identifier
	 */
	private Long identifier;
	
	/**
	 * The concept acronym
	 */
	private String acronym;
	
	/**
	 * The concept name
	 */
	private MultiLingualString name;
	
	/**
	 * The concept name
	 */
	private MultiLingualString description;
	
	

	public ConceptImpl(Object concept_dto) throws ReferenceServiceException {

	}







	@Override
	public Long getId() {
		return identifier;
	}

	@Override
	public String getAcronym() {
		return acronym;
	}

	@Override
	public MultiLingualString getName() {
		return name;
	}

	@Override
	public void setName(MultiLingualString name)  {
		this.name = name;
	}

	@Override
	public MultiLingualString getDescription()  {
		return description;
	}

	@Override
	public void setDescription(MultiLingualString description)  {
		this.description = description;
	}

	




	@Override
	public Collection<ReferenceObject> getObjects() throws ReferenceServiceException {
		return null;
}

	@Override
	public ReferenceObject getObject(Long id) throws ReferenceServiceException {
		return null;
	}





	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[").append(getId()).append("]").append("[").append(getAcronym()).append("]").append("[").append(getName()).append("]").append("[").append(getDescription()).append("]").append("[");
		return buffer.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Concept)) {
			return false;
		}
		return getId().equals(((Concept) obj).getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}




	@Override
	public void removeObject(ReferenceObject object) throws ReferenceServiceException {
		
	}

	


	@Override
	public void removeAll() throws ReferenceServiceException {

	}

	@Override
	public Collection<ReferenceObject> addAll(Collection<ReferenceObject> objects) throws ReferenceServiceException {
		return null;
	}

	@Override
	public ReferenceObject addObject(ReferenceObject object) throws ReferenceServiceException {
		return null;
	}


	




}
