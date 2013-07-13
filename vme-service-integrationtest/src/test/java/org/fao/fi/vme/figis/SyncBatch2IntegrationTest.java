package org.fao.fi.vme.figis;

import javax.inject.Inject;

import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.fao.fi.vme.domain.Rfmo;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.sync2.SyncBatch2;
import org.fao.fi.vme.test.FigisDaoTestLogic;
import org.fao.fi.vme.test.ValidityPeriodMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class })
public class SyncBatch2IntegrationTest extends FigisDaoTestLogic {

	private static int INSERTED = 5;

	@Inject
	SyncBatch2 syncBatch2;

	@Inject
	VmeDao vmeDao;

	@Before
	public void testBefore() {
		int start = 100000;

		for (int i = start; i < start + INSERTED; i++) {
			// make sure at least those 5 objects do exist
			Long id = new Long(i);
			if (vmeDao.findVme(id) == null) {
				Vme vme = new Vme();
				vme.setValidityPeriod(ValidityPeriodMock.create());
				vme.setId(new Long(i));
				Rfmo rfmo = new Rfmo();
				rfmo.setId(id);
				vmeDao.persist(rfmo);
				vme.setRfmo(rfmo);

				vmeDao.persist(vme);

			}
			// remove them from figis, if they exist
			RefVme refVme = (RefVme) dao.find(RefVme.class, id);
			if (refVme != null) {
				dao.remove(refVme);
			}
		}
	}

	@Test
	public void testSyncFigisWithVme() {
		int c[] = count();
		int totalR = dao.count(RefVme.class).intValue() + INSERTED;
		syncBatch2.syncFigisWithVme();
		assertEquals(totalR, dao.count(RefVme.class).intValue());
		checkCount(c, INSERTED);

		// a subsequent synch should return the same numbers
		syncBatch2.syncFigisWithVme();
		assertEquals(totalR, dao.count(RefVme.class).intValue());
		checkCount(c, INSERTED);

	}
}
