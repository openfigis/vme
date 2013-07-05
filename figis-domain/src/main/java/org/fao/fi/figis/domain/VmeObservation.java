package org.fao.fi.figis.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the FS_VME_OBSERVATION database table.
 * 
 */
@Entity
@Table(name = "FS_VME_OBSERVATION", schema = "figis")
public class VmeObservation implements Serializable {

	/**
	 * 
	 */
	private static final Long serialVersionUID = 2268197859547174188L;

	/**
	 * In figis terms, this is the oid.
	 * 
	 * 
	 */
	@Id
	@Column(name = "CD_OBSERVATION")
	private Long observationId;
	// private Observation observation;

	// bi-directional many-to-one association to RefVme
	// @ManyToOne
	// @PrimaryKeyJoinColumn(name = "CD_VME")
	@Column(name = "CD_VME", nullable = false)
	private Long vmeId;
	// private RefVme refVme;

	@Column(name = "REPORTING_YEAR", nullable = true, length = 10)
	private String reportingYear;

	public Long getObservationId() {
		return observationId;
	}

	public void setObservationId(long observationId) {
		this.observationId = observationId;
	}

	public Long getVmeId() {
		return vmeId;
	}

	public void setVmeId(Long vmeId) {
		this.vmeId = vmeId;
	}

	public String getReportingYear() {
		return reportingYear;
	}

	public void setReportingYear(String reportingYear) {
		this.reportingYear = reportingYear;
	}

}