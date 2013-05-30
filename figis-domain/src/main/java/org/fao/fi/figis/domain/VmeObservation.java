package org.fao.fi.figis.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

public class VmeObservation {

	@Column(name = "CD_VME", nullable = false)
	@MapsId
	@EmbeddedId
	private int parentId;

	@Column(name = "CD_OBSERVATION", nullable = false)
	@OneToMany(mappedBy = "rfmo")
	@MapsId
	@EmbeddedId
	Observation observation;

	@Column(name = "REPORTING_YEAR", nullable = false)
	String reportingYear;

}
