package org.vme.service.tabular.record;

import java.util.List;

import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.domain.model.MultiLingualString;

public class VmeContainer {

	private MultiLingualString name;

	public MultiLingualString getName() {
		return name;
	}

	private List<VmeObservation> observations;

	public VmeContainer(MultiLingualString name, List<VmeObservation> observations) {
		this.observations = observations;
		this.name = name;
	}

	public List<VmeObservation> findVmeObservationByVme() {
		return observations;
	}

}
