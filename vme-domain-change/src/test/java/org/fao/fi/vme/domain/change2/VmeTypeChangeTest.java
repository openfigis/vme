package org.fao.fi.vme.domain.change2;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDB;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class, })
public class VmeTypeChangeTest {

	@Inject
	@VmeDB
	private EntityManager em;

	@Inject
	VmeTypeChange change;

	/**
	 * 1) add column with hibernate
	 * 
	 * 2) update data with SQL directly
	 * 
	 * update vme set areatype_id = 30 where areatype = 'Risk area'
	 * 
	 * 
	 */
	@Test
	public void test() {

		assertNotNull(change);
		change.migrate();
	}

}
