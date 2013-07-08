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

	/**
	 * vme1 plain vme; vme2 is another with 2 observations for 2 years; vme3 is
	 * 
	 * 
	 * 
	 * @return list with 3 vmes
	 */
	public static List<Vme> create3() {
		List<Vme> l = new ArrayList<Vme>();
		Vme vme1 = create();
		Vme vme2 = create();

		vme2.setId(ID + 1);
		vme2.getGeoRefList().get(0).setYear(YEAR + 1);

		l.add(vme1);
		l.add(vme2);
		return l;
	}
}
