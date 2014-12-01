package org.vme.dao.sources.vme;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class TooManySpecificMeasuresTest {

	@Inject
	VmeDao dao;

	@Test
	public void testTooManySpecificMeasures() {
		Vme vme = new Vme();

		GeneralMeasure g1 = new GeneralMeasure();
		GeneralMeasure g2 = new GeneralMeasure();
		dao.persist(g1);
		dao.persist(g2);

		Rfmo rfmo = new Rfmo();
		rfmo.setId("fjiedowfjewoirewiorfew");
		List<GeneralMeasure> l = new ArrayList<GeneralMeasure>();
		l.add(g1);
		l.add(g2);
		dao.persist(rfmo);

	}
}
