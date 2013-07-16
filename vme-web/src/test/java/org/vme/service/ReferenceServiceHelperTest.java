/**
 * 
 */
package org.vme.service;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.dao.config.VmeDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vme.service.reference.impl.ReferenceServiceHelper;

/**
 * @author SIBENI
 *
 */


public class ReferenceServiceHelperTest {


	@Inject
	ReferenceServiceHelper helper;
	
	@Inject
	@VmeDB
	private EntityManager manager;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.vme.service.reference.impl.ReferenceServiceHelper#persistVmeCriterias()}.
	 */
	@Test
	public void testPersistVmeCriterias() {
		assert(true);
		//helper.getAuthority((long)20010);
	}

}
