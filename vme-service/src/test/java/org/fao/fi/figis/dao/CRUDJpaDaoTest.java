/**
 * (c) 2013 FAO / UN (project: vme-dao)
 */
package org.fao.fi.figis.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.msaccess.VmeAccessDbImport;
import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.sources.vme.VmeDao;

/**
 * Place your class / interface description here.
 * 
 * History:
 * 
 * ------------- --------------- ----------------------- Date Author Comment
 * ------------- --------------- ----------------------- 16 Jan 2014 Fiorellato
 * Creation.
 * 
 * @version 1.0
 * @since 16 Jan 2014
 */
@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisTestPersistenceUnitConfiguration.class, VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducer.class, FilesystemMsAccessConnectionProvider.class })
public class CRUDJpaDaoTest {
	@Inject
	private VmeAccessDbImport importer;
	@Inject
	private VmeDao vmeDao;

	private EntityManager em;

	@PostConstruct
	public void postConstruct() {
		this.em = vmeDao.getEm();

		importer.importMsAccessData();
	}

	@Test
	public void testDeleteVme() {
		EntityTransaction et = this.vmeDao.getEm().getTransaction();

		Vme ref = this.vmeDao.getEntityById(this.em, Vme.class, 1L);

		et.begin();

		this.vmeDao.delete(ref);

		et.commit();

		Assert.assertNull(this.vmeDao.getEntityById(this.em, Vme.class, ref.getId()));

		for (Profile profile : ref.getProfileList()) {
			Assert.assertNull(this.vmeDao.getEntityById(this.em, Profile.class, profile.getId()));
		}

		for (SpecificMeasure measure : ref.getSpecificMeasureList()) {
			Assert.assertNull(this.vmeDao.getEntityById(this.em, SpecificMeasure.class, measure.getId()));
		}

		for (GeoRef georef : ref.getGeoRefList()) {
			Assert.assertNull(this.vmeDao.getEntityById(this.em, GeoRef.class, georef.getId()));
		}
	}

	@Test
	public void testChangeVmeRfmo() {
		Vme ref = this.vmeDao.getEntityById(this.em, Vme.class, 1L);
		Rfmo currentRfmo = ref.getRfmo();
		Rfmo newRfmo = this.vmeDao.getEntityById(this.em, Rfmo.class, "CCAMLR");

		Assert.assertNotNull(ref);
		Assert.assertNotNull(ref.getRfmo());
		Assert.assertNotNull(ref.getRfmo().getId());
		Assert.assertNotNull(newRfmo);
		Assert.assertNotEquals(ref.getRfmo().getId(), newRfmo.getId());

		ref.setRfmo(newRfmo);

		this.vmeDao.merge(this.em, ref);

		Vme refreshed = this.vmeDao.getEntityById(this.em, Vme.class, ref.getId());

		Assert.assertNotNull(refreshed);
		Assert.assertNotNull(refreshed.getRfmo());
		Assert.assertNotNull(refreshed.getRfmo().getId());

		Assert.assertEquals(newRfmo.getId(), refreshed.getRfmo().getId());

		boolean found = false;

		for (Vme current : refreshed.getRfmo().getListOfManagedVmes())
			if (current.getId().equals(refreshed.getId())) {
				found = true;
				break;
			}

		Assert.assertTrue(found);

		found = false;

		currentRfmo = this.vmeDao.getEntityById(this.em, Rfmo.class, currentRfmo.getId());

		for (Vme previous : currentRfmo.getListOfManagedVmes())
			if (previous.getId().equals(refreshed.getId())) {
				found = true;
				break;
			}

		Assert.assertFalse(found);
	}

