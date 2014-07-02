package org.fao.fi.vme.domain.test;

import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Vme;

public class GeoRefMock {

	// public static final Long ID = 578l;

	public static final String geographicFeatureID = "jfiopf7ye89wf6n2349";

	public static GeoRef create() {

		Vme vme = VmeMock.create();

		vme.getGeoRefList().get(0).setGeographicFeatureID(geographicFeatureID);

		return vme.getGeoRefList().get(0);

	}
}
