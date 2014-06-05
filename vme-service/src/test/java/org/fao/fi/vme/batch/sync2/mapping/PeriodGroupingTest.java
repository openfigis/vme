package org.fao.fi.vme.batch.sync2.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.support.ValidityPeriodUtil;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Test;

public class PeriodGroupingTest {

	PeriodGrouping grouping = new PeriodGrouping();
	MultiLingualStringUtil u = new MultiLingualStringUtil();
	ValidityPeriodUtil vu = new ValidityPeriodUtil();

	@Test
	public void testCollectValidation() {
		Vme vme = new Vme();
		delegate(vme);
		ValidityPeriod vp = new ValidityPeriod();
		vme.setValidityPeriod(vp);
		delegate(vme);

		vp.setBeginDate(vu.beginYear2BeginDate(100));
		delegate(vme);
		vp.setEndDate(vu.endYear2endDate(100));
	}

	private void delegate(Vme vme) {
		try {
			grouping.collect(vme);
			fail();
		} catch (Exception e) {
		}

	}

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

	@Test
	public void testCollectEndYear() {
		int numberOfYears = 2;
		Vme vme = VmeMock.generateVme(numberOfYears);
		int endYear = 1971;

		vme.getSpecificMeasureList().get(0).getValidityPeriod().setEndDate(vu.endYear2endDate(endYear));
		int endYearSecond = vu.getEndYear(vme.getSpecificMeasureList().get(1).getValidityPeriod());

		List<DisseminationYearSlice> list = grouping.collect(vme);
		assertEquals(numberOfYears, list.size());
		assertEquals(endYearSecond, vu.getEndYear(list.get(0).getSpecificMeasure().getValidityPeriod()));
		assertEquals(endYear, vu.getEndYear(list.get(1).getSpecificMeasure().getValidityPeriod()));

	}
}
