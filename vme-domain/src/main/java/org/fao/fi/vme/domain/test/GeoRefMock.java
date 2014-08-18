package org.fao.fi.vme.domain.test;

import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Vme;

public class GeoRefMock {

	public static final String GEOGRAPHICFEATUREID = "jfiopf7ye89wf6n2349";

	private GeoRefMock(){
		
	}
	
	public static GeoRef create() {

		Vme vme = VmeMock.create();

		vme.getGeoRefList().get(0).setGeographicFeatureID(GEOGRAPHICFEATUREID);

		return vme.getGeoRefList().get(0);

	}
}
