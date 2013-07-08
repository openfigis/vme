package org.fao.fi.figis.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VmeObservationPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5216398903927284437L;

	@Column(name = "CD_OBSERVATION")
	private Long observationId;

	@Column(name = "CD_VME")
	private Long vmeId;

	@Column(name = "REPORTING_YEAR")
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
