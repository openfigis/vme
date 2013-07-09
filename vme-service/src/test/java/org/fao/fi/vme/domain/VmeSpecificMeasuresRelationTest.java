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

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class VmeSpecificMeasuresRelationTest {

	@Inject
	private VmeDao vmeDao;

	@Test
	public void relationVmeSpecificMeasures() {
		Vme vme = new Vme();
		vme.setId(10l);

		SpecificMeasures s = new SpecificMeasures();
		s.setId(20l);

		List<Vme> vl = new ArrayList<Vme>();
		vl.add(vme);

		List<SpecificMeasures> sl = new ArrayList<SpecificMeasures>();
		sl.add(s);

		vme.setSpecificMeasureList(sl);
		s.setVmeList(vl);

		vmeDao.persist(vme);

	}

}
