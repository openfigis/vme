package org.fao.fi.vme;

import javax.inject.Inject;

import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class,
		FilesystemMsAccessConnectionProvider.class })
public class VmeWorkshop2014ModelChangeTest {

	@Inject
	VmeWorkshop2014ModelChange c;

	@Test
	public void testMigrate() {
		c.migrate();
	}

}
