package org.fao.fi.vme.domain.model.reference;

import org.fao.fi.vme.domain.model.ReferenceDataObject;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.NamedReferenceConcept;

/**
 * 
 * Reference data object for the scope of a VME.
 * 
 * Add Type attribute to VME record with values: "VME" and "Regulatory"
 * 
 * 
 * @author Erik van Ingen
 * 
 */

// @Entity
// @ReferenceConceptName("scope")
public class Scope implements ReferenceDataObject<Long>, NamedReferenceConcept {

	/**
	 * The code for the reference data object
	 */
	// @RSGIdentifier
	// @Id
	private Long id;

	/**
	 * The name in different languages. In English this is "VME" and
	 * "Regulatory"
	 */
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}