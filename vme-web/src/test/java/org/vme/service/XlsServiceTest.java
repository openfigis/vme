package org.vme.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.VmeObservationPk;
import org.fao.fi.figis.domain.rule.VmeObservationDomainFactory;
import org.fao.fi.figis.domain.test.ObservationMock;
import org.fao.fi.figis.domain.test.ObservationXmlMock;
import org.fao.fi.figis.domain.test.RefVmeMock;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.junit.Test;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.service.tabular.TabularGenerator;
import org.vme.service.tabular.record.VmeContainer;

//@RunWith(CdiRunner.class)
//@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducer.class })
public class XlsServiceTest {

	@Inject
	private XlsService xlsService = new XlsService();

	@Inject
	private VmeDao vDao;

	@Inject
	private FigisDao fDao;

	private WritableWorkbookFactory f = new WritableWorkbookFactory();
	private TabularGenerator g = new TabularGenerator();

	private static final Long observation_ID = (long) 10001;

	@Test
	public void testCreateXlsFile() throws Exception {
		ByteArrayInputStream bos = xlsService.createXlsFile("SEAFO");
		assertNotNull(bos);
	}

	@Test
	public void testFillWorkSheet() throws RowsExceededException, WriteException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Vme vme = VmeMock.generateVme(2);

		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(vme);

		WritableWorkbook ww = f.create(baos);

		for (WritableSheet wSheet : ww.getSheets()) {
			if (wSheet.getName().equals("Fact Sheets")) {
				List<VmeContainer> vmeContainerList = prepereListMock(vmeList);
				for (VmeContainer vmeContainer : vmeContainerList) {
					vmeContainerList.remove(null);
				}
				List<List<Object>> tabular = g.generateFactSheet(vmeContainerList);
				xlsService.fillCells(tabular, wSheet);
			} else {
				xlsService.fillWorkSheet(wSheet, vmeList);
			}
		}

		assertEquals(8, ww.getNumberOfSheets());

		for (WritableSheet wSheet : ww.getSheets()) {
			assertTrue(wSheet.getRows() > 1);
		}

	}

	@Test
	public void testFillWorkSheetInCaseOfEmptyDB() throws RowsExceededException, WriteException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		List<Vme> vmeList = new ArrayList<Vme>();

		WritableWorkbook ww = f.create(baos);

		for (WritableSheet wSheet : ww.getSheets()) {

			if (wSheet.getName().equals("Fact Sheets")) {
				List<VmeContainer> vmeContainerList = prepereListMock(vmeList);
				List<List<Object>> tabular = g.generateFactSheet(vmeContainerList);
				xlsService.fillCells(tabular, wSheet);
			} else {
				xlsService.fillWorkSheet(wSheet, vmeList);
			}

		}

		assertEquals(7, ww.getNumberOfSheets());

		for (WritableSheet wSheet : ww.getSheets()) {
			assertTrue(wSheet.getRows() > 1);
		}

	}

	@Test
	public void testGetAuthorityIdByAcronym() {
//		fail("Not yet implemented");
	}

	@Test
	public void prepereListTest(){

		Vme vme = VmeMock.generateVme(2);

		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(vme);

		List<VmeContainer> cList = new ArrayList<VmeContainer>();

		int i = 0;

		for (Vme v : vmeList) {

			Observation ob = ObservationMock.create();
			ob.setId(observation_ID+i);

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
	
	public List<VmeContainer> prepereListMock(List<Vme> vmeList){

		List<VmeContainer> cList = new ArrayList<VmeContainer>();

		int i = 0;

		for (Vme v : vmeList) {

			Observation ob = ObservationMock.create();
			ob.setId(observation_ID+i);

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

		assertNotNull(cList);

		return cList;
	}

	@Test
	public void dataStringTest(){
		System.out.println(xlsService.dataString());
	}

}
