package org.fao.fi.vme.msaccess;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.Vme;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class VmeAccessDbImportTest {

	@Inject
	VmeAccessDbImport i;

	@Inject
	VmeDao vmeDao;

	/**

	 * 
	 * 
	 * 
	 */

	// @Test
	public void testImportMsAccessData() {
		i.importMsAccessData();
	}

	@Test
	public void testImportMsAccessDataPlusValidation() {
		i.importMsAccessData();
		List<?> objects = vmeDao.loadObjects(Vme.class);
		for (Object o : objects) {
			Vme vme = (Vme) o;
			int beginYear = vme.getValidityPeriod().getBeginYear();
			int endYear = vme.getValidityPeriod().getBeginYear();
			vme.getRfmo().getGeneralMeasuresList();
			for (GeneralMeasures gm : vme.getRfmo().getGeneralMeasuresList()) {
				assertTrue(gm.getValidityPeriod().getBeginYear() >= beginYear);
				String message = vme.getInventoryIdentifier() + " " + gm.getId() + " "
						+ gm.getValidityPeriod().getBeginYear() + " " + endYear;
				assertTrue(message, gm.getValidityPeriod().getEndYear() <= endYear);
			}
		}
	}
}
