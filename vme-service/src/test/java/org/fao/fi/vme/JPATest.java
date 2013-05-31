package org.fao.fi.vme;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.fao.fi.vme.domain.ValidityPeriod;
import org.fao.fi.vme.domain.Vme;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class JPATest {

	@Inject
	private EntityManager manager;

	@Test
	public void roundTrip() {

		int id = 10;
		Vme created = new Vme();
		created.setId(id);
		// String criteria = "go";
		// created.setCriteria(criteria);

		ValidityPeriod vp = new ValidityPeriod();
		created.setValidityPeriod(vp);

		store(created);
		assertNotNull(created.getId());
		Vme loaded = lookup(created.getId(), Vme.class);

		assertNotNull(loaded);

		assertEquals(id, loaded.getId());
		// assertEquals(criteria, loaded.getCriteria());

	}

	void store(Object entity) {

		manager.getTransaction().begin();
		manager.persist(entity);
		manager.getTransaction().commit();

	}

	<T> T lookup(Object id, Class<T> type) {

		manager.getTransaction().begin();
		T entity = manager.find(type, id);
		manager.getTransaction().commit();

		return entity;
	}

}
