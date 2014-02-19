package org.vme.dao;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.model.Authority;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDB;
import org.vme.dao.config.vme.VmeDataBaseConfigurationTest;
import org.vme.dao.config.vme.VmeDataBaseProducer;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseConfigurationTest.class, VmeDataBaseProducer.class })
public class ReferenceBatchDaoTest {

	@Inject
	ReferenceBatchDao dao;

	@Inject
	@VmeDB
	private EntityManager em;

	@Test
	public void testSyncStoreObject() {
		Authority a = new Authority(20220, "NAFO", "Northwest Atlantic Fisheries Organization");
		assertEquals(0, dao.loadObjects(em, Authority.class).size());
		dao.syncStoreObject(a, a.getId());
		assertEquals(1, dao.loadObjects(em, Authority.class).size());
		a.setAcronym("5483905843905");
		dao.syncStoreObject(a, a.getId());
		assertEquals(1, dao.loadObjects(em, Authority.class).size());

	}
}
