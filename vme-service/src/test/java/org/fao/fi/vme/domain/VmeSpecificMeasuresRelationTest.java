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

		// adding 1 SpecificMeasures
		SpecificMeasure s = generateSpecificMeasures(vme);
		s.setId(30l);

		vmeDao.merge(vme);
		List<SpecificMeasure> originalList = vme.getSpecificMeasureList();
		List<SpecificMeasure> foundList = vmeDao.findVme(vme.getId()).getSpecificMeasureList();
		for (int i = 0; i < originalList.size(); i++) {
			assertEquals(originalList.get(i), foundList.get(i));
		}

		assertEquals(2, vmeDao.count(SpecificMeasure.class).intValue());
		Vme vmeFound = vmeDao.findVme(vme.getId());
		assertEquals(2, vmeFound.getSpecificMeasureList().size());

		assertEquals(vme.getSpecificMeasureList().get(0), vmeFound.getSpecificMeasureList().get(0));
		assertEquals(s, vmeFound.getSpecificMeasureList().get(1));

	}

	private Vme generateVme() {
		Vme vme = new Vme();
		vme.setId(10l);

		generateSpecificMeasures(vme);
		return vme;
	}

	private SpecificMeasure generateSpecificMeasures(Vme vme) {
		SpecificMeasure s = new SpecificMeasure();
		s.setId(20l);
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
