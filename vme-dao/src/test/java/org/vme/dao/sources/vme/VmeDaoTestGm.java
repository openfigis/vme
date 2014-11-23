package org.vme.dao.sources.vme;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.GeneralMeasure;
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
public class VmeDaoTestGm {

	@Inject
	private VmeDao dao;

	@Test
	public void testDeleteGm() {
		GeneralMeasure gm = dao.getEntityById(dao.getEm(), GeneralMeasure.class, 24398l);
		assertNotNull(gm);
		EntityManager em = dao.getEm();
		EntityTransaction tx = em.getTransaction();

		tx.begin();
		dao.delete(gm);
		tx.commit();
		assertNull(dao.getEntityById(dao.getEm(), GeneralMeasure.class, 24398l));

	}
}
