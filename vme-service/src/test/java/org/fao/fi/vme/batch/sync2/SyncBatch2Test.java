package org.fao.fi.vme.batch.sync2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.test.VmeTypeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
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
@AdditionalClasses({ ReferenceDaoImpl.class })
@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class,
		FigisDataBaseProducer.class, FigisTestPersistenceUnitConfiguration.class })
public class SyncBatch2Test {

	@Inject
	private SyncBatch2 syncBatch2;

	@Inject
	private VmeDao vmeDao;

	@Inject
	private FigisDao figisDao;

	@Test
	public void testSyncFigisWithVmePrimaryRule() {
		Vme vme = VmeMock.create();
		assertEquals(ValidityPeriodMock.YEARS, vme.getGeoRefList().size());

		Rfmo rfmo = new Rfmo();
		rfmo.setId("RFMO");
		vmeDao.persist(rfmo);
		vmeDao.persist(VmeTypeMock.create());

		vme.setRfmo(rfmo);
		vmeDao.persist(vme);

		syncBatch2.syncFigisWithVme();

		List<?> oList = figisDao.loadObjects(Observation.class);
		List<?> nonPrimary = oList.subList(0, oList.size() - 2);
		for (Object object : nonPrimary) {
			Observation o = (Observation) object;
			assertFalse(o.isPrimary());
		}
		Observation o = (Observation) oList.get(oList.size() - 1);
		assertTrue(o.isPrimary());
		assertEquals(ValidityPeriodMock.YEARS, oList.size());

	}
}
