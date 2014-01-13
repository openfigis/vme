/**
 * (c) 2013 FAO / UN (project: vme-dao)
 */
package org.fao.fi.figis.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
import org.junit.Assume;
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
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 20/dic/2013   Fabio     Creation.
 *
 * @version 1.0
 * @since 20/dic/2013
 */
@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class, VmeDataBaseProducer.class, JpaDaoFactory.class, EmbeddedMsAccessConnectionProvider.class })
public class CRUDJpaDaoTest {
	@Inject private VmeAccessDbImport importer;
	@Inject private VmeDao vmeDao;
	
	private EntityManager em;
	
	@PostConstruct
	public void postConstruct() {
		this.em = vmeDao.getEm();
		
		importer.importMsAccessData();
	}
	
	@Test
	public void testDeleteVme() {
		Vme vme = this.vmeDao.getEntityById(this.em, Vme.class, 1L);
		
		Collection<Profile> profiles = new ArrayList<Profile>();
		Collection<SpecificMeasure> specificMeasures = new ArrayList<SpecificMeasure>();
		Collection<GeoRef> georefs = new ArrayList<GeoRef>();
		
		for(Profile profile : vme.getProfileList()) {
			profiles.add(profile);
		}
		
		for(SpecificMeasure specificMeasure : vme.getSpecificMeasureList()) {
			specificMeasures.add(specificMeasure);
		}
		
		for(GeoRef georef : vme.getGeoRefList()) {
			georefs.add(georef);
		}
		
		this.vmeDao.remove(vme);
		
		Vme deleted = this.vmeDao.getEntityById(this.em, Vme.class, 1L);
		
		Assert.assertNull(deleted);
		
		Assert.assertFalse(profiles.isEmpty());
		
		for(Profile profile : profiles) {
			System.out.println("Checking null-ity of (previous) profile with ID " + profile.getId());
			Assert.assertNull(this.vmeDao.getEntityById(this.em, Profile.class, profile.getId()));
		}

		Assert.assertFalse(specificMeasures.isEmpty());

		for(SpecificMeasure measure : specificMeasures) {
			System.out.println("Checking null-ity of (previous) specific measure with ID " + measure.getId());
			Assert.assertNull(this.vmeDao.getEntityById(this.em, SpecificMeasure.class, measure.getId()));
			
			if(measure.getInformationSource() != null) {
				System.out.println("Checking non-nullity of previously linked information source with ID " + measure.getInformationSource().getId());
				Assert.assertNotNull(this.vmeDao.getEntityById(this.em, InformationSource.class, measure.getInformationSource().getId()));
			}
		}
		
		Assert.assertFalse(georefs.isEmpty());
		
		for(GeoRef georef : georefs) {
			System.out.println("Checking null-ity of (previous) geo ref with ID " + georef.getId());
			Assert.assertNull(this.vmeDao.getEntityById(this.em, GeoRef.class, georef.getId()));
		}
	}
	
	@Test
	public void testDeleteGeoRefFromVme() {
		Vme vme = this.vmeDao.getEntityById(this.em, Vme.class, 1L);
		
		Iterator<GeoRef> iterator = vme.getGeoRefList().iterator();
		GeoRef georef;

		Collection<Long> removed = new ArrayList<Long>();
		
		while(iterator.hasNext()) {
			georef = iterator.next();
			
			iterator.remove();
			this.vmeDao.remove(georef);
			
			removed.add(georef.getId());
		}
		
		this.vmeDao.merge(vme);
		
		vme = this.vmeDao.getEntityById(this.em, Vme.class, 1L);
		
		Assert.assertTrue(vme.getGeoRefList() == null || vme.getGeoRefList().isEmpty() || ( vme.getGeoRefList().size() == 1 && vme.getGeoRefList().get(0) == null ) );
		
		for(Long id : removed)
			Assert.assertNull(this.vmeDao.getEntityById(this.em, GeoRef.class, id));
	}
	
