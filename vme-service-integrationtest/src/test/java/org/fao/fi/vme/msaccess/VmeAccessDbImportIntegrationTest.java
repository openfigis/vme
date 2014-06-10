package org.fao.fi.vme.msaccess;

import javax.inject.Inject;

import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class, VmeDataBaseProducer.class, FigisDataBaseProducer.class,
		FigisPersistenceUnitConfiguration.class, FilesystemMsAccessConnectionProvider.class })
public class VmeAccessDbImportIntegrationTest {

	@Inject
	VmeAccessDbImport i;

	@Inject
	VmeDao vmeDao;

	VmeClean c = new VmeClean();

	@Before
	public void beforeTest() {
		c.clean(vmeDao);
	}

	@After
	public void afterTest() {
		// c.clean(vmeDao);
	}

	/**
	 * 
	 */
	@Test
	@Ignore("ms access is phased out")
	public void testImportMsAccessData() {
		i.importMsAccessData();
	}

}
