package org.fao.fi.vme.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.fao.fi.vme.domain.test.RfmoMock;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.fao.fi.vme.domain.test.VmeCriteriaMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.test.VmeTypeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Before;
import org.junit.Test;

public class VmeTest {

	private Vme vme1;
	private Vme vme2;
	private static final MultiLingualStringUtil UTIL = new MultiLingualStringUtil();

	@Before
	public void before() {
		vme1 = VmeMock.generateVme(3);
		vme1.setId(1000L);
		vme2 = VmeMock.generateVme(3);
		vme2.setId(1001L);
	}

	@Test
	public void testGetId() {
		assertTrue(1000 == vme1.getId());
	}

	@Test
	public void testSetId() {
		vme2.setId(2000L);
		assertTrue(2000 == vme2.getId());
	}

	@Test
	public void testGetInventoryIdentifier() {
		assertEquals("VME_RFMO_1", vme1.getInventoryIdentifier());
	}

	@Test
	public void testSetInventoryIdentifier() {
		vme2.setInventoryIdentifier("VME_RFMO_2");
		assertEquals("VME_RFMO_2", vme2.getInventoryIdentifier());
	}

	@Test
	public void testGetName() {
		assertEquals("Hard Corner Bugs ", UTIL.getEnglish(vme1.getName()));
	}

	@Test
	public void testSetName() {
		vme2.setName(UTIL.english("Foo Hard Corner Bugs"));
		assertEquals("Foo Hard Corner Bugs", UTIL.getEnglish(vme2.getName()));
	}

	@Test
	public void testGetRfmo() {
		// assertEquals("1026", vme1.getRfmo().getId());
	}

	@Test
	public void testSetRfmo() {
		Rfmo r = RfmoMock.create();
		r.setId("2001");
		vme2.setRfmo(r);
		assertEquals("2001", vme2.getRfmo().getId());
	}

	@Test
	public void testGetProfileList() {
		assertTrue(3 == vme1.getProfileList().size());
	}

	@Test
	public void testSetProfileList() {
		Profile profile = new Profile();

		profile.setGeoform(UTIL.english("Geoform"));
		profile.setDescriptionBiological(UTIL.english("Hello World DescriptionBiological"));
		profile.setDescriptionImpact(UTIL.english("Hello World DescriptionImpact"));
		profile.setDescriptionPhisical(UTIL.english("Hello World DescriptionPhisical"));
		profile.setVme(vme2);
		profile.setYear(2001);
		vme2.getProfileList().add(profile);

		assertTrue(4 == vme2.getProfileList().size());
	}

	@Test
	public void testGetGeoRefList() {
		assertTrue(9 == vme1.getGeoRefList().size());
	}

	@Test
	public void testSetGeoRefList() {
		GeoRef geoRef = new GeoRef();
		geoRef.setVme(vme2);

		geoRef.setYear(2001);
		geoRef.setGeographicFeatureID(vme2.getInventoryIdentifier() + "_2001");
		vme2.getGeoRefList().add(geoRef);

		assertTrue(10 == vme2.getGeoRefList().size());
	}

	@Test
	@SuppressWarnings("deprecation")
	public void testGetValidityPeriod() {
		assertTrue(100 == vme1.getValidityPeriod().getBeginDate().getYear());
		assertTrue(102 == vme1.getValidityPeriod().getEndDate().getYear());
	}

	@Test
	@SuppressWarnings("deprecation")
	public void testSetValidityPeriod() {
		ValidityPeriod v = ValidityPeriodMock.create(2000, 2001);
		vme2.setValidityPeriod(v);
		assertTrue(100 == vme2.getValidityPeriod().getBeginDate().getYear());
		assertTrue(101 == vme2.getValidityPeriod().getEndDate().getYear());
	}

	@Test
	public void testGetAreaType() {
		assertTrue(150 == vme1.getAreaType());
	}

	@Test
	public void testSetAreaType() {
		vme2.setAreaType(VmeTypeMock.createAnother().getId());
		assertTrue(180 == vme2.getAreaType());
	}

	@Test
	public void testGetCriteria() {
		assertTrue(40 == vme1.getCriteria().get(0));
	}

	@Test
	public void testSetCriteria() {
		vme2.setCriteria(new ArrayList<Long>(Arrays.asList(VmeCriteriaMock.create().getId(), VmeCriteriaMock
				.createAnother().getId(), VmeCriteriaMock.createYetAnother().getId())));
		assertTrue(3 == vme2.getCriteria().size());
	}

	@Test
	public void testGetGeoArea() {
		assertEquals("Southern Pacific Ocean", UTIL.getEnglish(vme1.getGeoArea()));
	}

	@Test
	public void testGetScope() {
		assertEquals(VmeMock.VME_SCOPE, vme1.getScope());

	}

	@Test
	public void testSetScope() {
		vme2.setScope(1000L);
		assertTrue(1000 == vme2.getScope());
	}

	@Test
	public void testSetGeoArea() {
		vme2.setGeoArea(UTIL.english("Northern Pacific Ocean"));
		assertEquals("Northern Pacific Ocean", UTIL.getEnglish(vme2.getGeoArea()));
	}

	@Test
	public void testGetSpecificMeasureList() {
		assertTrue(3 == vme1.getSpecificMeasureList().size());
	}

	@Test
	public void testSetSpecificMeasureList() {
		SpecificMeasure s = new SpecificMeasure();
		vme2.getSpecificMeasureList().add(s);
		assertTrue(4 == vme2.getSpecificMeasureList().size());
	}

	@Test
	public void testEqualsWithScopeObject() {
		vme1.setScope(2000L);
		vme2.setScope(2001L);
		assertNotEquals(vme1, vme2);
	}

	@Test
	public void testEqualsWOScopeObject() {
		vme1.setScope(null);
		vme2.setScope(null);
		vme1.setId(3000L);
		assertNotEquals(vme1, vme2);
	}

}
