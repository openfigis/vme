package org.vme.web.service;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.impl.jpa.VmeSearchDaoImpl;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ReferenceDaoImpl.class, VmeSearchDaoImpl.class })
@ActivatedAlternatives({ FigisTestPersistenceUnitConfiguration.class, FigisDataBaseProducer.class, VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducer.class })
public class VmeSearchRefTypeWsTest {

	@Inject
	VmeSearchRefTypeWs webservice;

	@Test
	public void testVmeSearchRefTypeWs() {
		assertNotNull(webservice);
	}

	@Test
	public void testFindStringString() {
		// TODO("Not yet implemented");
	}

	@Test
	public void testFind() {
		// TODO("Not yet implemented");
	}

}
