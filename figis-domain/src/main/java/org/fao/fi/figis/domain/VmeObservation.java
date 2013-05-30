package org.fao.fi.figis.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

public class VmeObservation {

	@MapsId
	@EmbeddedId
	RefVme refVme;

	@OneToMany(mappedBy = "rfmo")
	@MapsId
	@EmbeddedId
	Observation observation;

	@Column(name = "REPORTING_YEAR", nullable = false)
	String reportingYear;

}
