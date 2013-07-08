package org.fao.fi.figis.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the FS_VME_OBSERVATION database table.
 * 
 */
@Entity
@Table(name = "FS_VME_OBSERVATION", schema = "figis")
public class VmeObservation {

	@EmbeddedId
	private VmeObservationPk id;

	public VmeObservationPk getId() {
		return id;
	}

	public void setId(VmeObservationPk id) {
		this.id = id;
	}

}