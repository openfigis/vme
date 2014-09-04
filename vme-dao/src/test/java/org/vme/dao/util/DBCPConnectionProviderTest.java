package org.vme.dao.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.DoubleEntityManager;
import org.vme.dao.config.vme.VmeDB;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class DBCPConnectionProviderTest {

	@Inject
	@VmeDB
	private EntityManager em;

	@Inject
	private VmeDao vmeDao;

	@Inject
	DBCPConnectionProvider cp;

	@Inject
	private DoubleEntityManager d;

	@Before
	public void before() {
		d.createNewEm();
	}

	@Test
	public void testConfigurePool() {
		assertNotNull(em.getProperties());
		for (Iterator<String> iter = em.getProperties().keySet().iterator(); iter.hasNext();) {
			String key = iter.next();
			System.out.println(key + " - " + em.getProperties().get(key));
		}

		// assertTrue((Boolean)
		// em.getProperties().get("hibernate.dbcp.testOnBorrow"));
	}

	@Test
	public void testGetConnection() {
		Vme vme = VmeMock.create();
		vmeDao.saveVme(vme);
		assertEquals(1, vmeDao.count(Vme.class).intValue());

	}

}
