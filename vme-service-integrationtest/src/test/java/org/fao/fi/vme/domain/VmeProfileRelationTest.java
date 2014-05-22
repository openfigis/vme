package org.fao.fi.vme.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.InformationSourceType;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.msaccess.VmeClean;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class, VmeDataBaseProducer.class })
public class VmeProfileRelationTest {

	@Inject
	VmeDao vmeDao;

	VmeClean c = new VmeClean();

	@Before
	public void beforeTest() {
		c.clean(vmeDao);
	}

	@After
	public void afterTest() {
		c.clean(vmeDao);
	}

	@Test
	public void test() {
//		InformationSourceType defaultIST = InformationSourceMock
//				.createInformationSourceType();
//
//		vmeDao.persist(defaultIST);
		
		Vme vme = new Vme();
		Profile profile = new Profile();
		profile.setVme(vme);
		List<Profile> profileList = new ArrayList<Profile>();
		profileList.add(profile);
		vme.setProfileList(profileList);
		vmeDao.persist(vme);
		assertEquals(1, vmeDao.count(Vme.class).intValue());
		assertEquals(1, vmeDao.count(Profile.class).intValue());
		assertEquals(1, vmeDao.findVme(vme.getId()).getProfileList().size());

		Vme vmeFound = vmeDao.getEm().find(Vme.class, vme.getId());
		Profile profileFound = vmeDao.getEm().find(Profile.class, profile.getId());

		assertEquals(1, vmeFound.getProfileList().size());
		assertNotNull(profileFound.getVme());

	}
}
