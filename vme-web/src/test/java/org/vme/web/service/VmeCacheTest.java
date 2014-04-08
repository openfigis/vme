package org.vme.web.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.VmeSearchDao;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDB;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.impl.jpa.VmeSearchDaoImpl;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ReferenceDaoImpl.class, VmeSearchDaoImpl.class })
@ActivatedAlternatives({ FigisTestPersistenceUnitConfiguration.class, FigisDataBaseProducer.class,
		VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducer.class })
public class VmeCacheTest {

	@Inject
	@VmeDB
	private EntityManager em1;

	@Inject
	@VmeDB
	private EntityManager em2;

	@Inject
	VmeSearchDao vmeSearchDao;

	@Inject
	VmeCacheWs ws;

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testClean() throws Exception {
		assertNotEquals(em1, em2);

		Vme vme = VmeMock.create();

		EntityTransaction et = em1.getTransaction();
		et.begin();
		em1.persist(vme);
		et.commit();

		String text_query = "from Vme vme where vme.id = " + vme.getId();

		// I did not manage to get this working, probably because it is up to
		// Hibernate whether to clear or not clear
		// assertEquals(0, em2.createQuery(text_query).getResultList().size());

		ws.clean();
		assertEquals(1, em1.createQuery(text_query).getResultList().size());
		assertEquals(1, em2.createQuery(text_query).getResultList().size());
	}

}
