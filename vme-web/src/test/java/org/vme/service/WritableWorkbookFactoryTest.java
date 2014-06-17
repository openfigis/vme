package org.vme.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.WorkbookSettings;
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
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.junit.Ignore;
import org.junit.Test;
import org.vme.service.tabular.RecordGenerator;
import org.vme.service.tabular.record.FactSheetRecord;
import org.vme.service.tabular.record.FisheryAreasHistoryRecord;
import org.vme.service.tabular.record.GeneralMeasureRecord;
import org.vme.service.tabular.record.GeoReferenceRecord;
import org.vme.service.tabular.record.InformationSourceRecord;
import org.vme.service.tabular.record.SpecificMeasureRecord;
import org.vme.service.tabular.record.VmeContainer;
import org.vme.service.tabular.record.VmeProfileRecord;
import org.vme.service.tabular.record.VmesHistoryRecord;

public class WritableWorkbookFactoryTest {

	public static final String VME_PROFILE_REC = "Description";
	public static final String SPEC_MEASURE_REC = "Measures specific to this area";
	public static final String GEN_MEASURE_REC = "General Measure";
	public static final String FISH_AREA_HIS_REC = "Overview of fishing areas";
	public static final String VME_HIS_REC = "Overview of VMEs";
	public static final String INFO_SOURCE_REC = "Reports";
	public static final String GEO_REF_REC = "Geo Reference";
	public static final String FACT_SHEET_REC = "Fact Sheets";
	
	Map<String, RecordGenerator<?,?,?>> recordMap = new HashMap<String,RecordGenerator<?,?,?> >();  
	Map<String, List<?>> listMap = new HashMap<String,List<?>>();
	
	private WritableWorkbookFactory f = new WritableWorkbookFactory();

	private static final Long observation_ID = (long) 10001;
	
	@Test
	@Ignore("Profile needed")
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
			vPk.setReportingYear("1999");

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
	
	public WritableWorkbook createWWMock(ByteArrayOutputStream baos, List<Vme> vmeList) {
		WorkbookSettings wSettings = new WorkbookSettings();
		wSettings.setEncoding("utf8");
		wSettings.setExcel9File(true);

		WritableWorkbook ww;
		try {
			ww = Workbook.createWorkbook(baos, wSettings);
		} catch (IOException e) {
			throw new VmeException(e);
		}
		ww.createSheet(VME_PROFILE_REC, 0);
		ww.createSheet(SPEC_MEASURE_REC, 1);
		ww.createSheet(GEN_MEASURE_REC, 2);
		ww.createSheet(FISH_AREA_HIS_REC, 3);
		ww.createSheet(VME_HIS_REC, 4);
		ww.createSheet(INFO_SOURCE_REC, 5);
		ww.createSheet(GEO_REF_REC, 6);
		ww.createSheet(FACT_SHEET_REC, 7);
		/*
		 *  Note: Block of instruction related to map who contains
		 *  classes which implements RecordGenerator 
		 */

		recordMap.put(VME_PROFILE_REC, new VmeProfileRecord());
		recordMap.put(SPEC_MEASURE_REC, new SpecificMeasureRecord());
		recordMap.put(GEN_MEASURE_REC, new GeneralMeasureRecord());
		recordMap.put(FISH_AREA_HIS_REC, new FisheryAreasHistoryRecord());
		recordMap.put(VME_HIS_REC , new VmesHistoryRecord());
		recordMap.put(INFO_SOURCE_REC, new InformationSourceRecord());
		recordMap.put(GEO_REF_REC, new GeoReferenceRecord());
		recordMap.put(FACT_SHEET_REC, new FactSheetRecord());

		/*
		 *  Note: Block of instruction related to map who contains lists
		 *  of first parameter for TabularGenerator
		 */
		
		List<Rfmo> rfmoList = new ArrayList<Rfmo>();
		if (!vmeList.isEmpty()) {
			rfmoList.add(vmeList.get(0).getRfmo());
		}

		listMap.put(VME_PROFILE_REC, vmeList);
		listMap.put(SPEC_MEASURE_REC, vmeList);
		listMap.put(GEN_MEASURE_REC, rfmoList);
		listMap.put(FISH_AREA_HIS_REC, rfmoList);
		listMap.put(VME_HIS_REC , rfmoList);
		listMap.put(INFO_SOURCE_REC, rfmoList);
		listMap.put(GEO_REF_REC, vmeList);
		listMap.put(FACT_SHEET_REC, prepereListMock(vmeList));
		return ww;
	}
}
