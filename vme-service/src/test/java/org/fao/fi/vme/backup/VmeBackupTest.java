package org.fao.fi.vme.backup;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.msaccess.VmeAccessDbImport;
import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducer.class,
		FilesystemMsAccessConnectionProvider.class })
public class VmeBackupTest {

	VmeBackup b = new VmeBackup();
	String fileName = "target/VmeList.ser";
	File file = new File(fileName);

	@Inject
	VmeAccessDbImport i;

	@Inject
	VmeDao vmeDao;

	@Before
	public void before() {
		b = new VmeBackup();
		b.setFileName(fileName);
	}

	@Test
	public void testRun() {
		file.delete();

		Vme vme = VmeMock.generateVme(5);
		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(vme);

		b.run(vmeList);
		assertTrue(file.exists());
		file.delete();
	}

	/**
	 * This produces a file of 36kb, which can't possible hold all Vme's. This
	 * is probably because of the fact that Hibernate lazily loads these
	 * objects.
	 */
	@Test
	@Ignore("msaccess is not supported anymore")
	public void testRunWithAccess() {
		i.importMsAccessData();
		List<Vme> vmeList = vmeDao.loadVmes();
		file.delete();
		b.run(vmeList);
		assertTrue(file.exists());
		file.delete();
	}

}