	@Test
	public void testRemoveVmeRfmo() {
		Vme ref = this.vmeDao.getEntityById(this.em, Vme.class, 1L);
		Rfmo currentRfmo = ref.getRfmo();

		Assert.assertNotNull(ref);
		Assert.assertNotNull(ref.getRfmo());
		Assert.assertNotNull(ref.getRfmo().getId());

		ref.setRfmo(null);

		this.vmeDao.merge(this.em, ref);

		Vme refreshed = this.vmeDao.getEntityById(this.em, Vme.class, ref.getId());

		Assert.assertNotNull(refreshed);
		Assert.assertNull(refreshed.getRfmo());

		boolean found = false;

		currentRfmo = this.vmeDao.getEntityById(this.em, Rfmo.class, currentRfmo.getId());

		for (Vme previous : currentRfmo.getListOfManagedVmes())
			if (previous.getId().equals(refreshed.getId())) {
				found = true;
				break;
			}

		Assert.assertFalse(found);
	}

	@Test
	public void testDefaultDeleteGeoRef() {
		EntityTransaction et = this.vmeDao.getEm().getTransaction();

		GeoRef ref = this.vmeDao.getEntityById(this.em, GeoRef.class, 1L);

		Assert.assertNotNull(ref);

		et.begin();

		this.vmeDao.delete(ref);

		et.commit();

		Assert.assertNull(this.vmeDao.getEntityById(this.em, GeoRef.class, ref.getId()));

		Vme vme = this.vmeDao.getEntityById(this.em, Vme.class, ref.getVme().getId());

		for (GeoRef geoRef : vme.getGeoRefList())
			Assert.assertNotEquals(geoRef.getId(), ref.getId());
	}

	@Test
	public void testDefaultDeleteProfile() {
		EntityTransaction et = this.vmeDao.getEm().getTransaction();

		Profile ref = this.vmeDao.getEntityById(this.em, Profile.class, 1L);

		Assert.assertNotNull(ref);

		et.begin();

		this.vmeDao.delete(ref);

		et.commit();

		Assert.assertNull(this.vmeDao.getEntityById(this.em, Profile.class, ref.getId()));

		Vme vme = this.vmeDao.getEntityById(this.em, Vme.class, ref.getVme().getId());

		for (Profile profile : vme.getProfileList())
			Assert.assertNotEquals(profile.getId(), ref.getId());
	}

	@Test
	public void testDefaultDeleteSpecificMeasure() {
		EntityTransaction et = this.vmeDao.getEm().getTransaction();

		SpecificMeasure ref = this.vmeDao.getEntityById(this.em, SpecificMeasure.class, 1L);

		Assert.assertNotNull(ref);

		et.begin();

		this.vmeDao.delete(ref);

		et.commit();

		Assert.assertNull(this.vmeDao.getEntityById(this.em, SpecificMeasure.class, ref.getId()));

		Vme vme = ref.getVme();

		Assert.assertNotNull(vme);

		this.vmeDao.getEntityById(this.em, Vme.class, vme.getId());

		for (SpecificMeasure specificMeasure : vme.getSpecificMeasureList())
			Assert.assertNotEquals(specificMeasure.getId(), ref.getId());
	}

	private Collection<Long> listReferencedInfoSources() {
		Set<Long> gmReferenced = new HashSet<Long>();
		Set<Long> smReferenced = new HashSet<Long>();

		List<GeneralMeasure> gms = this.vmeDao.loadObjects(GeneralMeasure.class);

		System.out.println("Read " + gms.size() + " general measures");

		for (GeneralMeasure gm : gms) {
			if (gm.getInformationSourceList() != null && !gm.getInformationSourceList().isEmpty()) {
				System.out.println("General measure #" + gm.getId() + " refers to "
						+ gm.getInformationSourceList().size() + " information sources");

				for (InformationSource is : gm.getInformationSourceList()) {
					System.out.println("General measure #" + gm.getId() + " references Information source #"
							+ is.getId());

					gmReferenced.add(is.getId());
				}
			}
		}

		List<SpecificMeasure> sms = this.vmeDao.loadObjects(SpecificMeasure.class);

		System.out.println("Read " + gms.size() + " specific measures");

		for (SpecificMeasure sm : sms) {
			if (sm.getInformationSource() != null) {
				System.out.println("Specfic measure #" + sm.getId() + " refers to Information source #"
						+ sm.getInformationSource().getId());

				smReferenced.add(sm.getInformationSource().getId());
			}
		}

		gmReferenced.retainAll(smReferenced);

		return gmReferenced;
	}

