package org.fao.fi.vme.batch.sync2.mapping;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.junit.Test;

public class CalculateBeginYearRuleTest {

	CalculateBeginYearRule c = new CalculateBeginYearRule();

	@Test
	public void testCalculate() {
		Vme vme = new Vme();
		vme.setRfmo(new Rfmo());

		int year = 2000;

		vme.setScope(10l);

		SpecificMeasure s2000 = new SpecificMeasure();
		s2000.setYear(year);
		s2000.setVme(vme);
		s2000.setValidityPeriod(ValidityPeriodMock.create(2001, 2001));

		List<SpecificMeasure> smList = new ArrayList<SpecificMeasure>();
		smList.add(s2000);

		vme.setValidityPeriod(ValidityPeriodMock.create(2001, 2002));
		vme.setSpecificMeasureList(smList);

		assertEquals(year, c.calculate(vme));

	}
}
