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

	private List<ObservationDomain> observationList;

	public RefVme getRefVme() {
		return refVme;
	}

	public void setRefVme(RefVme refVme) {
		this.refVme = refVme;
	}

	public List<ObservationDomain> getObservationDomainList() {
		return observationList;
	}

	public void setObservationDomainList(List<ObservationDomain> observationList) {
		this.observationList = observationList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((observationList == null) ? 0 : observationList.hashCode());
		result = prime * result + ((refVme == null) ? 0 : refVme.hashCode());
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
		VmeObservationDomain other = (VmeObservationDomain) obj;
		if (observationList == null) {
			if (other.observationList != null)
				return false;
		} else if (!observationList.equals(other.observationList))
			return false;
		if (refVme == null) {
			if (other.refVme != null)
				return false;
		} else if (!refVme.equals(other.refVme))
			return false;
		return true;
	}

}
