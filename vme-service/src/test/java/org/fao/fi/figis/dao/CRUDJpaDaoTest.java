/**
 * (c) 2013 FAO / UN (project: vme-dao)
 */
package org.fao.fi.figis.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.VmeAccessDbImport;
import org.fao.fi.vme.msaccess.component.EmbeddedMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.JpaDaoFactory;
import org.vme.service.dao.config.figis.FigisDataBaseProducer;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.service.dao.sources.vme.VmeDao;

/**
 * Place your class / interface description here.
 * 
 * History:
 * 
 * ------------- --------------- ----------------------- Date Author Comment
 * ------------- --------------- ----------------------- 20/dic/2013 Fabio
 * Creation.
 * 
 * @version 1.0
 * @since 20/dic/2013
 */
@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class, VmeDataBaseProducer.class, JpaDaoFactory.class,
		EmbeddedMsAccessConnectionProvider.class })
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

		Vme vme = this.vmeDao.getEntityById(this.em, Vme.class, 1L);
		
		et.begin();
		
		this.vmeDao.delete(vme);

		et.commit();
		
		for (Profile profile : vme.getProfileList()) {
			System.out.println("Checking null-ity of (previous) profile with ID " + profile.getId());
			Assert.assertNull(this.vmeDao.getEntityById(this.em, Profile.class, profile.getId()));
		}

		for (SpecificMeasure measure : vme.getSpecificMeasureList()) {
			System.out.println("Checking null-ity of (previous) specific measure with ID " + measure.getId());
			Assert.assertNull(this.vmeDao.getEntityById(this.em, SpecificMeasure.class, measure.getId()));
		}

		for (GeoRef georef : vme.getGeoRefList()) {
			System.out.println("Checking null-ity of (previous) geo ref with ID " + georef.getId());
			Assert.assertNull(this.vmeDao.getEntityById(this.em, GeoRef.class, georef.getId()));
		}
		

		vme = this.vmeDao.getEntityById(this.em, Vme.class, 1L);

		Assert.assertNull(vme);
	}

	@Test
	public void testDefaultDeleteGeoRef() {
		EntityTransaction et = this.vmeDao.getEm().getTransaction();
		
		GeoRef ref = this.vmeDao.getEntityById(this.em, GeoRef.class, 1L);

		Assert.assertNotNull(ref);
		
		et.begin();
		
		this.vmeDao.delete(ref);
		
		et.commit();
		
		Assert.assertNull(this.vmeDao.getEntityById(this.em, GeoRef.class, 1L));
		
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

		Assert.assertNull(this.vmeDao.getEntityById(this.em, Profile.class, 1L));
		
		Vme vme = this.vmeDao.getEntityById(this.em, Vme.class, ref.getVme().getId());

		for (Profile profile : vme.getProfileList())
			Assert.assertNotEquals(profile.getId(), ref.getId());
	}

	@Test
	public void testDefaultDeleteInformationSource() {
		EntityTransaction et = this.vmeDao.getEm().getTransaction();
		
		InformationSource informationSource = this.vmeDao.getEntityById(this.vmeDao.getEm(), InformationSource.class, 44L);

		Assert.assertNotNull(informationSource.getRfmo());
		
		Rfmo rfmo = this.vmeDao.getEntityById(this.em, Rfmo.class, informationSource.getRfmo().getId());
		
		List<GeneralMeasure> generalMeasures = informationSource.getGeneralMeasureList();
		List<SpecificMeasure> specificMeasures = informationSource.getSpecificMeasureList();
		
		this.vmeDao.delete(informationSource);
		
		
	}
}