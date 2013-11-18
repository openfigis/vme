/**
 * 
 */
package org.fao.fi.vme.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.fao.fi.vme.domain.annotations.ReferenceConceptName;
import org.gcube.application.rsg.support.interfaces.NamedReferenceConcept;

/**
 * @author Fabrizio Sibeni
 * 
 */

@Entity
@Table(name = "VME_TYPE")
@ReferenceConceptName("type")
public class VmeType implements NamedReferenceConcept {
	/**
	 * Identifier of the domain entity.
	 */
	@Id
	private int id;

	/**
	 * Name of the vme type
	 */
	private String name;

	/**
	 * 
	 */
	public VmeType() {
		// TODO Auto-generated constructor stub
	}

	public VmeType(int id, String name) {
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

}
