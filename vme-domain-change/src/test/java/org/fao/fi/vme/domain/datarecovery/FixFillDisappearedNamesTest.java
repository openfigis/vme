package org.fao.fi.vme.domain.datarecovery;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

/**
 * In QA: Total amount of MultiLingualStrings 3513. Numer of Registered Without
 * String 18. Number of fixes applied: 18.
 * 
 * 
 * 
 * 
 * @author Erik van Ingen
 *
 */
@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class, ProduceTestList.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class FixFillDisappearedNamesTest {

	@Inject
	FixFillDisappearedNames f;

	@Test
	public void testFix() {
		f.fix();
	}

}
