package org.fao.fi.vme.msaccess;

import java.util.ArrayList;
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
import org.fao.fi.vme.test.GeneralMeasuresMock;
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

	/**
	 * When doing a clean, this problem occurs:
	 * 
	 * javax.persistence.PersistenceException: org.hibernate.exception.GenericJDBCException: could not initialize a
	 * collection: [org.fao.fi.vme.domain.Rfmo.generalMeasuresList#SEAFO] Caused by:
	 * org.hibernate.exception.GenericJDBCException: could not initialize a collection:
	 * [org.fao.fi.vme.domain.Rfmo.generalMeasuresList#SEAFO] Caused by: java.sql.SQLException: Stream has already been
	 * closed
	 * 
	 * 11:39:52,848 WARN SqlExceptionHelper:143 - SQL Error: 17027, SQLState: 99999
	 * 
	 * 11:39:52,848 ERROR SqlExceptionHelper:144 - Stream has already been closed
	 * 
	 * 
	 * This test tries to isolate the problem, not yet succeeded.
	 * 
	 * 
	 */
	@Test
	public void testProblemCouldNotInitializeACollection() {
		Rfmo rfmo = new Rfmo();
		rfmo.setId("ERIK");
		GeneralMeasures gm1 = GeneralMeasuresMock.create();
		GeneralMeasures gm2 = GeneralMeasuresMock.create();
		gm1.setRfmo(rfmo);
		gm2.setRfmo(rfmo);
		gm1.setId(10l);
		gm2.setId(20l);

		List<GeneralMeasures> generalMeasuresList = new ArrayList<GeneralMeasures>();
		rfmo.setGeneralMeasuresList(generalMeasuresList);
		vmeDao.persist(rfmo);
		vmeDao.persist(gm1);
		vmeDao.persist(gm2);

		List<GeneralMeasures> list = rfmo.getGeneralMeasuresList();
		for (GeneralMeasures generalMeasures : list) {
			generalMeasures.setRfmo(null);
			vmeDao.merge(generalMeasures);
		}

		rfmo.setGeneralMeasuresList(null);
		gm1.setRfmo(null);
		gm2.setRfmo(null);
		vmeDao.merge(gm1);
		vmeDao.merge(gm2);
		vmeDao.merge(rfmo);
		vmeDao.remove(gm1);
		vmeDao.remove(gm2);
		vmeDao.remove(rfmo);
	}

	@SuppressWarnings("unchecked")
	protected void clean() {

		// delete first the relations
		List<Rfmo> rfmoList = (List<Rfmo>) vmeDao.loadObjects(Rfmo.class);
		for (Rfmo rfmo : rfmoList) {
			rfmo.setFishingHistoryList(null);
			rfmo.setInformationSourceList(null);
			rfmo.setListOfManagedVmes(null);

			List<GeneralMeasures> list = rfmo.getGeneralMeasuresList();
			for (GeneralMeasures generalMeasures : list) {
				generalMeasures.setRfmo(null);
				vmeDao.merge(generalMeasures);
			}
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
