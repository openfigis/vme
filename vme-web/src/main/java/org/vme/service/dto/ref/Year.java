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
	private String lang;
	
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

}
