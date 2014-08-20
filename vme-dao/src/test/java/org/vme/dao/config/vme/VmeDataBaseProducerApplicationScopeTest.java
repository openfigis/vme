package org.vme.dao.config.vme;

import static org.junit.Assert.assertNotEquals;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives(VmePersistenceUnitConfiguration.class)
@AdditionalClasses(VmeDataBaseProducerApplicationScope.class)
public class VmeDataBaseProducerApplicationScopeTest {

	@Inject
	DoubleEntityManager d;

	@Inject
	@VmeDB
	EntityManager em;

	@Test
	public void testProduceEntityManager() {
		d.createNewEm();
		assertNotEquals(em, d.getEm());

	}
}
