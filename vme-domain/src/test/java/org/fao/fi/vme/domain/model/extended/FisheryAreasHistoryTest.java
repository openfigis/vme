package org.fao.fi.vme.domain.model.extended;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.test.RfmoMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Test;

public class FisheryAreasHistoryTest {

	private FisheryAreasHistory f1 = new FisheryAreasHistory();
	private FisheryAreasHistory f2 = new FisheryAreasHistory();
	private MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
	private Rfmo rfmo = RfmoMock.create();
	
	@Test
	public void testHashCode() {
		f1.setId(100L);
		f2.setId(102L);
		assertFalse(f1.hashCode() == f2.hashCode());
	}

	@Test
	public void testGetId() {
		f1.setId(100L);
		assertTrue(100 == f1.getId());
	}

	@Test
	public void testSetId() {
		f1.setId(100L);
		assertTrue(100 == f1.getId());
	}

	@Test
	public void testGetYear() {
		f1.setYear(1999);
		assertTrue(1999 == f1.getYear());
	}

	@Test
	public void testSetYear() {
		f1.setYear(1999);
		assertTrue(1999 == f1.getYear());
	}

	@Test
	public void testGetHistory() {
		f1.setHistory(UTIL.english("foo history"));
		assertEquals("foo history", UTIL.getEnglish(f1.getHistory()));
	}

	@Test
	public void testGetRfmo() {
		f1.setRfmo(rfmo);
		assertEquals("1000", f1.getRfmo().getId());
	}

	@Test
	public void testSetRfmo() {
		f1.setRfmo(rfmo);
		assertEquals("1000", f1.getRfmo().getId());
	}

	@Test
	public void testSetHistory() {
		f1.setHistory(UTIL.english("foo history"));
		assertEquals("foo history", UTIL.getEnglish(f1.getHistory()));
	}

	@Test
	public void testEqualsObject() {
		f1.setId(100L);
		f2.setId(200L);
		assertNotEquals(f1, f2);
	}

}
