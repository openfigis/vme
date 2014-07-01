package org.fao.fi.vme.dao.config;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDB;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class })
public class VmeDataBaseProducerTest {

	@Inject
	EntityManagerFactory f;

	@Inject
	@VmeDB
	EntityManager e;

	@Test
	public void testProduceEntityManager() {
		assertNotNull(f);
		assertNotNull(e);
	}

}
