package org.fao.fi.vme.domain.change4;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

/**
 * devel: Total amount of MultiLingualStrings 6073. Number of fixes applied: 10.
 * 
 * qa: Total amount of MultiLingualStrings 6336. Number of fixes applied: 0.
 * 
 * prod : Total amount of MultiLingualStrings 7195. Number of fixes applied: 0.
 * 
 * @author Erik van Ingen
 *
 */

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class CleanMexicoTest {
	@Inject
	CleanMexico f;

	@Test
	public void testFix() {

		f.fix();
	}

}
