/**
 * 
 */
package org.vme.service.reference.domain;

import javax.persistence.Id;

/**
 * @author Fabrizio Sibeni
 *
 */
public class VmeCriteria {

	/**
	 *  Identifier of the domain entity.  
	 */
	@Id
	private int id;
	
	/** 
	 * Name of the vme criteria
	 */
	private String name;

	/** 
	 * Verbose description of the vme criteria
	 */
	private String description;
	
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

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
