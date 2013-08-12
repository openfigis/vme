package org.fao.fi.vme.domain.logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.interfacee.Valid;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.junit.Test;

public class SlicerTest {

	Slicer slicer = new Slicer();
	int disseminationYear = 2010;

	@Test
	public void testSliceBefore() {
		assertEquals(1, slicer.slice(disseminationYear, create(2000, 2001)).size());
	}

	@Test
	public void testSliceBeforeInclusive() {
		assertEquals(1, slicer.slice(disseminationYear, create(2000, disseminationYear)).size());
	}

	@Test
	public void testSliceAfterInclusive() {
		assertEquals(1, slicer.slice(disseminationYear, create(disseminationYear, 2020)).size());
	}

	@Test
	public void testSliceAfter() {
		assertEquals(0, slicer.slice(disseminationYear, create(2011, 2020)).size());
	}

	@Test
	public void testSliceMultiple() {
		// create inclusive one
		List<Valid> vList = create(disseminationYear, 2020);

		// add future one
		Vme vme = new Vme();
		vme.setValidityPeriod(ValidityPeriodMock.create(2011, 2020));
		vList.add(vme);

		// slice
		assertEquals(1, slicer.slice(disseminationYear, vList).size());
	}

	protected List<Valid> create(Integer beginYear, Integer endYear) {
		Vme vme = new Vme();
		vme.setValidityPeriod(ValidityPeriodMock.create(beginYear, endYear));
		List<Valid> collection = new ArrayList<Valid>();
		collection.add(vme);
		return collection;
	}

}
