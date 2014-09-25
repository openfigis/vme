package org.fao.fi.vme.dto.test;

import static org.junit.Assert.assertTrue;

import org.fao.fi.vme.webservice.SpecificMeasureType;
import org.junit.Test;

public class SpecificMeasureTypeMockTest {

	@Test
	public void testCreate() {
		SpecificMeasureType smt = SpecificMeasureTypeMock.create();
		assertTrue(10 == smt.getId());
	}

}
