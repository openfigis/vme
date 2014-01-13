/**
 * (c) 2013 FAO / UN (project: vme-dao)
 */
package org.fao.fi.figis.dao;

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
				
		for(Vme vme : ref.getVmeList()) {
			this.vmeDao.getEntityById(this.em, Vme.class, vme.getId());

			for (SpecificMeasure specificMeasure : vme.getSpecificMeasureList())
				Assert.assertNotEquals(specificMeasure.getId(), ref.getId());
		}
	}

	@Test
	public void testDefaultDeleteInformationSource() {
		EntityTransaction et = this.vmeDao.getEm().getTransaction();
		
		InformationSource ref = this.vmeDao.getEntityById(this.vmeDao.getEm(), InformationSource.class, 44L);

		Assert.assertNotNull(ref);
		
		et.begin();
		
		this.vmeDao.delete(ref);
		
		et.commit();
		
		Assert.assertNull(this.vmeDao.getEntityById(this.vmeDao.getEm(), InformationSource.class, ref.getId()));
				
		Rfmo rfmo = this.vmeDao.getEntityById(this.em, Rfmo.class, ref.getRfmo().getId());
		
		for(InformationSource inRfmo : rfmo.getInformationSourceList())
			Assert.assertNotEquals(inRfmo.getId(), ref.getId());
		
		for(GeneralMeasure gm : ref.getGeneralMeasureList()) {
			gm = this.vmeDao.getEntityById(this.vmeDao.getEm(), GeneralMeasure.class, gm.getId());
			
			for(InformationSource is : gm.getInformationSourceList())
				Assert.assertNotEquals(is.getId(), ref.getId());
		}
		
		for(SpecificMeasure sm : ref.getSpecificMeasureList()) {
			sm = this.vmeDao.getEntityById(this.vmeDao.getEm(), SpecificMeasure.class, sm.getId());
			
			Assert.assertNull(sm.getInformationSource());
		}
	}
}