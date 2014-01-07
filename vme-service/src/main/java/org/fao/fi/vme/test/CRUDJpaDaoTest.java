/**
 * (c) 2013 FAO / UN (project: vme-dao)
 */
package org.fao.fi.vme.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
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
		
		this.vmeDao.remove(vme);
		
		Vme deleted = this.vmeDao.getEntityById(this.em, Vme.class, 1L);
		
		Assert.assertNull(deleted);
		
		for(Profile profile : vme.getProfileList()) {
			System.out.println("Checking null-ity of (previous) profile with ID " + profile.getId());
			Assert.assertNull(this.vmeDao.getEntityById(this.em, Profile.class, profile.getId()));
		}
		
		for(SpecificMeasure measure : vme.getSpecificMeasureList()) {
			System.out.println("Checking null-ity of (previous) specific measure with ID " + measure.getId());
			Assert.assertNull(this.vmeDao.getEntityById(this.em, SpecificMeasure.class, measure.getId()));
			
			if(measure.getInformationSource() != null) {
				System.out.println("Checking non-nullity of previously linked information source with ID " + measure.getInformationSource().getId());
				Assert.assertNotNull(this.vmeDao.getEntityById(this.em, InformationSource.class, measure.getInformationSource().getId()));
			}
		}
		
		for(GeoRef georef : vme.getGeoRefList()) {
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
}
