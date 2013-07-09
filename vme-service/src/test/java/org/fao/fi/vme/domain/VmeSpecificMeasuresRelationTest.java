package org.fao.fi.vme.domain;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

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
		assertEquals(1, vmeDao.count(SpecificMeasures.class).intValue());

		// adding 1 SpecificMeasures
		SpecificMeasures s = generateSpecificMeasures(vme);
		s.setId(30l);

		vmeDao.merge(vme);
		List<SpecificMeasures> originalList = vme.getSpecificMeasureList();
		List<SpecificMeasures> foundList = vmeDao.findVme(vme.getId()).getSpecificMeasureList();
		for (int i = 0; i < originalList.size(); i++) {
			assertEquals(originalList.get(i), foundList.get(i));
		}

		assertEquals(2, vmeDao.count(SpecificMeasures.class).intValue());
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

	private SpecificMeasures generateSpecificMeasures(Vme vme) {
		SpecificMeasures s = new SpecificMeasures();
		s.setId(20l);
		List<Vme> slv = new ArrayList<Vme>();
		slv.add(vme);
		s.setVmeList(slv);
		if (vme.getSpecificMeasureList() == null) {
			List<SpecificMeasures> sl = new ArrayList<SpecificMeasures>();
			vme.setSpecificMeasureList(sl);
		}

		vme.getSpecificMeasureList().add(s);
		return s;
	}

}
