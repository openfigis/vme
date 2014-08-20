package org.vme.dao.config.vme;

import static org.junit.Assert.assertNotEquals;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.Test;

public class DoubleEntityManagerTest {

	@Test
	public void testCreateNewEm() {
		DoubleEntityManager m = new DoubleEntityManager();

		m.setEmf(Persistence.createEntityManagerFactory(new VmePersistenceUnitConfiguration()
				.doGetPersistenceUnitName()));
		EntityManager em1 = m.getEm();
		m.createNewEm();
		assertNotEquals(em1, m.getEm());
	}
}
