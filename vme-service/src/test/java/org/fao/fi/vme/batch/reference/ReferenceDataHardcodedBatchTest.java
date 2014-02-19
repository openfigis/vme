package org.fao.fi.vme.batch.reference;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseConfigurationTest;
import org.vme.dao.config.vme.VmeDataBaseProducer;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseConfigurationTest.class, VmeDataBaseProducer.class })
public class ReferenceDataHardcodedBatchTest {

	@Inject
	ReferenceDataHardcodedBatch dao;

	@Test
	public void testRun() {
		dao.run();
	}

}
