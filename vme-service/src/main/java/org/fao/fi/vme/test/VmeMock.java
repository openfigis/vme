package org.fao.fi.vme.test;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.Vme;

public class VmeMock {

	public final static Long ID = 1000l;
	public final static int YEAR = 2013;

	public static Vme create() {

		Vme vme = new Vme();
		vme.setValidityPeriod(ValidityPeriodMock.create());
		vme.setId(ID);

		GeoRef g = new GeoRef();
		g.setYear(YEAR);
		List<GeoRef> l = new ArrayList<GeoRef>();
		l.add(g);
		vme.setGeoRefList(l);
		return vme;

	}
}
