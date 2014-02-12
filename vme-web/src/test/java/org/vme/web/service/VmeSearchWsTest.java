package org.vme.web.service;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.impl.jpa.VmeSearchDaoImpl;

@RunWith(CdiRunner.class)
@AdditionalClasses(VmeSearchDaoImpl.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class, VmeDataBaseProducer.class })
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
