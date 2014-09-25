package org.fao.fi.vme.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Before;
import org.junit.Test;

public class ProfileTest {

	private Profile p1;
	private Profile p2;
	private MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
	
	@Before
	public void before(){
		p1 = new Profile();
		p2 = new Profile();
		p1.setDescriptionBiological(UTIL.english("descriptionBiological"));
		p2.setDescriptionBiological(UTIL.english("descriptionBiological2"));
		p1.setDescriptionImpact(UTIL.english("descriptionImpact"));
		p2.setDescriptionImpact(UTIL.english("descriptionImpact2"));
		p1.setDescriptionPhisical(UTIL.english("descriptionPhisical"));
		p2.setDescriptionPhisical(UTIL.english("descriptionPhisical2"));
		p1.setGeoform(UTIL.english("geoform"));
		p2.setGeoform(UTIL.english("geoform2"));
		p1.setVme(VmeMock.create());
		p2.setVme(VmeMock.generateVme(3));
		p1.setYear(1999);
		p2.setYear(2001);
	}
	
	@Test
	public void testHashCode() {
		assertTrue(p1.hashCode()!=p2.hashCode());
	}

	@Test
	public void testGetId() {
		if(p1.getId()!=null){
			long expected = p1.getId();
			assertTrue(expected == p1.getId());
		}
	}

	@Test
	public void testSetId() {
		p2.setId(2000L);
		assertTrue(2000==p2.getId());
	}

	@Test
	public void testGetYear() {
		assertTrue(1999 == p1.getYear());
	}

	@Test
	public void testSetYear() {
		p2.setYear(2002);
		assertTrue(2002 == p2.getYear());
	}

	@Test
	public void testGetVme() {
		assertEquals("Hard Corner Bugs ", UTIL.getEnglish(p1.getVme().getName()));
	}

	@Test
	public void testSetVme() {
		Vme v = VmeMock.create();
		v.setName(UTIL.english("Foo name for vme"));
		p2.setVme(v);
		assertEquals("Foo name for vme", UTIL.getEnglish(p2.getVme().getName()));
	}

	@Test
	public void testGetDescriptionPhisical() {
		assertEquals("descriptionPhisical", UTIL.getEnglish(p1.getDescriptionPhisical()));
	}

	@Test
	public void testSetDescriptionPhisical() {
		p2.setDescriptionPhisical(UTIL.english("3"));
		assertEquals("3", UTIL.getEnglish(p2.getDescriptionPhisical()));
	}

	@Test
	public void testGetDescriptionBiological() {
		assertEquals("descriptionBiological", UTIL.getEnglish(p1.getDescriptionBiological()));
	}

	@Test
	public void testSetDescriptionBiological() {
		p2.setDescriptionBiological(UTIL.english("3"));
		assertEquals("3", UTIL.getEnglish(p2.getDescriptionBiological()));
	}

	@Test
	public void testGetDescriptionImpact() {
		assertEquals("descriptionImpact", UTIL.getEnglish(p1.getDescriptionImpact()));
	}

	@Test
	public void testSetDescriptionImpact() {
		p2.setDescriptionImpact(UTIL.english("3"));
		assertEquals("3", UTIL.getEnglish(p2.getDescriptionImpact()));
	}

	@Test
	public void testGetGeoform() {
		assertEquals("geoform", UTIL.getEnglish(p1.getGeoform()));
	}

	@Test
	public void testSetGeoform() {
		p2.setGeoform(UTIL.english("3"));
		assertEquals("3", UTIL.getEnglish(p2.getGeoform()));
	}

	@Test
	public void testEqualsObject() {
		assertNotEquals(p1,p2);
	}

}
