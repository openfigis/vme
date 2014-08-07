package org.fao.fi.vme.batch.reference;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives(VmePersistenceUnitConfiguration.class)
@AdditionalClasses(VmeDataBaseProducerApplicationScope.class)
public class ReferenceDataHardcodedBatchTest {

	@Inject
	ReferenceDataHardcodedBatch batch;

	@Test
	public void testRun() {
		batch.run();
	}

}
