package org.fao.fi.vme.msaccess;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.service.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FilesystemMsAccessConnectionProvider.class })
public class VmeAccessDbImportTest {

	@Inject
	VmeAccessDbImport i;

	@Inject
	VmeDao vmeDao;

	@Test
	public void testImportMsAccessData() {
		i.importMsAccessData();
	}

	// @Test
	public void testImportMsAccessDataPlusValidation() {
		i.importMsAccessData();
		List<?> objects = vmeDao.loadObjects(Vme.class);
		System.out.println("TEST: Found " + objects.size() + " Vme objects");
		for (Object o : objects) {
			Vme vme = (Vme) o;
			vme.getRfmo().getGeneralMeasureList();
		}
	}
}
