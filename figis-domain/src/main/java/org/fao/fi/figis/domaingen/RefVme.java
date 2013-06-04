package org.fao.fi.figis.domaingen;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the REF_VME database table.
 * 
 */
@Entity
@Table(name = "REF_VME")
public class RefVme implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_VME", unique = true, nullable = false, precision = 10)
	private long cdVme;

	// bi-directional many-to-many association to FsObservation
	@ManyToMany
	@JoinTable(name = "FS_VME_OBSERVATION", joinColumns = { @JoinColumn(name = "CD_VME", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "CD_OBSERVATION", nullable = false) })
	private List<FsObservation> fsObservations;

	// bi-directional many-to-one association to FsVmeObservation
	@OneToMany(mappedBy = "refVme")
	private List<FsVmeObservation> fsVmeObservations;

	public RefVme() {
	}

	public long getCdVme() {
		return this.cdVme;
	}

	public void setCdVme(long cdVme) {
		this.cdVme = cdVme;
	}

	public List<FsObservation> getFsObservations() {
		return this.fsObservations;
	}

	public void setFsObservations(List<FsObservation> fsObservations) {
		this.fsObservations = fsObservations;
	}

	public List<FsVmeObservation> getFsVmeObservations() {
		return this.fsVmeObservations;
	}

	public void setFsVmeObservations(List<FsVmeObservation> fsVmeObservations) {
		this.fsVmeObservations = fsVmeObservations;
	}

	public FsVmeObservation addFsVmeObservation(FsVmeObservation fsVmeObservation) {
		getFsVmeObservations().add(fsVmeObservation);
		fsVmeObservation.setRefVme(this);

		return fsVmeObservation;
	}

	public FsVmeObservation removeFsVmeObservation(FsVmeObservation fsVmeObservation) {
		getFsVmeObservations().remove(fsVmeObservation);
		fsVmeObservation.setRefVme(null);

		return fsVmeObservation;
	}

}