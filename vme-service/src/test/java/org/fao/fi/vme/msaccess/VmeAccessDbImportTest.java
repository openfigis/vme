package org.fao.fi.vme.msaccess;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
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

	@Test
	public void testImportMsAccessDataPlusValidation() {
		i.importMsAccessData();
		List<?> objects = vmeDao.loadObjects(Profile.class);
		assertTrue(objects.size() > 0);
		checkRelationGmIs();
	}

	private void checkRelationGmIs() {
		List<GeneralMeasure> gmList = vmeDao.loadObjectsGeneric(GeneralMeasure.class);

		// we check all generalMeasures
		for (GeneralMeasure generalMeasure : gmList) {
			// get the IS list
			List<InformationSource> foundIsList = generalMeasure.getInformationSourceList();
			for (InformationSource informationSource : foundIsList) {
				// this this informationSource refer to the same GM?
				assertTrue(informationSource.getGeneralMeasureList().contains(generalMeasure));
			}
		}

		List<InformationSource> isList = vmeDao.loadObjectsGeneric(InformationSource.class);
		// we check all InformationSource
		for (InformationSource informationSource : isList) {
			// get the IS list
			List<GeneralMeasure> foundGmList = informationSource.getGeneralMeasureList();
			for (GeneralMeasure generalMeasure : foundGmList) {
				// this this GeneralMeasure refer to the same informationSource?
				assertTrue(generalMeasure.getInformationSourceList().contains(informationSource));
			}
		}

	}

}
