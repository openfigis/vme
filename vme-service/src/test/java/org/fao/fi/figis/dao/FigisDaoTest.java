package org.fao.fi.figis.dao;

import javax.inject.Inject;

import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class })
public class FigisDaoTest {

	@Inject
	private FigisDao dao;

	@Test
	public void testLoadRefVmes() {
		int number = 50;
		for (int i = 0; i < number; i++) {
			RefVme o = new RefVme();
			o.setId(i);
			dao.persist(o);
		}
		assertEquals(number, dao.loadRefVmes().size());

	}

	@Test
	public void testLoadRefVme() {
		int id = 4354;
		RefVme o = new RefVme();
		o.setId(id);
		dao.persist(o);
		RefVme found = dao.loadRefVme(id);
		assertNotNull(found);
		assertEquals(id, found.getId().intValue());

	}

	/**
	 * return null when no object is found.
	 */

	@Test
	public void testLoadRefVmeNull() {
		RefVme object = dao.loadRefVme(4561);
		assertNull(object);

	}
}
