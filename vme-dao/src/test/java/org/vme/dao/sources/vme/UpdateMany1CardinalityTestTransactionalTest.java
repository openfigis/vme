package org.vme.dao.sources.vme;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class UpdateMany1CardinalityTestTransactionalTest extends Update1nCardinalityTest {

	@Inject
	private VmeDao dao;

	@Before
	public void before() {
		u1n = new Update1nCardinalityTransactional();
		em = dao.getEm();
	}

}
