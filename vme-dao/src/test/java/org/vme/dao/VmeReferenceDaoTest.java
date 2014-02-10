package org.vme.dao;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.model.Authority;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.vme.VmeDB;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;

@RunWith(CdiRunner.class)
@ActivatedAlternatives(VmeDataBaseProducer.class)
public class VmeReferenceDaoTest {

	@Inject
	VmeReferenceDao dao;

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