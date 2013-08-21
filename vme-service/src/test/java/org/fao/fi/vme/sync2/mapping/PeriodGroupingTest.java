package org.fao.fi.vme.sync2.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.fao.fi.vme.domain.History;
import org.fao.fi.vme.domain.Rfmo;
import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.ValidityPeriod;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.junit.Test;

public class PeriodGroupingTest {

	PeriodGrouping g = new PeriodGrouping();

	@Test
	public void testCollect() {
		int nrOfYears = 3;
		Vme vme = VmeMock.generateVme(nrOfYears);
		List<DisseminationYearSlice> slices = g.collect(vme);
		for (DisseminationYearSlice disseminationYearSlice : slices) {
			System.out.println(disseminationYearSlice.getYear());
		}
		assertEquals(ValidityPeriodMock.getNumberOfYearInclusive(), slices.size());
	}

	@Test
	public void testCollectYears() {
		Vme vme = VmeMock.create();
		vme.setRfmo(new Rfmo());
		List<DisseminationYearSlice> slices = g.collect(vme);

		assertEquals(ValidityPeriodMock.YEARS, slices.size());

	}

	@Test
	public void testOverlappingValidityPeriods() {
		Vme vme = VmeMock.create();
		vme.setRfmo(new Rfmo());

		// vp = 2000-2003
		SpecificMeasures sm1 = new SpecificMeasures();
		sm1.setYear(2002);
		sm1.setValidityPeriod(ValidityPeriodMock.create(2003, 2003));

		SpecificMeasures sm2 = new SpecificMeasures();
		sm1.setYear(2001);
		sm2.setValidityPeriod(ValidityPeriodMock.create(2002, 2003));

		List<SpecificMeasures> smList = new ArrayList<SpecificMeasures>();
		vme.setSpecificMeasuresList(smList);

		List<DisseminationYearSlice> slices = g.collect(vme);

		assertEquals(2000, slices.get(0).getYear());
		assertEquals(null, slices.get(0).getSpecificMeasures());

		assertEquals(2001, slices.get(1).getYear());
		assertEquals(null, slices.get(1).getSpecificMeasures());

		assertEquals(2002, slices.get(2).getYear());
		assertEquals(sm1, slices.get(2).getSpecificMeasures());

		assertEquals(2003, slices.get(3).getYear());
		assertEquals(sm2, slices.get(3).getSpecificMeasures());

	}

	@Test
	public void testCollect9999() {
		Vme vme = VmeMock.create();
		vme.setRfmo(new Rfmo());
		vme.getValidityPeriod().setEndYear(9999);
		int years = Calendar.getInstance().get(Calendar.YEAR) - vme.getValidityPeriod().getBeginYear();
		assertEquals(++years, g.collect(vme).size());

	}

	@Test
	public void testCollectPeriod() {
		int startYear = 2010;
		int endYear = 2011;

		ValidityPeriod vp1 = ValidityPeriodMock.create(startYear, startYear);
		ValidityPeriod vp2 = ValidityPeriodMock.create(endYear, endYear);
		SpecificMeasures sm1 = new SpecificMeasures();
		sm1.setValidityPeriod(vp1);
		SpecificMeasures sm2 = new SpecificMeasures();
		sm2.setValidityPeriod(vp2);

		List<SpecificMeasures> specificMeasureList = new ArrayList<SpecificMeasures>();
		specificMeasureList.add(sm1);
		specificMeasureList.add(sm2);

		Vme vme = VmeMock.create();
		vme.setRfmo(new Rfmo());
		ValidityPeriod vpVme = ValidityPeriodMock.create(startYear, endYear);
		vme.setValidityPeriod(vpVme);
		vme.setSpecificMeasuresList(specificMeasureList);

		List<DisseminationYearSlice> slices = g.collect(vme);
		for (DisseminationYearSlice s : slices) {
			assertNotNull(s.getSpecificMeasures());
			assertNotNull(s.getVme());
		}
		assertEquals(startYear, slices.get(0).getYear());
		assertEquals(endYear, slices.get(1).getYear());
		assertEquals(2, slices.size());
	}

	@Test
	public void testCollectHistory() {
		int startYear = 2010;
		int endYear = 2012;

		History h1 = new History();
		h1.setYear(startYear);
		History h2 = new History();
		h2.setYear(endYear);

		List<History> hList = new ArrayList<History>();
		hList.add(h1);
		hList.add(h2);

		Vme vme = VmeMock.create();
		vme.setRfmo(new Rfmo());
		ValidityPeriod vpVme = ValidityPeriodMock.create(startYear, endYear);
		vme.setValidityPeriod(vpVme);
		vme.setHistoryList(hList);

		List<DisseminationYearSlice> slices = g.collect(vme);
		for (DisseminationYearSlice s : slices) {
			assertNotNull(s.getVmeHistory());
		}
		assertEquals(3, slices.size());

		// 2010
		assertEquals(h1, slices.get(0).getVmeHistory());
		assertEquals(startYear, slices.get(0).getYear());

		// 2011
		assertEquals(h1, slices.get(1).getVmeHistory());
		assertEquals(2011, slices.get(1).getYear());

		// 2012
		assertEquals(h2, slices.get(2).getVmeHistory());
		assertEquals(endYear, slices.get(2).getYear());

	}

}
