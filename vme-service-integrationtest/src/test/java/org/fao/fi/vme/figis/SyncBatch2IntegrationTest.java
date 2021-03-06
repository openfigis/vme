package org.fao.fi.vme.figis;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.test.RefVmeMock;
import org.fao.fi.vme.batch.reference.ReferenceDataHardcodedBatch;
import org.fao.fi.vme.batch.sync2.SyncBatch2;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.reference.InformationSourceType;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.test.VmeScopeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.dao.test.FigisDaoTestLogic;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, VmePersistenceUnitConfiguration.class,
		FigisPersistenceUnitConfiguration.class })
@AdditionalClasses({ FigisDataBaseProducer.class, VmeDataBaseProducerApplicationScope.class })
public class SyncBatch2IntegrationTest extends FigisDaoTestLogic {

	@Inject
	SyncBatch2 syncBatch2;

	@Inject
	VmeDao vmeDao;

	@Inject
	FigisDao figisDao;

	@Inject
	ReferenceDataHardcodedBatch referenceDataHardcodedBatch;

	@Before
	public void testBefore() {
		clean();
		cleanVme();
		cleanRefFigis();
		vmeDao.persist(VmeScopeMock.create());
		referenceDataHardcodedBatch.run();
	}

	@After
	public void testAfter() {
		clean();
		cleanVme();
	}

	@Test
	public void testSyncFigisWithVme() {
		prepareDB();
		Vme vme = vmeDao.loadObjects(Vme.class).get(0);

		RefVme refVme = RefVmeMock.create();
		refVme.setId(vme.getId());

		int totalRefVme = dao.count(RefVme.class).intValue();
		syncBatch2.syncFigisWithVme();

		// apparently the algorithm generates 1 observation, not validated
		// further here.
		int c[] = count();
		System.out.println(c);

		// More important, a subsequent synch should return the same numbers
		syncBatch2.syncFigisWithVme();

		int difference = 0;
		checkCount(c, difference);

		// checking also the number of ref vmes
		totalRefVme++;
		assertEquals(totalRefVme, dao.count(RefVme.class).intValue());

	}

	private void prepareDB() {

		Vme vme = VmeMock.generateVme(3);
		// if (!vmeDao.getEm().contains(vme.getRfmo())) {
		vmeDao.merge(vme.getRfmo());
		// }

		vme.getRfmo().setGeneralMeasureList(null);
		vme.getRfmo().setHasFisheryAreasHistory(null);
		vme.getRfmo().setHasVmesHistory(null);
		vme.getRfmo().setInformationSourceList(null);

		vmeDao.saveVme(vme);

	}

	private void cleanVme() {
		List<Vme> list = vmeDao.loadObjects(Vme.class);
		for (Vme object : list) {
			vmeDao.delete(object);
		}
		List<Rfmo> rfmoList = vmeDao.loadObjects(Rfmo.class);
		for (Rfmo rfmo : rfmoList) {
			vmeDao.remove(rfmo);
		}
		List<InformationSource> informationSourceList = vmeDao.loadObjects(InformationSource.class);
		for (InformationSource is : informationSourceList) {
			vmeDao.remove(is);
		}
		List<InformationSourceType> informationSourceTypeList = vmeDao.loadObjects(InformationSourceType.class);
		for (InformationSourceType is : informationSourceTypeList) {
			vmeDao.remove(is);
		}
		List<VmeScope> l = vmeDao.loadObjects(VmeScope.class);
		for (VmeScope o : l) {
			vmeDao.remove(o);
		}
	}

	private void cleanRefFigis() {

		List<RefVme> l = figisDao.loadObjects(RefVme.class);
		for (RefVme r : l) {
			figisDao.remove(r);
		}

	}

}
