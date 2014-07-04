package org.fao.fi.vme.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.fao.fi.vme.domain.support.ValidityPeriodUtil;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Before;
import org.junit.Test;

public class SpecificMeasureTest {

	private SpecificMeasure sm1;
	private SpecificMeasure sm2;
	private static int YEAR = 2000;
	private MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
	private ValidityPeriodUtil VUTIL = new ValidityPeriodUtil();
	
	@Before
	public void before(){
		sm1 = new SpecificMeasure();

		sm1.setYear(YEAR);
		sm1.setVmeSpecificMeasure(UTIL.english("A specific measure for the year " + YEAR));
		sm1.setValidityPeriod(ValidityPeriodMock.create(YEAR, YEAR + 1));
		sm1.setInformationSource(InformationSourceMock.create());
		sm1.setReviewYear(YEAR + 1);
		sm1.setVme(VmeMock.create());
		
		sm2 = new SpecificMeasure();

		sm2.setYear(YEAR + 1);
		sm2.setVmeSpecificMeasure(UTIL.english("A specific measure for the year " + YEAR++));
		sm2.setValidityPeriod(ValidityPeriodMock.create(YEAR, YEAR + 1));
		sm2.setInformationSource(InformationSourceMock.create());
		sm2.setReviewYear(YEAR + 2);
		sm2.setVme(VmeMock.generateVme(3));
	}
	
	@Test
	public void testHashCode() {
		assertTrue(sm1.hashCode()!=sm2.hashCode());
	}

	@Test
	public void testSetId() {
		sm2.setId(2000L);
		assertTrue(2000 == sm2.getId());
	}

	@Test
	public void testGetVme() {
		assertEquals("Hard Corner Bugs ", UTIL.getEnglish(sm1.getVme().getName()));
	}

	@Test
	public void testSetVme() {
		sm2.setVme(VmeMock.create());
		assertEquals("Hard Corner Bugs ", UTIL.getEnglish(sm2.getVme().getName()));
	}

	@Test
	public void testGetInformationSource() {
		assertEquals("This is an abstract (report summary)", UTIL.getEnglish(sm1.getInformationSource().getReportSummary()));
	}

	@Test
	public void testSetInformationSource() {
		sm2.setInformationSource(InformationSourceMock.create());
		assertEquals("This is an abstract (report summary)", UTIL.getEnglish(sm1.getInformationSource().getReportSummary()));
	}

	@Test
	public void testGetValidityPeriod() {
		assertTrue(2006 == VUTIL.getBeginYear(sm1.getValidityPeriod()));
	}

	@Test
	public void testSetValidityPeriod() {
		ValidityPeriod vp = ValidityPeriodMock.create(2001, 2009);
		sm2.setValidityPeriod(vp);
		assertTrue(2001 == VUTIL.getBeginYear(sm2.getValidityPeriod()));
		assertTrue(2009 == VUTIL.getEndYear(sm2.getValidityPeriod()));
	}

	@Test
	public void testGetYear() {
		assertTrue(2003 == sm1.getYear());
	}

	@Test
	public void testSetYear() {
		sm2.setYear(2005);
		assertTrue(2005 == sm2.getYear());
	}

	@Test
	public void testGetVmeSpecificMeasure() {
		assertEquals("A specific measure for the year 2007",  UTIL.getEnglish(sm1.getVmeSpecificMeasure()));
	}

	@Test
	public void testSetVmeSpecificMeasure() {
		sm2.setVmeSpecificMeasure(UTIL.english("Foo specific measure for the year 2001"));
		assertEquals("Foo specific measure for the year 2001",  UTIL.getEnglish(sm2.getVmeSpecificMeasure()));
	}

	@Test
	public void testGetReviewYear() {
		assertTrue(2005 == sm1.getReviewYear());
	}

	@Test
	public void testSetReviewYear() {
		sm2.setReviewYear(2004);
		assertTrue(2004 == sm2.getReviewYear());
	}

	@Test
	public void testEqualsObject() {
		assertNotEquals(sm1, sm2);
	}

}
