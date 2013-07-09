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

	public void setObservationId(Long observationId) {
		this.observationId = observationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((observationId == null) ? 0 : observationId.hashCode());
		result = prime * result + ((reportingYear == null) ? 0 : reportingYear.hashCode());
		result = prime * result + ((vmeId == null) ? 0 : vmeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VmeObservationPk other = (VmeObservationPk) obj;
		if (observationId == null) {
			if (other.observationId != null)
				return false;
		} else if (!observationId.equals(other.observationId))
			return false;
		if (reportingYear == null) {
			if (other.reportingYear != null)
				return false;
		} else if (!reportingYear.equals(other.reportingYear))
			return false;
		if (vmeId == null) {
			if (other.vmeId != null)
				return false;
		} else if (!vmeId.equals(other.vmeId))
			return false;
		return true;
	}

}
