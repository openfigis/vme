package org.fao.fi.vme.dao;

import javax.inject.Inject;

import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.dao.config.VmeEntityManagerFactoryProducer;
import org.fao.fi.vme.dao.config.VmeEntityManagerProducer;
import org.fao.fi.vme.domain.Vme;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeEntityManagerFactoryProducer.class, VmeEntityManagerProducer.class })
public class VmeDaoTest {

	@Inject
	VmeDao dao;

	@Test
	public void testPersistVme() {
		assertNotNull(dao);

		Vme vme = new Vme();
		String criteria = "go";
		vme.setCriteria(criteria);
		// dao.persistVme(vme);

	}

}
