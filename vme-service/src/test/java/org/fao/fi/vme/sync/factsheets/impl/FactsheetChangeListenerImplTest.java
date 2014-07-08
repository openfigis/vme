package org.fao.fi.vme.sync.factsheets.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.RefWaterArea;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.batch.sync2.SyncBatch2;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.test.VmeScopeMock;
import org.fao.fi.vme.domain.test.VmeTypeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.sync.factsheets.listeners.FactsheetChangeListener;
import org.fao.fi.vme.sync.factsheets.listeners.impl.SyncFactsheetChangeListener;
import org.fao.fi.vme.sync.factsheets.updaters.impl.FigisFactsheetUpdater;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, SyncFactsheetChangeListener.class, FigisFactsheetUpdater.class,
		VmeDataBaseProducerApplicationScope.class, VmeTestPersistenceUnitConfiguration.class,
		FigisDataBaseProducer.class, FigisTestPersistenceUnitConfiguration.class })
public class FactsheetChangeListenerImplTest {

	/**
	 * SyncFactsheetChangeListener will be injected here
	 */
	@Inject
	FactsheetChangeListener l;

	@Inject
	private SyncBatch2 syncBatch2;

	@Inject
	VmeDao vmeDao;

	@Inject
	FigisDao figisDao;

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Ignore
	@Test
	public void testDoCreateFactsheets() {
	}

	@Test
	public void testDoUpdateFactsheets() throws Throwable {

		vmeDao.persist(InformationSourceMock.createInformationSourceType());
		vmeDao.persist(VmeTypeMock.create());
		vmeDao.persist(VmeScopeMock.create());

		String before = "before";
		Vme vme = VmeMock.generateVme(1);
		Rfmo rfmo = new Rfmo();
		rfmo.setId("fjdskjfds");

		vme.setName(u.english(before));

		vmeDao.saveVme(vme);
		syncBatch2.syncFigisWithVme();
		assertTrue(figisDao.loadObjects(ObservationXml.class).get(0).getXml().contains(before));
		delegateCount();
		String after = "after";
		vme.setName(u.english(after));

		EntityTransaction et = vmeDao.begin();

		try {
			vmeDao.update(vme);
			vmeDao.commit(et);

			l.vmeChanged(vme);
			delegateCount();

			System.out.println(figisDao.loadObjects(ObservationXml.class).get(0).getXml());
		} catch (Throwable t) {
			System.err.println("An error occurred while attempting to update the Vme. Rolling back the transaction");

			if (et.isActive())
				vmeDao.rollback(et);
		}

		assertTrue(figisDao.loadObjects(ObservationXml.class).get(0).getXml().contains(after));
	}

	private void delegateCount() {
		assertEquals(1, figisDao.count(ObservationXml.class).intValue());
		assertEquals(1, figisDao.count(RefVme.class).intValue());
		assertEquals(1, figisDao.count(Observation.class).intValue());
		assertEquals(1, figisDao.count(RefWaterArea.class).intValue());
		assertEquals(1, figisDao.count(VmeObservation.class).intValue());

	}

	@Ignore
	@Test
	public void testDoDeleteFactsheets() {
		// TODO("Not yet implemented");
	}

}
