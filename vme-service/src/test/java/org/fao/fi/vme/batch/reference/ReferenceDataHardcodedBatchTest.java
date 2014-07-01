package org.fao.fi.vme.batch.reference;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class })
public class ReferenceDataHardcodedBatchTest {

	@Inject
	ReferenceDataHardcodedBatch batch;

	@Test
	public void testRun() {
		batch.run();
	}

}
