package org.fao.fi.figis.domain;

import java.util.List;

public class ObservationDomain extends Observation {

	public ObservationDomain() {
	}

	public ObservationDomain(Observation observation, String reportingYear, List<ObservationXml> observationsPerLanguage) {
		this.reportingYear = reportingYear;
		this.setCollection(observation.getCollection());
		this.setId(observation.getId());
		this.setOrder(observation.getOrder());
		this.setPrimary(observation.isPrimary());
		this.setReference(observation.isReference());
		this.setObservationsPerLanguage(observationsPerLanguage);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5334861203546105398L;

	private String reportingYear;

	public String getReportingYear() {
		return reportingYear;
	}

	public void setReportingYear(String reportingYear) {
		this.reportingYear = reportingYear;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reportingYear == null) ? 0 : reportingYear.hashCode());
		return result;
	}

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
		ObservationDomain other = (ObservationDomain) obj;
		if (reportingYear == null) {
			if (other.reportingYear != null) {
				return false;
			}
		} else if (!reportingYear.equals(other.reportingYear)) {
			return false;
		}
		return true;
	}

}
