package org.fao.fi.vme.domain.model;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GeoRefTest {

	@Test
	public void testEqualsObject() {

		Vme vme = new Vme();
		// vme.setId(3l);
		GeoRef g1 = new GeoRef();
		// g1.setId(1l);
		g1.setVme(vme);
		GeoRef g2 = new GeoRef();
		g1.setId(2l);
		g2.setVme(vme);

		List<GeoRef> gl = new ArrayList<GeoRef>();
		gl.add(g1);
		gl.add(g2);
		vme.setGeoRefList(gl);

		assertFalse(g1.equals(g2));

	}

}
