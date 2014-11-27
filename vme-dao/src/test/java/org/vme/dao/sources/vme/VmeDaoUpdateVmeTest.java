package org.vme.dao.sources.vme;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.SpecificMeasure;
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
public class VmeDaoUpdateVmeTest {

	@Inject
	private VmeDao dao;

	@Test
	public void testRead() throws Throwable {

		Vme vme = dao.getEm().find(Vme.class, 31851l);
		List<SpecificMeasure> specificMeasureList = vme.getSpecificMeasureList();

		for (SpecificMeasure specificMeasure : specificMeasureList) {
			System.out.println(specificMeasure.getId());
		}

	}
}