	@Test
	public void testDeleteGeoRef() {
		GeoRef geoRef = this.vmeDao.getEntityById(this.em, GeoRef.class, 1L);
		Vme vme = this.vmeDao.getEntityById(this.em, Vme.class, geoRef.getVme().getId());
		
		GeoRef current = null;
		Iterator<GeoRef> iterator = vme.getGeoRefList() != null ? vme.getGeoRefList().iterator() : null;
		
		if(iterator != null) {
			while(iterator.hasNext()) {
				current = iterator.next();
				
				if(current.getId().equals(geoRef.getId()))
					iterator.remove();
			}
			
			if(current != null) {
				this.vmeDao.remove(current);
			
				this.vmeDao.merge(vme);
			}
		}

		Assert.assertNull(this.vmeDao.getEntityById(this.em, GeoRef.class, 1L));
		
		vme = this.vmeDao.getEntityById(this.em, Vme.class, 1L);
		
		Assert.assertTrue(vme.getGeoRefList() == null || vme.getGeoRefList().isEmpty() || ( vme.getGeoRefList().size() == 1 && vme.getGeoRefList().get(0) == null ) );
	}
	
	@Test
	public void testDefaultDeleteGeoRef() {
		GeoRef ref = this.vmeDao.getEntityById(this.em, GeoRef.class, 1L);
		
		Vme vme = this.vmeDao.getEntityById(this.em, Vme.class, ref.getVme().getId());
		
		this.vmeDao.deleteGeoRef(ref);
		
		ref = this.vmeDao.getEntityById(this.em, GeoRef.class, 1L);
		
		Assert.assertNull(ref);
		
		vme = this.vmeDao.getEntityById(this.em, Vme.class, vme.getId());
		
		for(GeoRef geoRef : vme.getGeoRefList())
			Assert.assertNotEquals(geoRef.getId(), new Long(1));
	}
	
	@Test
	public void testDefaultDeleteProfile() {
		Profile ref = this.vmeDao.getEntityById(this.em, Profile.class, 1L);
		
		Assume.assumeTrue(ref.getVme() != null);
		
		Vme vme = this.vmeDao.getEntityById(this.em, Vme.class, ref.getVme().getId());
		
		this.vmeDao.deleteProfile(ref);
		
		ref = this.vmeDao.getEntityById(this.em, Profile.class, 1L);
		
		Assert.assertNull(ref);
		
		vme = this.vmeDao.getEntityById(this.em, Vme.class, vme.getId());
		
		for(Profile profile : vme.getProfileList())
			Assert.assertNotEquals(profile.getId(), new Long(1));
	}
	
	@Test
	public void testDeleteInformationSource() {
		InformationSource informationSource = this.vmeDao.getEntityById(this.vmeDao.getEm(), InformationSource.class, 44L);
		
		Assert.assertNotNull(informationSource.getRfmo());

		Rfmo parent = informationSource.getRfmo();
		Iterator<InformationSource> rfmoIterator = parent.getInformationSourceList().iterator();
				
		while(rfmoIterator.hasNext()) {
			if(rfmoIterator.next().getId().equals(informationSource.getId()))
				rfmoIterator.remove();
		}
		
		this.vmeDao.merge(parent);
		
		parent = this.vmeDao.getEntityById(this.vmeDao.getEm(), Rfmo.class, parent.getId());
		
		for(InformationSource sources : parent.getInformationSourceList())
			Assert.assertNotEquals(informationSource.getId(), sources.getId());

		if(!informationSource.getSpecificMeasureList().isEmpty()) {
			for(SpecificMeasure specificMeasure : informationSource.getSpecificMeasureList()) {
				specificMeasure.setInformationSource(null);
				
				this.vmeDao.merge(specificMeasure);
			}
				
			for(SpecificMeasure specificMeasure : informationSource.getSpecificMeasureList()) {
				Assert.assertTrue(this.vmeDao.getEntityById(this.vmeDao.getEm(), SpecificMeasure.class, specificMeasure.getId()).getInformationSource() == null);
			}
		}
		
		if(!informationSource.getGeneralMeasureList().isEmpty()) {
			for(GeneralMeasure generalMeasure : informationSource.getGeneralMeasureList()) {
				Iterator<InformationSource> generalMeasureIterator = generalMeasure.getInformationSourceList().iterator();
				
				while(generalMeasureIterator.hasNext()) {
					if(generalMeasureIterator.next().getId().equals(informationSource.getId()))
						generalMeasureIterator.remove();
				}
				
				this.vmeDao.merge(generalMeasure);
			}
			
			for(GeneralMeasure specificMeasure : informationSource.getGeneralMeasureList()) {
				for(InformationSource sources : specificMeasure.getInformationSourceList()) 
					Assert.assertNotEquals(informationSource.getId(), sources.getId());
			}
		}

		this.vmeDao.remove(informationSource);
		
		informationSource = this.vmeDao.getEntityById(this.vmeDao.getEm(), InformationSource.class, informationSource.getId());
		
		Assert.assertNull(informationSource);
	}
}