	@Test
	public void testDefaultDeleteInformationSource() {
		EntityTransaction t = this.em.getTransaction();

		for (Long isId : this.listReferencedInfoSources()) {
			System.out.println("Deleting information source #" + isId);

			InformationSource informationSource = this.vmeDao.getEntityById(this.vmeDao.getEm(),
					InformationSource.class, isId);

			Rfmo rfmo = this.vmeDao.getEntityById(this.em, Rfmo.class, informationSource.getRfmo().getId());

			Assert.assertNotNull(informationSource);

			Assert.assertTrue(informationSource.getGeneralMeasureList() != null);
			Assert.assertFalse(informationSource.getGeneralMeasureList().isEmpty());

			Assert.assertTrue(informationSource.getSpecificMeasureList() != null);
			Assert.assertFalse(informationSource.getSpecificMeasureList().isEmpty());

			t.begin();

			this.vmeDao.delete(informationSource);

			t.commit();

			Assert.assertNull(this.vmeDao.getEntityById(this.vmeDao.getEm(), InformationSource.class,
					informationSource.getId()));

			for (InformationSource inRfmo : rfmo.getInformationSourceList())
				Assert.assertNotEquals(inRfmo.getId(), informationSource.getId());

			for (GeneralMeasure gm : informationSource.getGeneralMeasureList()) {
				gm = this.vmeDao.getEntityById(this.vmeDao.getEm(), GeneralMeasure.class, gm.getId());

				for (InformationSource is : gm.getInformationSourceList())
					Assert.assertNotEquals(is.getId(), informationSource.getId());
			}

			for (SpecificMeasure sm : informationSource.getSpecificMeasureList()) {
				sm = this.vmeDao.getEntityById(this.vmeDao.getEm(), SpecificMeasure.class, sm.getId());

				Assert.assertNull(sm.getInformationSource());
			}
		}
	}

	@Test
	public void testDefaultDeleteFisheryAreas() {
		List<FisheryAreasHistory> fahs = this.vmeDao.loadObjects(FisheryAreasHistory.class);

		Rfmo parent;
		EntityTransaction t = this.em.getTransaction();

		for (FisheryAreasHistory fah : fahs) {
			parent = fah.getRfmo();

			t.begin();

			this.vmeDao.delete(fah);

			t.commit();

			Assert.assertNull(this.vmeDao.getEntityById(this.vmeDao.getEm(), FisheryAreasHistory.class, fah.getId()));

			parent = this.vmeDao.getEntityById(this.vmeDao.getEm(), Rfmo.class, parent.getId());

			for (FisheryAreasHistory cfah : parent.getHasFisheryAreasHistory()) {
				Assert.assertNotEquals(fah.getId(), cfah.getId());
			}
		}
	}

	@Test
	public void testDefaultDeleteVMEsHistory() {
		List<VMEsHistory> vhs = this.vmeDao.loadObjects(VMEsHistory.class);

		Rfmo parent;
		EntityTransaction t = this.em.getTransaction();

		for (VMEsHistory vh : vhs) {
			parent = vh.getRfmo();

			t.begin();

			this.vmeDao.delete(vh);

			t.commit();

			Assert.assertNull(this.vmeDao.getEntityById(this.vmeDao.getEm(), VMEsHistory.class, vh.getId()));

			parent = this.vmeDao.getEntityById(this.vmeDao.getEm(), Rfmo.class, parent.getId());

			for (VMEsHistory cvh : parent.getHasVmesHistory()) {
				Assert.assertNotEquals(vh.getId(), cvh.getId());
			}
		}
	}

