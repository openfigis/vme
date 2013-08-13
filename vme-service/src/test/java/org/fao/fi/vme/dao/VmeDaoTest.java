package org.fao.fi.vme.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.test.VmeDaoTestLogic;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class VmeDaoTest {

	@Inject
	VmeDao dao;

	@Test
	public void testSave() {
		int nrOfyears = 1;
		Vme vme = VmeMock.generateVme(nrOfyears);
		VmeDaoTestLogic l = new VmeDaoTestLogic();
		l.saveVme(vme, dao);
		Vme vmeFound = dao.findVme(vme.getId());
		assertTrue(vmeFound.getRfmo().getGeneralMeasuresList().size() > 0);
		for (GeneralMeasures gm : vmeFound.getRfmo().getGeneralMeasuresList()) {
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
