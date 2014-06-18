package org.vme.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.InformationSourceType;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.impl.jpa.VmeSearchDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ReferenceDaoImpl.class, VmeSearchDaoImpl.class })
@ActivatedAlternatives({ FigisTestPersistenceUnitConfiguration.class, FigisDataBaseProducer.class,
		VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducer.class })
public class XlsServiceTest {

	@Inject
	private XlsService xlsService = new XlsService();

	@Inject
	VmeDao vDao;

	@Inject
	@ConceptProvider
	private ReferenceDaoImpl refDao;

	/**
	 * Note: @throws NullPointerException
	 * 
	 */

	@Test
	public void testCreateXlsFile() throws Exception {
		InformationSourceType defaultIST = InformationSourceMock.createInformationSourceType();

		vDao.persist(defaultIST);

		Vme vme = VmeMock.generateVme(5);
		vDao.saveVme(vme);

		ByteArrayInputStream byteArrayInputStream = xlsService.createXlsFile("1000");

		assertNotNull(byteArrayInputStream);
	}

	@Test
	@Ignore("Profile needed")
	public void testGetAuthorityIdByAcronym() {
		assertTrue(24561 == Long.valueOf(xlsService.getAuthorityIdByAcronym("GFCM")));
	}

	@Test
	public void dataStringTest() {
		System.out.println(xlsService.dataString());
	}

}
