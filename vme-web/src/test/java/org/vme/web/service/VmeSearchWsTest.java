package org.vme.web.service;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.JpaDaoFactory;
import org.vme.service.dao.config.figis.FigisDataBaseProducer;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class, VmeDataBaseProducer.class, JpaDaoFactory.class })
public class VmeSearchWsTest {

	@Inject
	VmeSearchWs vmeSearchWs;

	@Test
	public void testFind() throws Exception {

		String text = "lophelia";
		Response response = vmeSearchWs.find(text, null, null, null, null);
		assertNotNull(response);
	}

}
