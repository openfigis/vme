package org.vme.dao.impl.jpa;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

public class VmeRelevantYearsTest {

	VmeRelevantYears y = new VmeRelevantYears();

	@Test
	public void testCalculate() {

		assertEquals(Calendar.getInstance().get(Calendar.YEAR), y.calculate()[0], 1);

	}

}
