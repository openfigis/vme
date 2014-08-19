/**
 * 
 */
package org.fao.fi.vme.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.fao.fi.vme.domain.annotations.ReferenceConceptName;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.ReferenceConcept;

/**
 * @author Fabrizio Sibeni
 * 
 */
@Entity
@ReferenceConceptName("years")
public class ReferenceYear implements ReferenceConcept, Comparable<ReferenceYear>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1586960198381199769L;

	/**
	 * Identifier of the domain entity.
	 */
	@Id
	private Long id;

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
	 * @param lang
	 *            the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	public ReferenceYear() {
	}

	public ReferenceYear(Long id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return Long.toString(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ReferenceYear another) {
		return another.getId().compareTo(this.getId());
	}
}
