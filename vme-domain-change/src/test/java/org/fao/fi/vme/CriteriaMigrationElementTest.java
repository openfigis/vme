package org.fao.fi.vme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.fao.fi.vme.batch.reference.ReferenceDataHardcodedBatch;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class,
		FilesystemMsAccessConnectionProvider.class })
public class CriteriaMigrationElementTest {

	@Inject
	CriteriaMigrationElement c;

	@Inject
	ReferenceDataHardcodedBatch b;

	@Inject
	VmeDao vmeDao;

	@Before
	public void before() {
		b.run();
	}

	@Test
	public void testMigrate() {
		Vme vme = new Vme();
		vme.setCriteria("Fragility");
		vmeDao.persist(vme);
		c.migrate();
		Vme vmeFound = vmeDao.findVme(vme.getId());
		assertNotNull(vmeFound.getCriteriaList());
		assertEquals(1, vmeFound.getCriteriaList().size());
	}
}
