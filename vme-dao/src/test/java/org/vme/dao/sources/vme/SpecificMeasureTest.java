package org.vme.dao.sources.vme;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.SpecificMeasure;
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
public class SpecificMeasureTest {

	@Inject
	VmeDao dao;

	@Test
	public void test() {

		SpecificMeasure specificMeasureManaged = new SpecificMeasure();

		EntityTransaction t = dao.getEm().getTransaction();
		t.begin();
		dao.getEm().persist(specificMeasureManaged);
		t.commit();
		assertNotNull(specificMeasureManaged.getId());

	}

}