	private Vme getNewVme() {
		MultiLingualStringUtil MLSu = new MultiLingualStringUtil();

		Vme vme = new Vme();
		vme.setAreaType("Risk area");
		vme.setCriteria("Fragility");
		vme.setName(MLSu.english("Foobazzi mountain"));
		vme.setGeoArea(MLSu.english("GeoArea"));
		vme.setGeoform("GeoForm");
		vme.setInventoryIdentifier("InventoryIdentifier");

		ValidityPeriod validityPeriod = new ValidityPeriod();
		validityPeriod.setBeginYear(1975);
		validityPeriod.setEndYear(2015);

		vme.setValidityPeriod(validityPeriod);

		GeoRef gr1 = new GeoRef();
		gr1.setYear(1975);
		gr1.setGeographicFeatureID("BRT");

		GeoRef gr2 = new GeoRef();
		gr2.setYear(2175);
		gr2.setGeographicFeatureID("DTH");

		vme.setGeoRefList(new ArrayList<GeoRef>(Arrays.asList(new GeoRef[] { gr1, gr2 })));

		Profile p1 = new Profile();
		p1.setYear(2012);
		p1.setDescriptionBiological(MLSu.english("DB_2012"));
		p1.setDescriptionImpact(MLSu.english("DI_2012"));
		p1.setDescriptionPhisical(MLSu.english("PH_2012"));
		p1.setGeoform(MLSu.english("GF_2012"));

		Profile p2 = new Profile();
		p2.setYear(2013);
		p2.setDescriptionBiological(MLSu.english("DB_2013"));
		p2.setDescriptionImpact(MLSu.english("DI_2013"));
		p2.setDescriptionPhisical(MLSu.english("PH_2013"));
		p2.setGeoform(MLSu.english("GF_2013"));

		vme.setProfileList(new ArrayList<Profile>(Arrays.asList(new Profile[] { p1, p2 })));

		InformationSource is1 = new InformationSource();
		is1.setId(1L);

		InformationSource is2 = new InformationSource();
		is2.setId(2L);

		SpecificMeasure s1 = new SpecificMeasure();
		s1.setInformationSource(is1);
		s1.setValidityPeriod(new ValidityPeriod());
		s1.getValidityPeriod().setBeginYear(1888);
		s1.getValidityPeriod().setEndYear(1988);
		s1.setYear(1888);
		s1.setVmeSpecificMeasure(MLSu.english("IS_1888"));

		SpecificMeasure s2 = new SpecificMeasure();
		s2.setInformationSource(is2);
		s2.setValidityPeriod(new ValidityPeriod());
		s2.getValidityPeriod().setBeginYear(1988);
		s2.getValidityPeriod().setEndYear(2008);
		s2.setYear(1988);
		s2.setVmeSpecificMeasure(MLSu.english("IS_1988"));

		vme.setSpecificMeasureList(new ArrayList<SpecificMeasure>(Arrays.asList(new SpecificMeasure[] { s1, s2 })));

		Rfmo rfmo = new Rfmo();
		rfmo.setId("CCAMLR");

		vme.setRfmo(rfmo);

		return vme;
	}

