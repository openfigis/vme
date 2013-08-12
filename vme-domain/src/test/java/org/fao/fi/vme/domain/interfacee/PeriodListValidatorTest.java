package org.fao.fi.vme.domain.interfacee;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.ValidityPeriod;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.junit.Test;

public class PeriodListValidatorTest {

	PeriodListValidator v = new PeriodListValidator();

	@Test
	public void testValidate() {
		// same fails
		testFail(createList(2010, 2011, 2010, 2011));

		// overlap fails
		testFail(createList(2010, 2011, 2011, 2012));

		// invalid individual validityPeriods fails
		testFail(createList(2009, 2008, 2010, 2010));
		testFail(createList(2010, 2010, 2015, 2014));

		// earlier fails
		testFail(createList(2010, 2011, 2008, 2009));

		// after each other is valid
		v.validate(createList(2010, 2011, 2012, 2013));
	}

	private void testFail(List<Period> sortedPeriods) {
		try {
			v.validate(sortedPeriods);
			fail();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private List<Period> createList(int i, int j, int k, int l) {
		ValidityPeriod vp1 = ValidityPeriodMock.create(i, j);
		ValidityPeriod vp2 = ValidityPeriodMock.create(k, l);
		Vme vme1 = new Vme();
		vme1.setValidityPeriod(vp1);
		Vme vme2 = new Vme();
		vme2.setValidityPeriod(vp2);
		List<Period> sortedPeriods = new ArrayList<Period>();
		sortedPeriods.add(vme1);
		sortedPeriods.add(vme2);
		return sortedPeriods;

	}
}
