package org.vme.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, FigisPersistenceUnitConfiguration.class,
		VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class, FigisDataBaseProducer.class })
public class AbstractServiceTest {

	@Inject
	private VmeDao vDao;

	@Inject
	private GetInfoService service;

	private static final MultiLingualStringUtil UTIL = new MultiLingualStringUtil();

	private Vme vme;
	private Vme vme2;

	@Before
	public void before() {
		vme = VmeMock.generateVme(3);
		vme.setScope(10L);
		vme.setName(UTIL.english("Foo1"));
		vDao.saveVme(vme);

	}

	@Ignore
	@Test
	public void testFilterVmePerScope() {
		List<Vme> vmeList = vDao.loadVmes();
		service.filterVmePerScope(vmeList, "10");
		assertTrue(1 == vmeList.size());
	}

	@Test
	public void testFilterVmePerRfmoById() {
		List<Vme> vmeList = vDao.loadVmes();
		service.filterVmePerRfmoById(vmeList, 1001);
		assertEquals(0, vmeList.size());

		Vme vme = new Vme();
		vmeList.add(vme);

		service.filterVmePerRfmoById(vmeList, 1001);

	}

	@Ignore
	@Test
	public void testGetAuthorityIdByAcronym() {
		Long authorityId = service.getAuthorityIdByAcronym("RFMO");
		assertTrue(1001 == authorityId);
	}

	// TODO (implement this test logic)

	@Ignore
	@Test
	public void testVmeContainRelevantText() {
		assertTrue(service.vmeContainRelevantText(vme, "Foo1"));
		assertTrue(!service.vmeContainRelevantText(vme2, "Foo1"));
	}

}
