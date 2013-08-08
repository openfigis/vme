package org.fao.fi.vme.sync2;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.test.VmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class })
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
