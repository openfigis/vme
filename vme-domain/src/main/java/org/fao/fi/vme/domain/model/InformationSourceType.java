/**
 * 
 */
package org.fao.fi.vme.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.fao.fi.vme.domain.annotations.ReferenceConceptName;
import org.fao.fi.vme.domain.util.SerializableConceptUtils;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.NamedReferenceConcept;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.SerializableReferenceConcept;

/**
 * Place your class / interface description here.
 * 
 * History:
 * 
 * ------------- --------------- ----------------------- Date Author Comment
 * ------------- --------------- ----------------------- 26 Feb 2014 Fiorellato
 * Creation.
 * 
 * @version 1.0
 * @since 26 Feb 2014
 */
@Entity
@Table(name = "INFORMATION_SOURCE_TYPE")
@ReferenceConceptName("InformationSourceType")
public class InformationSourceType implements NamedReferenceConcept,
		SerializableReferenceConcept<InformationSourceType>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3051286652484467150L;
	final static public Character IS_A_MEETING_DOCUMENT = 'Y';
	final static public Character IS_NOT_A_MEETING_DOCUMENT = 'N';

	/**
	 * Identifier of the domain entity.
	 */
	@RSGIdentifier
	@Id
	private Long id;

	/**
	 * Name of the vme type
	 */
	private String name;

	@Column(name = "MEETING_DOCUMENT", nullable = false)
	private char _meetingDocument;

	public InformationSourceType() {
	}

	public InformationSourceType(Long id, String name, Character meetingDocument) {
		super();
		this.id = id;
		this.name = name;
		this._meetingDocument = meetingDocument == null ? IS_NOT_A_MEETING_DOCUMENT : meetingDocument;
	}

	public InformationSourceType(Long id, String name, boolean meetingDocument) {
		this(id, name, meetingDocument ? IS_A_MEETING_DOCUMENT : IS_NOT_A_MEETING_DOCUMENT);
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
	 * @return the 'meetingDocument' value
	 */
	public Character getMeetingDocument() {
		return this._meetingDocument;
	}

	/**
	 * @param meetingDocument
	 *            the 'meetingDocument' value to set
	 */
	public void setMeetingDocument(Character meetingDocument) {
		this._meetingDocument = meetingDocument == null ? IS_NOT_A_MEETING_DOCUMENT : meetingDocument;
	}

	public boolean isAMeetingDocument() {
		return IS_A_MEETING_DOCUMENT.equals(this._meetingDocument);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gcube.application.rsg.support.compiler.bridge.interfaces.reference
	 * .SerializableReferenceConcept#getSerializedForm()
	 */
	@Override
	public String getSerializedForm() {
		return SerializableConceptUtils.toString(this.id, this.name, this._meetingDocument);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gcube.application.rsg.support.compiler.bridge.interfaces.reference
	 * .SerializableReferenceConcept#fromSerializedForm(java.lang.String)
	 */
	@Override
	public InformationSourceType fromSerializedForm(String serialized) {
		String[] parts = SerializableConceptUtils.parts(serialized);

		return new InformationSourceType(parts[0] == null ? null : Long.parseLong(parts[0]), parts[1],
				parts[2].charAt(0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this._meetingDocument;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		InformationSourceType other = (InformationSourceType) obj;
		if (this._meetingDocument != other._meetingDocument) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
