package org.fao.fi.vme.domain.model.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VmeCriteriaTest {

	VmeCriteria criteria1 = new VmeCriteria(1001L, "Life-history traits");
	VmeCriteria criteria2 = new VmeCriteria(1002L, "Structural complexity");
	
	@Test
	public void testHashCode() {
		assertFalse(criteria1.hashCode() == criteria2.hashCode());
	}

	@Test
	public void testVmeCriteria() {
		VmeCriteria crit = new VmeCriteria();
		assertNotNull(crit);
	}

	@Test
	public void testVmeCriteriaLongString() {
		VmeCriteria crit = new VmeCriteria(1003L, "Fragility");
		assertNotNull(crit);
	}

	@Test
	public void testGetId() {
		assertTrue(1001 == criteria1.getId());
	}

	@Test
	public void testSetId() {
		criteria1.setId(1009L);
		assertTrue(1009 == criteria1.getId());
	}

	@Test
	public void testGetName() {
		assertEquals("Life-history traits", criteria1.getName());
	}

	@Test
	public void testSetName() {
		criteria1.setName("Fragility");
		assertEquals("Fragility", criteria1.getName());
	}

	@Test
	public void testEqualsObject() {
		assertNotEquals(criteria1, criteria2);
	}

}
