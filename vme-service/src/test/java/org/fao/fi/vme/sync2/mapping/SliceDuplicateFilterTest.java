package org.fao.fi.vme.sync2.mapping;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.test.GeneralMeasureMock;
import org.junit.Test;

public class SliceDuplicateFilterTest extends SliceDuplicateFilter {

	private static int START_YEAR = 1000;

	/**
	 * there is an error in the algorithm, it generates 186 observations,
	 * instead of 197. I am trying to isolate the problem here.
	 * 
	 */

	@Test
	public void testGeoRefs() {
		List<DisseminationYearSlice> slices = createSlices(2);

		// the years in GeoRef are now just content
		GeoRef g1 = new GeoRef();
		g1.setGeographicFeatureID("1");

		GeoRef g2 = new GeoRef();
		g2.setGeographicFeatureID("2");
		slices.get(0).setGeoRef(g1);
		slices.get(1).setGeoRef(g2);
		assertEquals(2, slices.size());
		filter(slices);
		assertEquals(2, slices.size());

	}

	@Test
	public void testWith1YearsWith1YearEmpty() {
		List<DisseminationYearSlice> slices = createSlices(5);

		// the years in GeoRef are now just content
		GeoRef g1 = new GeoRef();
		g1.setYear(2000);
		GeoRef g2 = new GeoRef();
		g2.setYear(2000);
		GeoRef g3 = new GeoRef();
		g3.setYear(2001);
		GeoRef g4 = new GeoRef();
		g4.setYear(2001);
		GeoRef g5 = new GeoRef();
		g5.setYear(2001);
		slices.get(0).setGeoRef(g1);
		slices.get(1).setGeoRef(g2);
		slices.get(2).setGeoRef(g3);
		slices.get(3).setGeoRef(g4);
		slices.get(4).setGeoRef(g5);

		assertEquals(5, slices.size());
		filter(slices);
		assertEquals(2, slices.size());
		assertEquals(START_YEAR, slices.get(0).getYear());
		assertEquals(START_YEAR + 2, slices.get(1).getYear());

	}

	private List<DisseminationYearSlice> createSlices(int numberOfYears) {
		List<DisseminationYearSlice> slices = new ArrayList<DisseminationYearSlice>();
		int endYear = START_YEAR + numberOfYears;
		for (int j = START_YEAR; j < endYear; j++) {
			DisseminationYearSlice s = new DisseminationYearSlice();
			s.setYear(j);
			slices.add(s);
		}
		return slices;
	}

	@Test
	public void testFilter() {

		List<DisseminationYearSlice> slices = create2Slices();

		this.filter(slices);
		assertEquals(1, slices.size());

		slices = create2Slices();

		slices.get(0).setGeneralMeasure(GeneralMeasureMock.create());
		slices.get(1).setGeneralMeasure(GeneralMeasureMock.create());
		this.filter(slices);
		assertEquals(1, slices.size());

		slices = create2Slices();
		slices.get(0).setGeneralMeasure(GeneralMeasureMock.create());
		slices.get(0).getGeneralMeasure().setFishingAreas("ghjg");
		this.filter(slices);
		assertEquals(2, slices.size());

		// testing with lists
		slices = createSlicesWithInformationSource();
		this.filter(slices);
		assertEquals(1, slices.size());

		slices = createSlicesWithInformationSource();
		slices.get(0).getInformationSourceList().get(0).setPublicationYear(5);

		this.filter(slices);
		assertEquals(2, slices.size());

	}

	@Test
	public void testFilterWith4Equals() {
		List<DisseminationYearSlice> slices = create4Slices();
		this.filter(slices);
		// 4 equals should result in 1
		assertEquals(1, slices.size());

		slices = create4Slices();
		assertEquals(4, slices.size());

		slices.get(2).getInformationSourceList().get(0).setPublicationYear(2005);
		this.filter(slices);

		// the
		assertEquals(3, slices.size());

	}

	List<DisseminationYearSlice> createSlicesWithInformationSource() {
		List<DisseminationYearSlice> slices = create2Slices();
		InformationSource is1 = new InformationSource();
		InformationSource is2 = new InformationSource();
		List<InformationSource> isList1 = new ArrayList<InformationSource>();
		isList1.add(is1);
		List<InformationSource> isList2 = new ArrayList<InformationSource>();
		isList2.add(is2);
		slices.get(0).setInformationSourceList(isList1);
		slices.get(1).setInformationSourceList(isList2);
		return slices;
	}

	List<DisseminationYearSlice> create4Slices() {
		List<DisseminationYearSlice> slices = new ArrayList<DisseminationYearSlice>();
		slices.addAll(createSlicesWithInformationSource());
		slices.addAll(createSlicesWithInformationSource());
		return slices;
	}

	List<DisseminationYearSlice> create2Slices() {

		DisseminationYearSlice s1 = new DisseminationYearSlice();
		DisseminationYearSlice s2 = new DisseminationYearSlice();

		List<DisseminationYearSlice> slices = new ArrayList<DisseminationYearSlice>();
		slices.add(s1);
		slices.add(s2);

		return slices;

	}

}