	@Test
	public void testCreateVme() throws Throwable {
		MultiLingualStringUtil MLSu = new MultiLingualStringUtil();

		EntityTransaction tx = this.vmeDao.getEm().getTransaction();

		Vme vme = this.getNewVme();
		Vme ref = this.getNewVme();

		tx.begin();

		ref = this.vmeDao.create(ref);

		tx.commit();

		ref = this.vmeDao.getEntityById(this.em, Vme.class, ref.getId());

		Assert.assertNotNull(ref);
		Assert.assertNotNull(ref.getId());
		Assert.assertNotNull(ref.getRfmo());
		Assert.assertNotNull(ref.getRfmo().getId());

		Assert.assertNotNull(ref.getName());

		Assert.assertEquals(MLSu.getEnglish(ref.getName()), MLSu.getEnglish(vme.getName()));

		Assert.assertEquals(vme.getGeoRefList().size(), ref.getGeoRefList().size());
		Assert.assertEquals(vme.getProfileList().size(), ref.getProfileList().size());
		Assert.assertEquals(vme.getSpecificMeasureList().size(), ref.getSpecificMeasureList().size());

		for (GeoRef geoRef : ref.getGeoRefList())
			Assert.assertEquals(ref.getId(), geoRef.getVme().getId());

		for (Profile profile : ref.getProfileList())
			Assert.assertEquals(ref.getId(), profile.getVme().getId());

		for (SpecificMeasure specificMeasure : ref.getSpecificMeasureList()) {
			Assert.assertEquals(ref.getId(), specificMeasure.getVme().getId());
			Assert.assertNotNull(specificMeasure.getInformationSource());
		}

		boolean found = false;

		Rfmo rfmo = this.vmeDao.getEntityById(this.em, Rfmo.class, ref.getRfmo().getId());

		for (Vme previous : rfmo.getListOfManagedVmes())
			if (previous.getId().equals(ref.getId())) {
				found = true;
				break;
			}

		Assert.assertTrue(found);
	}

