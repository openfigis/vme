package org.fao.fi.vme.domain.logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.interfaces.Period;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.junit.Test;

/**
 * @deprecated
 * @author Erik van Ingen
 * 
 */
public class SlicerTest {

	Slicer slicer = new Slicer();
	int disseminationYear = 2010;

	/**
	 * ValidityPeriod is before the dissemationYear
	 * 
	 */
	@Test
	public void testSliceBefore() {
		assertEquals(1, slicer.slice(disseminationYear, create(2000, 2001)).size());
	}

	/**
	 * ValidityPeriod includes the dissemationYear
	 * 
	 */
	@Test
	public void testSliceBeforeInclusive() {
		assertEquals(1, slicer.slice(disseminationYear, create(2000, disseminationYear)).size());
	}

	/**
	 * ValidityPeriod includes the dissemationYear
	 * 
	 */
	@Test
	public void testSliceAfterInclusive() {
		assertEquals(1, slicer.slice(disseminationYear, create(disseminationYear, 2020)).size());
	}

	/**
	 * ValidityPeriod is after the dissemationYear
	 * 
	 */
	@Test
	public void testSliceAfter() {
		assertEquals(0, slicer.slice(disseminationYear, create(2011, 2020)).size());
	}

	@Test
	public void testSliceMultiple() {
		// create inclusive one
		List<Period<?>> vList = create(disseminationYear, 2020);

		// add future one
		Vme vme = new Vme();
		vme.setValidityPeriod(ValidityPeriodMock.create(2011, 2020));
		vList.add(vme);

		// slice
		assertEquals(1, slicer.slice(disseminationYear, vList).size());
	}

	protected List<Period<?>> create(Integer beginYear, Integer endYear) {
		Vme vme = new Vme();
		vme.setValidityPeriod(ValidityPeriodMock.create(beginYear, endYear));
		List<Period<?>> collection = new ArrayList<Period<?>>();
		collection.add(vme);
		return collection;
	}

}
