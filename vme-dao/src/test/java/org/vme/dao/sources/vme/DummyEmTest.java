package org.vme.dao.sources.vme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.fao.fi.vme.domain.model.GeoRef;
import org.junit.Test;

public class DummyEmTest {

	@Test
	public void testRemove() {
		GeoRef g = new GeoRef();
		DummyEm em = new DummyEm();
		em.persist(g);
		em.remove(g);
		assertNull(em.find(GeoRef.class, g.getId()));
		assertEquals(g, em.getRemovedObject());

	}

	@Test
	public void testFindClassOfTObject() {
		GeoRef g = new GeoRef();
		DummyEm em = new DummyEm();
		em.persist(g);
		assertEquals(g, em.find(GeoRef.class, g.getId()));
	}

}
