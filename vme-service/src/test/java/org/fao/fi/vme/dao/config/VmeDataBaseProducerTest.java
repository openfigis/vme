package org.fao.fi.vme.dao.config;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.vme.VmeDB;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
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
