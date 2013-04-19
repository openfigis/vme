package org.fao.fi.vme.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.VmeObservation;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(CdiRunner.class)
public class JPATest {

	@Inject
	private EntityManager manager;

	@Test
	public void roundTrip() {
		VmeObservation created = new VmeObservation();
		String status = "go";
		created.setStatus(status);

		store(created);

		VmeObservation loaded = lookup(1, VmeObservation.class);

		assertNotNull(loaded);

		assertEquals(1, loaded.getId());
		assertEquals(status, loaded.getStatus());

	}

	void store(Object entity) {

		manager.getTransaction().begin();
		manager.persist(entity);
		manager.getTransaction().commit();
		manager.close();
	}

	<T> T lookup(Object id, Class<T> type) {

		manager.getTransaction().begin();
		T entity = manager.find(type, id);
		manager.getTransaction().commit();
		manager.close();

		return entity;
	}

}
