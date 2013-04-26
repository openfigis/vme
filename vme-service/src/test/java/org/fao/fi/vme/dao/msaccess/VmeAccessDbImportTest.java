package org.fao.fi.vme.dao.msaccess;

import javax.inject.Inject;

import org.fao.fi.vme.dao.config.EntityManagerFactoryProducer;
import org.fao.fi.vme.dao.config.EntityManagerProducer;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ EntityManagerFactoryProducer.class, EntityManagerProducer.class })
public class VmeAccessDbImportTest {

	@Inject
	VmeAccessDbImport i;

	@Test
	public void testGenerateObjects() {
		i.importMsAccessData();
	}

}
