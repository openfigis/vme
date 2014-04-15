package org.fao.fi.vme.batch.reference;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducer.class })
public class ReferenceDataHardcodedBatchTest {

	@Inject
	ReferenceDataHardcodedBatch batch;

	@Test
	public void testRun() {
		batch.run();
	}

}
