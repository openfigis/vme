//package org.vme.service;
//
//import static org.junit.Assert.assertTrue;
//
//import java.util.List;
//
//import javax.inject.Inject;
//
//import org.fao.fi.vme.domain.model.Vme;
//import org.fao.fi.vme.domain.test.VmeMock;
//import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
//import org.jglue.cdiunit.ActivatedAlternatives;
//import org.jglue.cdiunit.CdiRunner;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.vme.dao.config.figis.FigisDataBaseProducer;
//import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
//import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
//import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
//import org.vme.dao.impl.jpa.ReferenceDaoImpl;
//import org.vme.dao.sources.vme.VmeDao;
//
//@RunWith(CdiRunner.class)
//@ActivatedAlternatives({ ReferenceDaoImpl.class, FigisTestPersistenceUnitConfiguration.class, FigisDataBaseProducer.class,
//	VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class,})
//public class AbstractServiceTest {
//
//	@Inject
//	private VmeDao vDao;
//
//	private GetInfoService service;
//	
//	private static final MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
//
//	private Vme vme;
//	private Vme vme2;
//
//	@Before
//	public void before(){
//		vme = VmeMock.generateVme(3);
//		vme2 = VmeMock.generateVme(5);
//		vme.setScope(10L);
//		vme.setScope(20L);
//		vme.setName(UTIL.english("Foo1"));
//		vme2.setName(UTIL.english("Foo2"));
//		vDao.saveVme(vme);
//		vDao.saveVme(vme2);
//	}
//
//	@Test
//	public void testFilterVmePerScope() {
//		List<Vme> vmeList = vDao.loadVmes();
//		service.filterVmePerScope(vmeList, "10");
//		assertTrue(1 == vmeList.size());
//	}
//
//	@Test
//	public void testFilterVmePerRfmoById() {
//		List<Vme> vmeList = vDao.loadVmes();
//		service.filterVmePerRfmoById(vmeList, 1001);
//		assertTrue(2 == vmeList.size());
//	}
//
//	@Test
//	public void testGetAuthorityIdByAcronym() {
//		Long authorityId = service.getAuthorityIdByAcronym("RFMO");
//		assertTrue(1001 == authorityId);
//	}
//
////	TODO (implement this test logic)
//	
//	@Test
//	public void testVmeContainRelevantText() {
//		assertTrue(service.vmeContainRelevantText(vme, "Foo1"));
//		assertTrue(!service.vmeContainRelevantText(vme2, "Foo1"));
//	}
//
//}
