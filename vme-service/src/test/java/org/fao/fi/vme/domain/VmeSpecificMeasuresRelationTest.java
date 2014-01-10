package org.fao.fi.vme.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.service.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class VmeSpecificMeasuresRelationTest {

	@Inject
	private VmeDao vmeDao;

	@Test
	public void relationVmeSpecificMeasures() {
		Vme vme = generateVme();
		vmeDao.persist(vme);
		assertEquals(1, vmeDao.count(Vme.class).intValue());
		assertEquals(1, vmeDao.count(SpecificMeasure.class).intValue());

		// adding the second SpecificMeasures
		SpecificMeasure s = generateSpecificMeasures(vme);
		vmeDao.merge(vme);
		assertEquals(2, vme.getSpecificMeasureList().size());
		assertEquals(1, vme.getSpecificMeasureList().get(0).getVmeList().size());
		assertEquals(1, vme.getSpecificMeasureList().get(1).getVmeList().size());
		assertEquals(1, s.getVmeList().size());

	}

	private Vme generateVme() {
		Vme vme = new Vme();
		generateSpecificMeasures(vme);
		return vme;
	}

	private SpecificMeasure generateSpecificMeasures(Vme vme) {
		SpecificMeasure s = new SpecificMeasure();
		List<Vme> slv = new ArrayList<Vme>();
		slv.add(vme);
		s.setVmeList(slv);
		if (vme.getSpecificMeasureList() == null) {
			List<SpecificMeasure> sl = new ArrayList<SpecificMeasure>();
			vme.setSpecificMeasureList(sl);
		}
		vme.getSpecificMeasureList().add(s);
		return s;
	}

}
