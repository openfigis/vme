package org.vme.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationPk;
import org.fao.fi.figis.domain.test.ObservationMock;
import org.fao.fi.figis.domain.test.ObservationXmlMock;
import org.fao.fi.vme.domain.model.InformationSourceType;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.impl.jpa.VmeSearchDaoImpl;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.service.tabular.TabularGenerator;
import org.vme.service.tabular.record.VmeContainer;

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

	private WritableWorkbookFactory f = new WritableWorkbookFactory();
	private TabularGenerator g = new TabularGenerator();

	private static final Long observation_ID = (long) 10001;

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
	public void testFillWorkSheet() throws RowsExceededException, WriteException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Vme vme = VmeMock.generateVme(2);

		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(vme);

		WritableWorkbook ww = f.create(baos, vmeList);

		for (WritableSheet wSheet : ww.getSheets()) {
			f.fillWorkSheet(wSheet);
		}

		assertEquals(8, ww.getNumberOfSheets());

	}

	@Test
	public void testFillWorkSheetInCaseOfEmptyDB() throws RowsExceededException, WriteException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		List<Vme> vmeList = new ArrayList<Vme>();

		WritableWorkbook ww = f.create(baos, vmeList);

		for (WritableSheet wSheet : ww.getSheets()) {
			f.fillWorkSheet(wSheet);
		}

		assertEquals(8, ww.getNumberOfSheets());

		for (WritableSheet wSheet : ww.getSheets()) {
			System.out.println(wSheet.getName());
			assertEquals(1, wSheet.getRows());
		}

	}

	@Test
	public void testGetAuthorityIdByAcronym() {
		// fail("Not yet implemented");
	}

	@Test
	public void prepereListTest() {

		Vme vme = VmeMock.generateVme(2);

		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(vme);

		List<VmeContainer> cList = new ArrayList<VmeContainer>();

		int i = 0;

		for (Vme v : vmeList) {

			Observation ob = ObservationMock.create();
			ob.setId(observation_ID + i);

			List<ObservationXml> observationXmlList = new ArrayList<ObservationXml>();
			observationXmlList.add(ObservationXmlMock.create());
			ob.setObservationsPerLanguage(observationXmlList);

			VmeObservationPk vPk = new VmeObservationPk();
			vPk.setVmeId(v.getId());
			vPk.setObservationId(ob.getId());

			VmeObservation vObservation = new VmeObservation();
			vObservation.setId(vPk);

			List<VmeObservation> observations = new ArrayList<VmeObservation>();
			observations.add(vObservation);

			VmeContainer c = new VmeContainer(v.getName(), observations);

			cList.add(c);
			i++;
		}

		assertNotNull(cList);
	}

	public List<VmeContainer> prepereListMock(List<Vme> vmeList) {

		List<VmeContainer> cList = new ArrayList<VmeContainer>();

		int i = 0;

		for (Vme v : vmeList) {

			Observation ob = ObservationMock.create();
			ob.setId(observation_ID + i);

			ObservationXml obXml = ObservationXmlMock.create();
			obXml.setObservation(ob);

			List<ObservationXml> observationXmlList = new ArrayList<ObservationXml>();
			observationXmlList.add(obXml);
			ob.setObservationsPerLanguage(observationXmlList);

			VmeObservationPk vPk = new VmeObservationPk();
			vPk.setVmeId(v.getId());
			vPk.setObservationId(ob.getId());

			VmeObservation vObservation = new VmeObservation();
			vObservation.setId(vPk);

			List<VmeObservation> observations = new ArrayList<VmeObservation>();
			observations.add(vObservation);

			VmeContainer c = new VmeContainer(v.getName(), observations);

			cList.add(c);
			i++;
		}

		return cList;
	}

	@Test
	public void dataStringTest() {
		System.out.println(xlsService.dataString());
	}

}
