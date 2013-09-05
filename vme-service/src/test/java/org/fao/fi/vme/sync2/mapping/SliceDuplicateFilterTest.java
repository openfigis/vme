package org.fao.fi.vme.sync2.mapping;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.domain.test.GeneralMeasureMock;
import org.junit.Test;

public class SliceDuplicateFilterTest extends SliceDuplicateFilter {

	@Test
	public void testFilter() {

		List<DisseminationYearSlice> slices = createSlices();

		this.filter(slices);
		assertEquals(1, slices.size());

		slices = createSlices();

		slices.get(0).setGeneralMeasure(GeneralMeasureMock.create());
		slices.get(1).setGeneralMeasure(GeneralMeasureMock.create());
		this.filter(slices);
		assertEquals(1, slices.size());

		slices = createSlices();
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
		assertEquals(1, slices.size());

	}

	List<DisseminationYearSlice> createSlicesWithInformationSource() {
		List<DisseminationYearSlice> slices = createSlices();
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
		slices.addAll(createSlices());
		slices.addAll(createSlices());
		return slices;
	}

	List<DisseminationYearSlice> createSlices() {

		DisseminationYearSlice s1 = new DisseminationYearSlice();
		DisseminationYearSlice s2 = new DisseminationYearSlice();

		List<DisseminationYearSlice> slices = new ArrayList<DisseminationYearSlice>();
		slices.add(s1);
		slices.add(s2);

		return slices;

	}

}