	@Test
	public void testUpdateVme() throws Throwable {
		MultiLingualStringUtil MLSu = new MultiLingualStringUtil();

		EntityTransaction tx = this.vmeDao.getEm().getTransaction();

		Vme ref = this.getNewVme();

		tx.begin();

		ref = this.vmeDao.create(ref);

		tx.commit();

		// em.clear();
		//
		// Long profileId = ref.getProfileList().get(0).getId();
		//
		// ref.getProfileList().remove(0);
		//
		// ref = this.vmeDao.update(ref);
		//
		// Profile isDeleted = this.vmeDao.getEntityById(em, Profile.class,
		// profileId);
		//
		// Assert.assertNull(isDeleted);

		ref = this.vmeDao.getEntityById(this.em, Vme.class, ref.getId());

		Vme toUpdate = new Vme();
		toUpdate.setId(ref.getId());

		String previousRfmoId = ref.getRfmo().getId();
		String newRfmoId = "NAFO";

		Rfmo currentRfmo = new Rfmo();
		currentRfmo.setId(newRfmoId);

		toUpdate.setRfmo(currentRfmo);

		toUpdate.setGeoRefList(new ArrayList<GeoRef>());
		toUpdate.setProfileList(new ArrayList<Profile>());
		toUpdate.setSpecificMeasureList(new ArrayList<SpecificMeasure>());

		GeoRef newGeoRef;
		for (GeoRef geoRef : ref.getGeoRefList()) {
			newGeoRef = new GeoRef();

			newGeoRef.setId(geoRef.getId());
			newGeoRef.setYear(geoRef.getYear() + 1);
			newGeoRef.setGeographicFeatureID("U_" + geoRef.getGeographicFeatureID());

			toUpdate.getGeoRefList().add(newGeoRef);
		}

		newGeoRef = new GeoRef();
		newGeoRef.setYear(2150);
		newGeoRef.setGeographicFeatureID("N_GFID");
		toUpdate.getGeoRefList().add(newGeoRef);

		Profile newProfile;
		for (Profile profile : ref.getProfileList()) {
			newProfile = new Profile();

			newProfile.setId(profile.getId());
			newProfile.setYear(profile.getYear());
			newProfile.setDescriptionBiological(MLSu.english(MLSu.getEnglish(profile.getDescriptionBiological())));
			newProfile.setDescriptionPhisical(MLSu.english(MLSu.getEnglish(profile.getDescriptionPhisical())));
			newProfile.setDescriptionImpact(MLSu.english(MLSu.getEnglish(profile.getDescriptionImpact())));
			newProfile.setGeoform(MLSu.english(MLSu.getEnglish(profile.getGeoform())));

			toUpdate.getProfileList().add(newProfile);
		}

		// Long deletedProfile = toUpdate.getProfileList().remove(0).getId();
		toUpdate.getProfileList().remove(0).getId();

		newProfile = new Profile();
		newProfile.setYear(1492);
		newProfile.setGeoform(MLSu.english("Banana shaped"));
		newProfile.setDescriptionBiological(MLSu.english("Banana shaped"));
		newProfile.setDescriptionPhisical(MLSu.english("Banana shaped"));
		newProfile.setDescriptionImpact(MLSu.english("Banana shaped"));

		toUpdate.getProfileList().add(newProfile);

		SpecificMeasure newSpecificMeasure;
		for (SpecificMeasure specificMeasure : ref.getSpecificMeasureList()) {
			newSpecificMeasure = new SpecificMeasure();

			newSpecificMeasure.setId(specificMeasure.getId());
			newSpecificMeasure.setYear(specificMeasure.getYear());
			newSpecificMeasure.setValidityPeriod(specificMeasure.getValidityPeriod());
			newSpecificMeasure.setVmeSpecificMeasure(MLSu.english(MLSu.getEnglish(specificMeasure
					.getVmeSpecificMeasure())));
			newSpecificMeasure.setInformationSource(new InformationSource());
			newSpecificMeasure.getInformationSource().setId(specificMeasure.getInformationSource().getId());

			toUpdate.getSpecificMeasureList().add(newSpecificMeasure);
		}

		// Long deletedSpecificMeasure =
		// toUpdate.getSpecificMeasureList().remove(1).getId();
		toUpdate.getSpecificMeasureList().remove(1).getId();

		toUpdate.setName(MLSu.english("U_" + MLSu.getEnglish(ref.getName())));
		toUpdate.setGeoArea(MLSu.english("U_" + MLSu.getEnglish(ref.getGeoArea())));
		toUpdate.setGeoform("U_" + ref.getGeoform());
		toUpdate.setInventoryIdentifier("U_" + ref.getInventoryIdentifier());
		toUpdate.setAreaType(ref.getAreaType());
		toUpdate.setCriteria(null);
		toUpdate.setValidityPeriod(ref.getValidityPeriod());
		toUpdate.getValidityPeriod().setBeginYear(ref.getValidityPeriod().getBeginYear() - 100);
		toUpdate.getValidityPeriod().setEndYear(ref.getValidityPeriod().getEndYear() + 100);

		tx.begin();
		Vme updated = this.vmeDao.update(toUpdate);
		tx.commit();

		updated = this.vmeDao.getEntityById(this.em, Vme.class, toUpdate.getId());

		Assert.assertEquals(newRfmoId, updated.getRfmo().getId());
		Assert.assertEquals(2, updated.getProfileList().size());
		Assert.assertEquals(1, updated.getSpecificMeasureList().size());
		Assert.assertEquals(3, updated.getGeoRefList().size());

		// Temporarily removed...
		// Assert.assertNull(this.vmeDao.getEntityById(this.em, Profile.class,
		// deletedProfile));
		// Assert.assertNull(this.vmeDao.getEntityById(this.em,
		// SpecificMeasure.class, deletedSpecificMeasure));

		Rfmo previousRfmo = this.vmeDao.getEntityById(this.em, Rfmo.class, previousRfmoId);
		Rfmo newRfmo = this.vmeDao.getEntityById(this.em, Rfmo.class, newRfmoId);

		for (Vme vme : previousRfmo.getListOfManagedVmes()) {
			Assert.assertFalse(updated.getId().equals(vme.getId()));
		}

		boolean found = false;
		for (Vme vme : newRfmo.getListOfManagedVmes()) {
			found = updated.getId().equals(vme.getId());

			if (found)
				break;
		}

		Assert.assertTrue(found);
	}
}