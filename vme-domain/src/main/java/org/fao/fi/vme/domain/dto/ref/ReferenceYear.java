/**
 * 
 */
package org.fao.fi.vme.domain.dto.ref;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.fao.fi.vme.domain.annotations.ReferenceConceptName;
import org.gcube.application.rsg.support.reference.concepts.interfaces.ReferenceConcept;

/**
 * @author Fabrizio Sibeni
 * 
 */
@Entity
@ReferenceConceptName("years")
public class ReferenceYear implements ReferenceConcept {
	/**
	 * Identifier of the domain entity.
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

	public ReferenceYear() {
	}

	public ReferenceYear(int id) {
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
	
	
	public String getName(){
		return Integer.toString(id);
	}

}
