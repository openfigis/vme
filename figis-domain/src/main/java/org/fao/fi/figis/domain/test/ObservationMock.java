package org.fao.fi.figis.domain.test;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.rule.RtmsVme;

public class ObservationMock {

	public static Observation create() {
		Observation o = new Observation();
		o.setOrder(RtmsVme.ORDER);
		o.setCollection(RtmsVme.COLLECTION);
		return o;
	}

}
