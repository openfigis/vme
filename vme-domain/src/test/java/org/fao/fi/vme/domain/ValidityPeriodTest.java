package org.fao.fi.vme.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.junit.Test;

public class ValidityPeriodTest {

	@Test
	public void testCompareTo() {

		ValidityPeriod vp1 = ValidityPeriodMock.create(2010, 2011);
		ValidityPeriod vp2 = ValidityPeriodMock.create(2010, 2011);
		// same
		assertEquals(0, vp1.compareTo(vp2));

		// vp1 is less
		vp2 = ValidityPeriodMock.create(2012, 2013);
		assertTrue(vp1.compareTo(vp2) < 0);

		// vp1 is more
		vp2 = ValidityPeriodMock.create(2008, 2009);
		assertTrue(vp1.compareTo(vp2) > 0);

		List<ValidityPeriod> l = new ArrayList<ValidityPeriod>();
		l.add(vp1);
		l.add(vp2);
		Collections.sort(l);
		assertEquals(vp2, l.get(0));
		assertEquals(vp1, l.get(1));

	}
}
