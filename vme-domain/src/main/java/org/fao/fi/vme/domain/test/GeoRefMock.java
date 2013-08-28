package org.fao.fi.vme.domain.test;

import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.Vme;

public class GeoRefMock {

	// public static final Long ID = 578l;

	public static final String geographicFeatureID = "jfiopf7ye89wf6n2349";

	public static GeoRef create() {

		Vme vme = VmeMock.create();

		vme.getGeoRefList().get(0).setGeographicFeatureID(geographicFeatureID);

		return vme.getGeoRefList().get(0);

		// GeoRef geoRef = new GeoRef();
		// // geoRef.setId(ID);
		// geoRef.setYear(VmeMock.YEAR);
		// geoRef.setGeographicFeatureID(geographicFeatureID);
		// return geoRef;

	}
}
