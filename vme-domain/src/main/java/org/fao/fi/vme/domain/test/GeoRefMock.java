package org.fao.fi.vme.domain.test;

import org.fao.fi.vme.domain.GeoRef;

public class GeoRefMock {

	public static final Long ID = 578l;
	public static final String geographicFeatureID = "jfiopf7ye89wf6n2349";

	public static GeoRef create() {

		GeoRef geoRef = new GeoRef();
		// geoRef.setId(ID);
		geoRef.setYear(VmeMock.YEAR);
		geoRef.setGeographicFeatureID(geographicFeatureID);
		return geoRef;

	}

}
