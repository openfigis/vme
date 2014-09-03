package org.fao.fi.vme.domain.datarecovery;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class, ProduceListImpl.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class FixRunner4Real {

	@Inject
	FixDisappearedNames f;

	@Test
	public void testFix() {
		f.fix();
	}
}
