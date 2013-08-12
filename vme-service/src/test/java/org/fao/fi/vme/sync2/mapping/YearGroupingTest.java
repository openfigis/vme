package org.fao.fi.vme.sync2.mapping;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.interfacee.YearObject;
import org.fao.fi.vme.domain.test.VmeMock;
import org.junit.Test;

public class YearGroupingTest {

	YearGrouping yg = new YearGrouping();

	@Test
	public void testCollect() {
		for (int i = 1; i < 4; i++) {
			Vme vme = VmeMock.generateVme(i);
			Map<Integer, List<YearObject<?>>> map = yg.collect(vme);
			assertEquals(i, map.size());
			assertEquals(6, map.get(VmeMock.YEAR).size());
		}
	}

}
