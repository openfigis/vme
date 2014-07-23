package org.vme.web.service;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.fao.fi.vme.VmeException;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.impl.jpa.VmeSearchDaoImpl;

@RunWith(CdiRunner.class)
@AdditionalClasses({ VmeSearchDaoImpl.class })
@ActivatedAlternatives({ ReferenceDaoImpl.class, FigisTestPersistenceUnitConfiguration.class,
	FigisDataBaseProducer.class, VmeTestPersistenceUnitConfiguration.class,
	VmeDataBaseProducerApplicationScope.class })
public class VmeGetInfoWSTest {

	@Inject
	VmeGetInfoWS ws;
	
	@Test
	public void testVmeGetInfoWS() {
		ws = new VmeGetInfoWS();
		assertNotNull(ws);
	}

	@Test
	public void testFindStringString() {
		try {
			assertNotNull(ws.find("VME_NAFO_12", "2001"));
		} catch (Exception e) {
			throw new VmeException(e);
		}
	}

	@Test
	public void testFindString() {
		try {
			assertNotNull(ws.find("VME_NAFO_12"));
		} catch (Exception e) {
			throw new VmeException(e);
		}
	}

	@Test
	public void testVmeIdentifierSpecificmeasures() {
		try {
			assertNotNull(ws.find("VME_NAFO_12"));
		} catch (Exception e) {
			throw new VmeException(e);
		}
	}

}
