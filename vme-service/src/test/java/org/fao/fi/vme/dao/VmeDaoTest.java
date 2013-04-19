package org.fao.fi.vme.dao;

import javax.inject.Inject;

import org.fao.fi.vme.domain.VmeObservation;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(CdiRunner.class)
public class VmeDaoTest {

	@Inject
	VmeDao dao;

	@Test
	public void testPersistVme() {
		assertNotNull(dao);

		VmeObservation vme = new VmeObservation();
		String status = "go";
		vme.setStatus(status);
		// dao.persistVme(vme);

	}

}
