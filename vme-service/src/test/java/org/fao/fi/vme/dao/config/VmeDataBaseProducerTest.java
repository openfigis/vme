package org.fao.fi.vme.dao.config;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDB;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives(VmePersistenceUnitConfiguration.class)
@AdditionalClasses(VmeDataBaseProducerApplicationScope.class)
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
