package org.vme.web.service;

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
public class VmeSearchWsTest {

	@Inject
	VmeSearchWs vmeSearchWs;

	@Test
	public void testFind() throws Exception {

		String text = "fiets";
		vmeSearchWs.find(text, null, null, null, null);
	}

}
