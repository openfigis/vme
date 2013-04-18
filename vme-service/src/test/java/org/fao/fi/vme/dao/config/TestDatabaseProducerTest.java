package org.fao.fi.vme.dao.config;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(CdiRunner.class)
public class TestDatabaseProducerTest {

	@Inject
	TestDatabaseProducer test;

	@Test
	public void test() {
		assertNotNull(test);
		assertNotNull(test.getEntityManager());
	}

}
