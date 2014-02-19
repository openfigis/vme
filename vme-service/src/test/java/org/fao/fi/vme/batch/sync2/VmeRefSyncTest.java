package org.fao.fi.vme.batch.sync2;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseConfigurationTest;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.vme.VmeDataBaseConfigurationTest;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseConfigurationTest.class, VmeDataBaseProducer.class, FigisDataBaseProducer.class, FigisDataBaseConfigurationTest.class })
public class VmeRefSyncTest {

	@Inject
	VmeRefSync vmeRefSync;

	@Inject
	VmeDao vmeDao;

	@Inject
	FigisDao figisDao;

	/**
	 * 
	 */
	@Test
	public void testSync() {
		assertNrOfObjects(0);
		vmeDao.persist(VmeMock.create());
		vmeRefSync.sync();
		assertNrOfObjects(1);
		vmeRefSync.sync();
		assertNrOfObjects(1);

	}

	private void assertNrOfObjects(int i) {
		assertEquals(i, figisDao.count(RefVme.class).intValue());
		assertEquals(i, vmeDao.count(Vme.class).intValue());
	}

}
