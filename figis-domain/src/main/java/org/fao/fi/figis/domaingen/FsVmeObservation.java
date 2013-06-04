package org.fao.fi.figis.domaingen;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the FS_VME_OBSERVATION database table.
 * 
 */
@Entity
@Table(name = "FS_VME_OBSERVATION")
public class FsVmeObservation implements Serializable {
	private static final long serialVersionUID = 1L;

	// bi-directional many-to-one association to RefVme
	@ManyToOne
	@JoinColumn(name = "CD_VME", nullable = false)
	private RefVme refVme;

	// bi-directional one-to-one association to FsObservation
	@OneToOne
	@JoinColumn(name = "CD_OBSERVATION", nullable = false)
	private FsObservation fsObservation;

	public FsVmeObservation() {
	}

	public RefVme getRefVme() {
		return this.refVme;
	}

	public void setRefVme(RefVme refVme) {
		this.refVme = refVme;
	}

	public FsObservation getFsObservation() {
		return this.fsObservation;
	}

	public void setFsObservation(FsObservation fsObservation) {
		this.fsObservation = fsObservation;
	}

}