/**
 * 
 */
package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Fabrizio Sibeni
 *
 */

@Entity
@Table(name="VME_CRITERIA")
public class VmeCriteria {

	public final static String PARAMETER_ID = "criteria";
	
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


	

}
