package org.fao.fi.vme.domain.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VmeTypeTest {

	private VmeType vType1 = new VmeType(150L, "TTH_FISH_RSCH", "Research fisheries");
	private VmeType vType2 = new VmeType(200L, "MPR_FISH_SRCH", "Something different from the previous");
	
	@Test
	public void testHashCode() {
		assertFalse(vType1.hashCode() == vType2.hashCode());
	}

	@Test
	public void testGetCode() {
		assertEquals("TTH_FISH_RSCH", vType1.getCode());
	}

	@Test
	public void testSetCode() {
		vType1.setCode("RRT_FISH_FOO");
		assertEquals("RRT_FISH_FOO", vType1.getCode());
	}

	@Test
	public void testVmeType() {
		VmeType vType = new VmeType();
		assertNotNull(vType);
	}

	@Test
	public void testVmeTypeLongStringString() {
		assertNotNull(vType1);
	}

	@Test
	public void testGetId() {
		assertTrue(150 == vType1.getId());
	}

	@Test
	public void testSetId() {
		vType1.setId(350L);
		assertTrue(350 == vType1.getId());
	}

	@Test
	public void testGetName() {
		assertEquals("Research fisheries", vType1.getName());
	}

	@Test
	public void testSetName() {
		vType1.setName("Research fisheries foo change");
		assertEquals("Research fisheries foo change", vType1.getName());
	}

	@Test
	public void testEqualsObject() {
		assertNotEquals(vType1, vType2);
	}

}
