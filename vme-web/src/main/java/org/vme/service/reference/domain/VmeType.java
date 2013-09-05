/**
 * 
 */
package org.vme.service.reference.domain;

import javax.persistence.Id;

/**
 * @author Fabrizio Sibeni
 *
 */
public class VmeType {
	/**
	 *  Identifier of the domain entity.  
	 */
	@Id
	private int id;
	
	/** 
	 * Name of the vme type
	 */
	private String name;

	/** 
	 * Verbose description of the vme type
	 */
	private String description;
	
	
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
