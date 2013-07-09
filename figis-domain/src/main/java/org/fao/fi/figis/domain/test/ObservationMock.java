package org.fao.fi.figis.domain.test;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.rule.Figis;

public class ObservationMock {

	public static Observation create() {
		Observation o = new Observation();
		o.setOrder(Figis.ORDER);
		o.setCollection(Figis.COLLECTION);
		return o;
	}

}
