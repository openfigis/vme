package org.vme.web.service;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.test.VmeTypeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.VmeSearchDao;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.impl.jpa.VmeSearchDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ReferenceDaoImpl.class, VmeSearchDaoImpl.class })
@ActivatedAlternatives({ FigisTestPersistenceUnitConfiguration.class, FigisDataBaseProducer.class,
		VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class })
public class VmeSearchWsTest {

	@Inject
	VmeSearchWs vmeSearchWs;

	@Inject
	VmeDao vmeDao;

	@Inject
	VmeSearchDao vmeSearchDao;

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testFind() throws Exception {
		String text1 = "fdsfdsf";
		String text2 = "vdfewsiuj";
		Vme vme = VmeMock.create();
		vme.setName(u.english(text1));
		vmeDao.persist(VmeTypeMock.create());
		vmeDao.persist(vme);
		assertEquals(1, vmeSearchDao.searchVme(0, 0, 0, 0, text1).size());
		vme.setName(u.english(text2));
		vmeDao.merge(vme);
		assertEquals(0, vmeSearchDao.searchVme(0, 0, 0, 0, text1).size());
		assertEquals(1, vmeSearchDao.searchVme(0, 0, 0, 0, text2).size());

	}
}
