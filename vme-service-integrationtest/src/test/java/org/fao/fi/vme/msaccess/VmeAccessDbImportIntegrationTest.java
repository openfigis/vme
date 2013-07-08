package org.fao.fi.vme.msaccess;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.domain.Profile;
import org.fao.fi.vme.domain.Rfmo;
import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.Vme;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class VmeAccessDbImportIntegrationTest {

	@Inject
	VmeAccessDbImport i;

	@Inject
	VmeDao vmeDao;

	@Before
	public void beforeTest() {
		clean();
	}

	@After
	public void afterTest() {
		clean();
	}

	/**
	 * 
	 */
	@Test
	public void testImportMsAccessData() {
		i.importMsAccessData();
	}

	@SuppressWarnings("unchecked")
	protected void clean() {

		// delete first the relations
		List<Rfmo> rfmoList = (List<Rfmo>) vmeDao.loadObjects(Rfmo.class);
		for (Rfmo rfmo : rfmoList) {
			rfmo.setFishingHistoryList(null);
			rfmo.setInformationSourceList(null);
			rfmo.setListOfManagedVmes(null);
			rfmo.setGeneralMeasuresList(null);
			vmeDao.merge(rfmo);
		}

		List<InformationSource> informationSourceList = (List<InformationSource>) vmeDao
				.loadObjects(InformationSource.class);
		for (InformationSource is : informationSourceList) {
			is.setRfmoList(null);
			is.setGeneralMeasures(null);
			is.setSpecificMeasures(null);
			vmeDao.merge(is);
		}

		List<Vme> vmeList = (List<Vme>) vmeDao.loadObjects(Vme.class);
		for (Vme vme : vmeList) {
			vme.setGeoRefList(null);
			vme.setRfmo(null);
			vme.setSpecificMeasureList(null);
			vme.setProfileList(null);
			vme.setHistoryList(null);
			vmeDao.merge(vme);
		}

		// now delete the actual objects
		Class<?>[] classes = { GeneralMeasures.class, SpecificMeasures.class, InformationSource.class, Vme.class,
				GeoRef.class, Profile.class, Rfmo.class };
		for (Class<?> clazz : classes) {
			List<?> list = vmeDao.loadObjects(clazz);
			for (Object object : list) {
				vmeDao.remove(object);
			}
		}
	}
}
