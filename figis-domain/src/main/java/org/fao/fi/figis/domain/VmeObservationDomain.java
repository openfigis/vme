package org.fao.fi.figis.domain;

import java.util.List;

/**
 * 
 * The class VmeObservation is only there to properly map to the DB. This class is there to be used by the application.
 * This class will be mapped to VmeObservation by the DAO.
 * 
 * TODO Using VmeObservationDomain is a kind of workaround. Principally it should be possible to have only
 * VmeObservation, mapped correctly to the DB with the right JPA annotations. There is work to do to find the right
 * annotations, most probalby the VmeObservationDomain needs to inherit from Observation, like is done in FIGIS.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeObservationDomain {

	private RefVme refVme;

	private String reportingYear;

	private List<Observation> observationList;

	public RefVme getRefVme() {
		return refVme;
	}

	public void setRefVme(RefVme refVme) {
		this.refVme = refVme;
	}

	public String getReportingYear() {
		return reportingYear;
	}

	public void setReportingYear(String reportingYear) {
		this.reportingYear = reportingYear;
	}

	public List<Observation> getObservationList() {
		return observationList;
	}

	public void setObservationList(List<Observation> observationList) {
		this.observationList = observationList;
	}

	/**
	 * this assumes that the observationList has always one element.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((observationList == null) ? 0 : observationList.get(0).getId().intValue());
		result = prime * result + ((refVme == null) ? 0 : refVme.hashCode());
		result = prime * result + ((reportingYear == null) ? 0 : reportingYear.hashCode());
		return result;
	}

	/**
	 * here the observation list has been ignored.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VmeObservationDomain other = (VmeObservationDomain) obj;
		if (refVme == null) {
			if (other.refVme != null)
				return false;
		} else if (!refVme.equals(other.refVme))
			return false;
		if (reportingYear == null) {
			if (other.reportingYear != null)
				return false;
		} else if (!reportingYear.equals(other.reportingYear))
			return false;
		return true;
	}

}
