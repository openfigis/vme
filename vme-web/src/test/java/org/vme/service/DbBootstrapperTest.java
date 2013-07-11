/**
 * 
 */
package org.vme.service;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.web.service.DbBootstrapper;

/**
 * @author Fabrizio Sibeni
 *
 */


@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class DbBootstrapperTest {


	@Inject
	DbBootstrapper bootstrapper;
	
	
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
	 * Test method for {@link org.vme.web.service.DbBootstrapper#bootDb()}.
	 */
	@Test
	public void testBootDb() {
		try {
			bootstrapper.bootDb();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
