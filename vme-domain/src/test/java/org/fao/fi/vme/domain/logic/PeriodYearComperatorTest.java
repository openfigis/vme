package org.fao.fi.vme.domain.logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.interfacee.PeriodYear;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.junit.Test;

public class PeriodYearComperatorTest {

	@Test
	public void testCompare1() {
		PeriodYearComperator pyc = new PeriodYearComperator();

		PeriodYear py1 = new SpecificMeasures();
		py1.setYear(2000);
		py1.setValidityPeriod(ValidityPeriodMock.create());
		PeriodYear py2 = new SpecificMeasures();
		py2.setYear(2000);
		py2.setValidityPeriod(ValidityPeriodMock.create());
		assertEquals(0, pyc.compare(py1, py2));

		py2.setYear(2001);

		assertEquals(-1, pyc.compare(py1, py2));
		assertEquals(1, pyc.compare(py2, py1));

	}

	@Test
	public void testCompare2() {

		// vp = 2000-2003
		SpecificMeasures sm1 = new SpecificMeasures();
		sm1.setYear(2002);
		sm1.setValidityPeriod(ValidityPeriodMock.create(2003, 2003));

		SpecificMeasures sm2 = new SpecificMeasures();
		sm2.setYear(2001);
		sm2.setValidityPeriod(ValidityPeriodMock.create(2002, 2003));
		List<SpecificMeasures> l = new ArrayList<SpecificMeasures>();
		l.add(sm1);
		l.add(sm2);
		Collections.sort(l, new PeriodYearComperator());
		assertEquals(sm2, l.get(0));
		assertEquals(sm1, l.get(1));

	}
}
