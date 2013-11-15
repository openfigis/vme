/**
 * 
 */
package org.fao.fi.vme.domain.dto.ref;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.fao.fi.vme.domain.annotations.ConceptName;
import org.gcube.application.rsg.support.interfaces.ReferenceConcept;

/**
 * @author Fabrizio Sibeni
 * 
 */
@Entity
@ConceptName("years")
public class Year implements ReferenceConcept {
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
