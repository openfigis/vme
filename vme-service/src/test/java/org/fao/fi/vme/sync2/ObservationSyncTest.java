package org.fao.fi.vme.sync2;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.fao.fi.vme.domain.Rfmo;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.test.RefVmeMock;
import org.fao.fi.vme.test.VmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class })
public class ObservationSyncTest {

	@Inject
	ObservationSync observationSync;

	@Inject
	FigisDao figisDao;

	@Inject
	VmeDao vmeDao;

	Long id;

	@Before
	public void generateVme() {
		Vme vme = VmeMock.create();
		RefVme refVme = RefVmeMock.create();
		vme.setRfmo(new Rfmo());
		id = refVme.getId();
		refVme.setId(vme.getId());
		figisDao.persist(refVme);
		vmeDao.persist(vme);
	}

	/**
	 * TODO test the update
	 */
	@Ignore
	@Test
	public void testSync() {
		assertNrOfObjects(0);
		observationSync.sync();
		assertNrOfObjects(1);

		// // test repeatability
		observationSync.sync();
		observationSync.sync();
		assertNrOfObjects(1);
	}

	@Ignore
	@Test
	public void testSyncWithUpdate() {
		observationSync.sync();
		assertNrOfObjects(1);
		VmeObservationDomain vod = figisDao.findVmeObservationDomainByVme(id);

		// TODO
		// Integer y = new Integer(vod.getReportingYear()).intValue() + 1;
		Vme vme = vmeDao.findVme(id);

		// TODO
		// vme.getValidityPeriod().setEndYear(y);
		vmeDao.merge(vme);
		observationSync.sync();

		// TODO
		// vod = figisDao.findVmeObservationDomain(id);

		// TODO
		// asssertEquals(y.toString(), vod.getReportingYear());
	}

	private void asssertEquals(String string, String reportingYear) {
		// TODO Auto-generated method stub

	}

	private void assertNrOfObjects(int i) {
		assertEquals(i, figisDao.count(VmeObservation.class).intValue());
		assertEquals(i, figisDao.count(Observation.class).intValue());
		assertEquals(i, figisDao.count(ObservationXml.class).intValue());
	}
};
