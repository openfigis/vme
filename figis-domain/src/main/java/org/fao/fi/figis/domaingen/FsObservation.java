package org.fao.fi.figis.domaingen;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FS_OBSERVATION database table.
 * 
 */
@Entity
@Table(name="FS_OBSERVATION")
public class FsObservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_OBSERVATION", unique=true, nullable=false, precision=10)
	private long cdObservation;

	@Column(name="CD_COLLECTION", nullable=false, precision=10)
	private BigDecimal cdCollection;

	@Column(name="FG_PRIMARY", nullable=false, precision=1)
	private BigDecimal fgPrimary;

	@Column(name="FG_REFERENCE", nullable=false, precision=1)
	private BigDecimal fgReference;

	//bi-directional many-to-one association to FsObservationXml
	@OneToMany(mappedBy="fsObservation")
	private List<FsObservationXml> fsObservationXmls;

	//bi-directional many-to-many association to RefVme
	@ManyToMany(mappedBy="fsObservations")
	private List<RefVme> refVmes;

	//bi-directional one-to-one association to FsVmeObservation
	@OneToOne(mappedBy="fsObservation")
	private FsVmeObservation fsVmeObservation;

	public FsObservation() {
	}

	public long getCdObservation() {
		return this.cdObservation;
	}

	public void setCdObservation(long cdObservation) {
		this.cdObservation = cdObservation;
	}

	public BigDecimal getCdCollection() {
		return this.cdCollection;
	}

	public void setCdCollection(BigDecimal cdCollection) {
		this.cdCollection = cdCollection;
	}

	public BigDecimal getFgPrimary() {
		return this.fgPrimary;
	}

	public void setFgPrimary(BigDecimal fgPrimary) {
		this.fgPrimary = fgPrimary;
	}

	public BigDecimal getFgReference() {
		return this.fgReference;
	}

	public void setFgReference(BigDecimal fgReference) {
		this.fgReference = fgReference;
	}

	public List<FsObservationXml> getFsObservationXmls() {
		return this.fsObservationXmls;
	}

	public void setFsObservationXmls(List<FsObservationXml> fsObservationXmls) {
		this.fsObservationXmls = fsObservationXmls;
	}

	public FsObservationXml addFsObservationXml(FsObservationXml fsObservationXml) {
		getFsObservationXmls().add(fsObservationXml);
		fsObservationXml.setFsObservation(this);

		return fsObservationXml;
	}

	public FsObservationXml removeFsObservationXml(FsObservationXml fsObservationXml) {
		getFsObservationXmls().remove(fsObservationXml);
		fsObservationXml.setFsObservation(null);

		return fsObservationXml;
	}

	public List<RefVme> getRefVmes() {
		return this.refVmes;
	}

	public void setRefVmes(List<RefVme> refVmes) {
		this.refVmes = refVmes;
	}

	public FsVmeObservation getFsVmeObservation() {
		return this.fsVmeObservation;
	}

	public void setFsVmeObservation(FsVmeObservation fsVmeObservation) {
		this.fsVmeObservation = fsVmeObservation;
	}

}