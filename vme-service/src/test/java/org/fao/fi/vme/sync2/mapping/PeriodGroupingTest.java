package org.fao.fi.vme.sync2.mapping;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Test;

public class PeriodGroupingTest {

	PeriodGrouping grouping = new PeriodGrouping();
	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testCollect() {
		int numberOfYears = 2;
		Vme vme = VmeMock.generateVme(numberOfYears);
		List<DisseminationYearSlice> list = grouping.collect(vme);
		for (int i = 0; i < numberOfYears; i++) {
			int year = VmeMock.YEAR + i;
			System.out.println(year);
			assertEquals(year, list.get(i).getSpecificMeasure().getYear().intValue());
			assertEquals(year, list.get(i).getGeneralMeasure().getYear().intValue());
			assertEquals(year, list.get(i).getFisheryAreasHistory().getYear().intValue());
			assertEquals(year, list.get(i).getVmesHistory().getYear().intValue());
			assertEquals(year, list.get(i).getGeoRef().getYear().intValue());
			assertEquals(year, list.get(i).getProfile().getYear().intValue());
			assertEquals(vme.getRfmo().getInformationSourceList(), list.get(i).getInformationSourceList());
		}

	}
}
