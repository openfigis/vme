/**
 * 
 */
package org.vme.service.dto.ref;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Fabrizio Sibeni
 *
 */
@Entity
public class Authority {

	public final static String PARAMETER_ID = "authority";

	
	/**
	 *  Identifier of the domain entity.  
	 */
	@Id
	private int id;
	
	/** 
	 * Acronym of the authority
	 */
	private String acronym;
	
	/** 
	 * Name of the authority
	 */
	private String name;
	
	/** 
	 * Verbose description of the authority
	 */
	private String description;
	
	/** 
	 * Language of reference
	 */
	private String lang;
	
	
	public Authority() {
		// TODO Auto-generated constructor stub
	}



	public Authority(int id, String acronym, String name) {
		super();
		this.id = id;
		this.acronym = acronym;
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
	 * @return the acronym
	 */
	public String getAcronym() {
		return acronym;
	}



	/**
	 * @param acronym the acronym to set
	 */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
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
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}



	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
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
