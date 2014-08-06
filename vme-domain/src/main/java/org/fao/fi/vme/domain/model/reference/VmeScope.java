package org.fao.fi.vme.domain.model.reference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.fao.fi.vme.domain.annotations.ReferenceConceptName;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
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

@Entity
@Table(name = "VME_SCOPE")
@ReferenceConceptName("scope")
public class VmeScope implements ReferenceDataObject<Long>, NamedReferenceConcept {

	/**
	 * The code for the reference data object
	 */
	@RSGIdentifier
	@Id
	private Long id;

	/**
	 * The name in different languages. In English this is "VME" and
	 * "Regulatory"
	 */
	private String name;
	private String codeSystem;

	public String getCodeSystem() {
		return codeSystem;
	}

	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}

	/**
	 * Default constructor
	 */
	public VmeScope() {
	}

	public VmeScope(Long id, String name, String codeSystem) {
		super();
		this.id = id;
		this.name = name;
		this.codeSystem = codeSystem;
	}

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
