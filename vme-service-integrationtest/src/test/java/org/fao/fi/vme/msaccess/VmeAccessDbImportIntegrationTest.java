package org.fao.fi.vme.msaccess;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.service.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FilesystemMsAccessConnectionProvider.class })
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
			rfmo.setHasFisheryAreasHistory(null);
			rfmo.setInformationSourceList(null);
			rfmo.setListOfManagedVmes(null);

			List<GeneralMeasure> list = rfmo.getGeneralMeasureList();
			for (GeneralMeasure generalMeasures : list) {
				generalMeasures.setRfmo(null);
				vmeDao.merge(generalMeasures);
			}
			rfmo.setGeneralMeasureList(null);
			vmeDao.merge(rfmo);

			if (rfmo.getHasFisheryAreasHistory() != null) {
				List<FisheryAreasHistory> fahList = rfmo.getHasFisheryAreasHistory();
				for (FisheryAreasHistory fisheryAreasHistory : fahList) {
					fisheryAreasHistory.setRfmo(null);
					vmeDao.merge(fisheryAreasHistory);
				}
			}

			if (rfmo.getHasVmesHistory() != null) {
				List<VMEsHistory> vhList = rfmo.getHasVmesHistory();
				for (VMEsHistory vh : vhList) {
					vh.setRfmo(null);
					vmeDao.merge(vh);
				}
			}
			rfmo.setHasFisheryAreasHistory(null);
			rfmo.setHasVmesHistory(null);
			vmeDao.merge(rfmo);

		}

		List<InformationSource> informationSourceList = (List<InformationSource>) vmeDao
				.loadObjects(InformationSource.class);
		for (InformationSource is : informationSourceList) {
			is.setRfmoList(null);
			is.setGeneralMeasure(null);
			is.setSpecificMeasure(null);
			vmeDao.merge(is);
		}

		List<Vme> vmeList = (List<Vme>) vmeDao.loadObjects(Vme.class);
		for (Vme vme : vmeList) {
			vme.setGeoRefList(null);
			vme.setRfmo(null);
			vme.setSpecificMeasureList(null);
			vme.setProfileList(null);
			vmeDao.merge(vme);
		}

		// VMEsHistory.class

		// now delete the actual objects
		Class<?>[] classes = { VMEsHistory.class, FisheryAreasHistory.class, GeneralMeasure.class,
				SpecificMeasure.class, InformationSource.class, GeoRef.class, Vme.class, Profile.class, Rfmo.class };
		for (Class<?> clazz : classes) {
			List<?> list = vmeDao.loadObjects(clazz);
			for (Object object : list) {
				vmeDao.remove(object);
			}
		}
	}
}
