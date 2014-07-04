package org.fao.fi.vme.domain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Test;

public class VmeMockTest {

	private Vme vmeSimple = VmeMock.create();
	private List<Vme> vmeSimpleList = VmeMock.create3();
	private Vme vmeGeek = VmeMock.generateVme(3);
	private MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
	

	@Test
	@SuppressWarnings("deprecation")
	public void testCreate() {
		assertEquals("Hard Corner Bugs ", UTIL.getEnglish(vmeSimple.getName()));
		assertEquals("Southern Pacific Ocean", UTIL.getEnglish(vmeSimple.getGeoArea()));
		assertTrue(100 == vmeSimple.getValidityPeriod().getBeginDate().getYear());
		assertTrue(103 == vmeSimple.getValidityPeriod().getEndDate().getYear());
		assertTrue(4 == vmeSimple.getGeoRefList().size());
		assertTrue(150 == vmeSimple.getAreaType());
	}


	@Test
	@SuppressWarnings("deprecation")
	public void testCreate3() {
		for (Vme vme : vmeSimpleList) {
			assertEquals("Hard Corner Bugs ", UTIL.getEnglish(vme.getName()));
			assertEquals("Southern Pacific Ocean", UTIL.getEnglish(vme.getGeoArea()));
			assertTrue(100 == vme.getValidityPeriod().getBeginDate().getYear());
			assertTrue(103 == vme.getValidityPeriod().getEndDate().getYear());
			assertTrue(4 == vme.getGeoRefList().size());
			assertTrue(150 == vme.getAreaType());
		}
	}

	@Test
	@SuppressWarnings("deprecation")
	public void testGenerateVme() {
		assertTrue(3 == vmeGeek.getCriteria().size());
		assertTrue(9 == vmeGeek.getGeoRefList().size());
		assertTrue(3 == vmeGeek.getProfileList().size());
		assertTrue(3 == vmeGeek.getSpecificMeasureList().size());
		assertTrue(100 == vmeGeek.getValidityPeriod().getBeginDate().getYear());
		assertTrue(102 == vmeGeek.getValidityPeriod().getEndDate().getYear());
	}

}
