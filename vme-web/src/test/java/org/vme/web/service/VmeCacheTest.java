package org.vme.web.service;

import static org.junit.Assert.assertEquals;

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
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDB;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.web.cache.CacheProducer;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, VmePersistenceUnitConfiguration.class,
		FigisPersistenceUnitConfiguration.class })
@AdditionalClasses({ CacheProducer.class, FigisDataBaseProducer.class, VmeDataBaseProducerApplicationScope.class })
public class VmeCacheTest {

	@Inject
	@VmeDB
	private EntityManager em1;

	@Inject
	@VmeDB
	private EntityManager em2;

	@Inject
	VmeCacheWs ws;

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testClean() throws Exception {

		Vme vme = VmeMock.create();

		EntityTransaction et = em1.getTransaction();
		et.begin();
		em1.persist(vme);
		et.commit();

		String text_query = "from Vme vme where vme.id = " + vme.getId();

		ws.clean();
		assertEquals(1, em1.createQuery(text_query).getResultList().size());
		assertEquals(1, em2.createQuery(text_query).getResultList().size());
	}

}
