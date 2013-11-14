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
public class Year {

	public final static String PARAMETER_ID = "years";

	
	
	/**
	 *  Identifier of the domain entity.  
	 */
	@Id
	private int id;
	
	/** 
	 * Language of reference
	 */
	private String lang = "en";
	
	/** 
	 * Year in textual mode
	 */
	private String name;
	
	
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
	
	
	
	
	public Year() {
	}



	public Year(int id) {
		super();
		this.id = id;
		this.name = Integer.toString(id);
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
