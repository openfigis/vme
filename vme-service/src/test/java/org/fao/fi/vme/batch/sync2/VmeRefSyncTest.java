package org.fao.fi.vme.batch.sync2;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.test.VmeTypeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, VmePersistenceUnitConfiguration.class,
		FigisPersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class, FigisDataBaseProducer.class })
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
		vmeDao.persist(VmeTypeMock.create());
		vmeDao.persist(VmeMock.create());
		vmeRefSync.sync();
		assertNrOfObjects(1);
		vmeRefSync.sync();
		assertNrOfObjects(1);

	}

	/**
	 * 
	 */
	@Test
	public void testSyncVme() {
		RefVme r = new RefVme();
		r.setId(100l);
		assertEquals(0, figisDao.count(RefVme.class).intValue());
		figisDao.persist(r);
		assertEquals(1, figisDao.count(RefVme.class).intValue());
		vmeRefSync.sync();
		assertNrOfObjects(0);

	}

	private void assertNrOfObjects(int i) {
		assertEquals(i, figisDao.count(RefVme.class).intValue());
		assertEquals(i, vmeDao.count(Vme.class).intValue());
	}

}
