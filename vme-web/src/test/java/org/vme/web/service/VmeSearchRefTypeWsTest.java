package org.vme.web.service;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.JpaDaoFactory;
import org.vme.service.dao.config.figis.FigisDataBaseProducer;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class, VmeDataBaseProducer.class, JpaDaoFactory.class })
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
