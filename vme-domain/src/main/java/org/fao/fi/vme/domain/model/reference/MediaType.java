package org.fao.fi.vme.domain.model.reference;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.fao.fi.vme.domain.annotations.ReferenceConceptName;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.NamedReferenceConcept;

/**
 * For now we have 2 types. Image (1) and Video (2)
 * 
 * 
 * @author Erik van Ingen
 *
 */

@Entity
@Table
@ReferenceConceptName("mediatype")
public class MediaType implements ReferenceDataObject<Long>, NamedReferenceConcept, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3232564603231071956L;

	@RSGIdentifier
	@Id
	private Long id;

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
