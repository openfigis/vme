/**
 * 
 */
package org.vme.service;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.vme.VmeDB;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.web.service.DbBootstrapper;

/**
 * @author Fabrizio Sibeni
 *
 */


@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FilesystemMsAccessConnectionProvider.class })
public class DbBootstrapperTest {


	@Inject
	DbBootstrapper bootstrapper;
	
	@Inject
	@VmeDB
	private EntityManager manager;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		bootstrapper.bootDb();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.vme.web.service.DbBootstrapper#bootDb()}.
	 */
	@Test
	public void testBootDb() {
		Vme result =  manager.find(Vme.class, new Long(1));
		System.out.println("--------------------Got VME object from DBMS!--------------------");
		System.out.println("id.................." + result.getId());
		System.out.println("area type..........." + result.getAreaType());
		System.out.println("geoform............." + result.getGeoform());
		System.out.println("-----------------------------------------------------------------");
		
		
		
		result.getId();
	}

}
