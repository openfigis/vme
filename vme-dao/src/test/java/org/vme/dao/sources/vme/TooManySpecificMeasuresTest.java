package org.vme.dao.sources.vme;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.PersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDB;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class TooManySpecificMeasuresTest {

	@Inject
	@VmeDB
	protected PersistenceUnitConfiguration config;

	/**
	 * select count(*)
	 * 
	 * from VMETEST.Vme vme0_
	 * 
	 * left outer join VMETEST.Rfmo rfmo3_ on vme0_.rfmo_id=rfmo3_.id
	 * 
	 * left outer join VMETEST.GENERAL_MEASURE generalmea4_ on rfmo3_.id=generalmea4_.rfmo_id
	 * 
	 * left outer join VMETEST.SPECIFIC_MEASURE specificme10_ on vme0_.id=specificme10_.vme_id
	 * 
	 * left outer join VMETEST.Rfmo rfmo15_ on informatio11_.rfmo_id=rfmo15_.id
	 * 
	 * left outer join VMETEST.INFORMATION_SOURCE_TYPE informatio16_ on informatio11_.sourceType_id=informatio16_.id
	 * 
	 * where vme0_.id=?
	 * 
	 * The vme-configuration/src/main/resources/META-INF/persistence.xml has now this setting:<property
	 * name="hibernate.max_fetch_depth" value="0" />
	 * 
	 * This setting prevents Hibernate from generating a left outer join like the one above. This was causing problems
	 * on the level of SpecificMeasures, they were duplicated.
	 * 
	 * 
	 */
	@Test
	public void testTooManySpecificMeasures() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory(this.config.getPersistenceUnitName());
		EntityManager em = emf.createEntityManager();

		EntityTransaction t = em.getTransaction();
		t.begin();

		Vme vme = new Vme();
		em.persist(vme);

		Rfmo rfmo = new Rfmo();
		rfmo.setId("fjiedowfjewoirewiorfew");
		List<GeneralMeasure> gmList = new ArrayList<GeneralMeasure>();
		for (int i = 0; i < 10; i++) {
			GeneralMeasure g = new GeneralMeasure();
			g.setRfmo(rfmo);
			em.persist(g);
			gmList.add(g);
		}

		List<Vme> listOfManagedVmes = new ArrayList<Vme>();
		listOfManagedVmes.add(vme);
		rfmo.setListOfManagedVmes(listOfManagedVmes);
		rfmo.setGeneralMeasureList(gmList);
		em.persist(rfmo);

		List<SpecificMeasure> ls = new ArrayList<SpecificMeasure>();
		for (int i = 0; i < 2; i++) {
			SpecificMeasure s = new SpecificMeasure();
			em.persist(s);
			s.setVme(vme);
			ls.add(s);
		}

		vme.setSpecificMeasureList(ls);
		vme.setRfmo(rfmo);
		em.merge(vme);
		t.commit();

		Properties props = new Properties();
		props.setProperty("javax.persistence.jdbc.url", "jdbc:h2:mem:db1");
		props.setProperty("hibernate.hbm2ddl.auto", "validate");

		EntityManagerFactory newEmf = Persistence.createEntityManagerFactory(this.config.getPersistenceUnitName(),
				props);

		EntityManager emNew = newEmf.createEntityManager();

		Vme vmeFound = emNew.find(Vme.class, vme.getId());

		// if "hibernate.max_fetch_depth is not set correctly, the result will be a higher than 2
		assertEquals(2, vmeFound.getSpecificMeasureList().size());

	}
}
