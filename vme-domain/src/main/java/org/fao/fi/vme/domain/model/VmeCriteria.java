/**
 * 
 */
package org.fao.fi.vme.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.fao.fi.vme.domain.annotations.ReferenceConceptName;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.NamedReferenceConcept;

/**
 * @author Fabrizio Sibeni
 *
 */

@Entity
@Table(name="VME_CRITERIA")
@ReferenceConceptName("criteria")
public class VmeCriteria implements NamedReferenceConcept {
	/**
	 *  Identifier of the domain entity.  
	 */
	@RSGIdentifier
	@Id
	private int id;
	
	/** 
	 * Name of the vme criteria
	 */
	private String name;


	
	/**
	 * 
	 */
	public VmeCriteria() {
		// TODO Auto-generated constructor stub
	}

	
	
	public VmeCriteria(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.id;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VmeCriteria other = (VmeCriteria) obj;
		if (this.id != other.id)
			return false;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		return true;
	}
}
