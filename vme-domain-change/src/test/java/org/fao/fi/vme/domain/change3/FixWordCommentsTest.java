package org.fao.fi.vme.domain.change3;

import javax.inject.Inject;

import org.fao.fi.vme.domain.datarecovery.ProduceTestList;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class, ProduceTestList.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class FixWordCommentsTest {

	@Inject
	FixWordComments f;

	@Inject
	VmeDao vmeDao;

	@Test
	public void testFix() {

		f.fix();
	}

}
