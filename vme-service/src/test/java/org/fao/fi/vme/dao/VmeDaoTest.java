package org.fao.fi.vme.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.History;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.service.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class VmeDaoTest {

	@Inject
	VmeDao dao;

	@Test
	public void testMergeRfmo() {
		String id = "fiosdfsd";
		Rfmo rfmo = new Rfmo();
		rfmo.setId(id);
		dao.persist(rfmo);

		List<History> hasFisheryAreasHistory = new ArrayList<History>();
		History h = new History();
		h.setYear(2008);
		// h.setId(456l);

		dao.persist(h);

		hasFisheryAreasHistory.add(h);
		rfmo.setHasFisheryAreasHistory(hasFisheryAreasHistory);

		dao.merge(rfmo);

	}

	@Test
	public void testSave() {
		int nrOfyears = 1;
		Vme vme = VmeMock.generateVme(nrOfyears);
		// VmeDaoTestLogic l = new VmeDaoTestLogic();
		dao.saveVme(vme);
		// l.saveVme(vme, dao);
		Vme vmeFound = dao.findVme(vme.getId());
		assertTrue(vmeFound.getRfmo().getGeneralMeasureList().size() > 0);
		for (GeneralMeasure gm : vmeFound.getRfmo().getGeneralMeasureList()) {
			assertNotNull(gm.getFishingAreas());
		}

	}

	@Test
	public void testLoadVmes() {
		assertNotNull(dao);

		Vme vme = new Vme();
		String criteria = "go";
		vme.setCriteria(criteria);
		dao.loadVmes();
	}

	@Test
	public void testCount() {
		assertEquals(0, dao.count(Vme.class).intValue());

		dao.merge(VmeMock.create());
		assertEquals(1, dao.count(Vme.class).intValue());

	}

}
