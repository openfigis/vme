package org.fao.fi.vme.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.fao.fi.vme.domain.annotations.ReferenceConceptName;
import org.fao.fi.vme.domain.model.reference.ReferenceDataObject;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.AcronymAwareReferenceConcept;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.NamedReferenceConcept;

/**
 * 
 * @author Fabrizio Sibeni
 * 
 */
@Entity
@ReferenceConceptName("authority")
public class Authority implements ReferenceDataObject<Long>, NamedReferenceConcept, AcronymAwareReferenceConcept,
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2840731664895873896L;

	/**
	 * Identifier of the domain entity.
	 */
	@Id
	private Long id;

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

	public Authority() {
	}

	public Authority(Long id, String acronym, String name) {
		super();
		this.id = id;
		this.acronym = acronym;
		this.name = name;
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

	/**
	 * @return the acronym
	 */
	public String getAcronym() {
		return acronym;
	}

	/**
	 * @param acronym
	 *            the acronym to set
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
	 * @param name
	 *            the name to set
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
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
