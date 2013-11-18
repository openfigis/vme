package org.fao.fi.vme.sync2.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Test;

public class PeriodGroupingTest {

	PeriodGrouping g = new PeriodGrouping();
	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testInformationHistoryCollectingYears() {
		int nrOfYears = 2;
		Vme vme = VmeMock.generateVme(nrOfYears);
		List<DisseminationYearSlice> slices = g.collect(vme);

		assertEquals(1, slices.get(0).getInformationSourceList().size());
		assertEquals(2, slices.get(1).getInformationSourceList().size());
	}

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

	/**
	 * This is the bloody case, described by Tony, put in the Wiki by Erik
	 * 
	 * http://km.fao.org/FIGISwiki/index.php/VME_UML#Rules_for_Slicing
	 * 
	 * 
	 * TODO There is still this case to be discussed.
	 * 
	 * SpecialMeasures1.year = 2010 SpecialMeasures1.validityPeriod = 2010-2012
	 * 
	 * SpecialMeasures2.year = 2011 SpecialMeasures2.validityPeriod = 2010-2012
	 * 
	 * In the year 2010, we show SpecialMeasures2 or we show SpecialMeasures1? We do show now SpecialMeasures2 because
	 * the validityPeriod of the most recent one covers also 2010
	 * 
	 * 
	 * 
	 */
	@Test
	public void testOverlappingValidityPeriods1() {

		// SpecialMeasures1.year = 2009 SpecialMeasures1.validityPeriod = 2010-2012
		// SpecialMeasures2.year = 2010 SpecialMeasures2.validityPeriod = 2011-2012

		ValidityPeriod vp1 = ValidityPeriodMock.create(2010, 2012);
		ValidityPeriod vp2 = ValidityPeriodMock.create(2011, 2012);

		Vme vme = VmeMock.create();
		vme.setRfmo(new Rfmo());
		vme.setValidityPeriod(vp1);

		SpecificMeasure sm1 = new SpecificMeasure();
		sm1.setYear(2009);
		sm1.setValidityPeriod(vp1);
		sm1.setVmeSpecificMeasure(u.english("go sado masochistic1"));

		SpecificMeasure sm2 = new SpecificMeasure();
		sm2.setYear(2010);
		sm2.setValidityPeriod(vp2);
		sm2.setVmeSpecificMeasure(u.english("go sado masochistic2"));

		List<SpecificMeasure> smList = new ArrayList<SpecificMeasure>();
		smList.add(sm1);
		smList.add(sm2);

		vme.setSpecificMeasureList(smList);

		List<DisseminationYearSlice> slices = g.collect(vme);

		// d=2008: Nothing will be shown
		// d=2009:Nothing will be shown
		// d=2010:SpecialMeasures1 will be shown
		// d=2011: SpecialMeasures2 will be shown
		// d=2012: SpecialMeasures2 will be shown
		// d=2013: Nothing will be shown

		assertEquals(3, slices.size());

		assertEquals(2010, slices.get(0).getYear());
		assertEquals(sm1, slices.get(0).getSpecificMeasures());

		assertEquals(2011, slices.get(1).getYear());
		assertEquals(sm2, slices.get(1).getSpecificMeasures());

		assertEquals(2012, slices.get(2).getYear());
		assertEquals(sm2, slices.get(2).getSpecificMeasures());

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
		SpecificMeasure sm1 = new SpecificMeasure();
		sm1.setValidityPeriod(vp1);
		sm1.setVmeSpecificMeasure(u.english("go sado masochistic1"));
		SpecificMeasure sm2 = new SpecificMeasure();
		sm2.setValidityPeriod(vp2);
		sm2.setVmeSpecificMeasure(u.english("go sado masochistic2"));

		List<SpecificMeasure> specificMeasureList = new ArrayList<SpecificMeasure>();
		specificMeasureList.add(sm1);
		specificMeasureList.add(sm2);

		Vme vme = VmeMock.create();
		vme.setRfmo(new Rfmo());
		ValidityPeriod vpVme = ValidityPeriodMock.create(startYear, endYear);
		vme.setValidityPeriod(vpVme);
		vme.setSpecificMeasureList(specificMeasureList);

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

		GeoRef h1 = new GeoRef();
		h1.setYear(startYear);
		GeoRef h2 = new GeoRef();
		h2.setYear(endYear);

		List<GeoRef> hList = new ArrayList<GeoRef>();
		hList.add(h1);
		hList.add(h2);

		Vme vme = VmeMock.create();
		vme.setRfmo(new Rfmo());
		ValidityPeriod vpVme = ValidityPeriodMock.create(startYear, endYear);
		vme.setValidityPeriod(vpVme);
		vme.setGeoRefList(hList);

		List<DisseminationYearSlice> slices = g.collect(vme);
		assertEquals(3, slices.size());

		// 2010
		assertEquals(h1, slices.get(0).getGeoRef());
		assertEquals(startYear, slices.get(0).getYear());

		// 2011
		assertEquals(h1, slices.get(1).getGeoRef());
		assertEquals(2011, slices.get(1).getYear());

		// 2012
		assertEquals(h2, slices.get(2).getGeoRef());
		assertEquals(endYear, slices.get(2).getYear());

	}

}
