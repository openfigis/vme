package org.fao.fi.vme.sync2.mapping;

import java.util.List;
import java.util.Map;

import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.YearObject;
import org.fao.fi.vme.test.VmeMock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class YearGroupingTest {

	YearGrouping yg = new YearGrouping();

	@Test
	public void testCollect() {
		// int year = 2012;
		// Vme vme = generateVme(year);
		Vme vme = VmeMock.generateVme(1);
		Map<Integer, List<YearObject<?>>> map = yg.collect(vme);
		assertEquals(1, map.size());
		assertEquals(6, map.get(VmeMock.YEAR).size());
	}

}
