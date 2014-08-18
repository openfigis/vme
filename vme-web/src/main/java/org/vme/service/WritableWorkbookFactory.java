package org.vme.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Blank;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.hibernate.collection.internal.PersistentBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.service.tabular.RecordGenerator;
import org.vme.service.tabular.TabularGenerator;
import org.vme.service.tabular.record.FactSheetRecord;
import org.vme.service.tabular.record.FisheryAreasHistoryRecord;
import org.vme.service.tabular.record.GeneralMeasureRecord;
import org.vme.service.tabular.record.GeoReferenceRecord;
import org.vme.service.tabular.record.InformationSourceRecord;
import org.vme.service.tabular.record.SpecificMeasureRecord;
import org.vme.service.tabular.record.VmeContainer;
import org.vme.service.tabular.record.VmeProfileRecord;
import org.vme.service.tabular.record.VmesHistoryRecord;

public class WritableWorkbookFactory {

	@Inject
	private FigisDao fDao;
	@Inject
	private VmeProfileRecord vmeProfileRecord;
	@Inject
	private SpecificMeasureRecord specificMeasureRecord;
	@Inject
	private GeneralMeasureRecord generalMeasureRecord;
	@Inject
	private FisheryAreasHistoryRecord fisheryAreasHistoryRecord;
	@Inject
	private VmesHistoryRecord vmesHistoryRecord;
	@Inject
	private InformationSourceRecord informationSourceRecord;
	@Inject
	private GeoReferenceRecord geoReferenceRecord;
	@Inject
	private FactSheetRecord factSheetRecord;

	public static final String VME_PROFILE_REC = "Description";
	public static final String SPEC_MEASURE_REC = "Measures specific to this area";
	public static final String GEN_MEASURE_REC = "General Measure";
	public static final String FISH_AREA_HIS_REC = "Overview of fishing areas";
	public static final String VME_HIS_REC = "Overview of VMEs";
	public static final String INFO_SOURCE_REC = "Reports";
	public static final String GEO_REF_REC = "Geo Reference";
	public static final String FACT_SHEET_REC = "Fact Sheets";
	
	public static final String ERROR_DURING_CREATION = "Error during creation of Excell sheet";
	public static final String ERROR_TYPE_NOT_FOUND = "Type not found:";

	Map<String, RecordGenerator<?, ?, ?>> recordMap = new HashMap<String, RecordGenerator<?, ?, ?>>();
	Map<String, List<?>> listMap = new HashMap<String, List<?>>();

	private static final Logger LOG = LoggerFactory.getLogger(WritableWorkbookFactory.class);

	private TabularGenerator g = new TabularGenerator();

	public WritableWorkbook create(OutputStream os, List<Vme> vmeList) {
		WorkbookSettings wSettings = new WorkbookSettings();
		wSettings.setEncoding("utf8");
		wSettings.setExcel9File(true);

		WritableWorkbook ww;
		try {
			ww = Workbook.createWorkbook(os, wSettings);
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
		generateMaps(vmeList);
		return ww;
	}

	private void generateMaps(List<Vme> vmeList) {

		/*
		 * Note: Block of instruction related to map who contains classes which
		 * implements RecordGenerator
		 */

		recordMap.put(VME_PROFILE_REC, vmeProfileRecord);
		recordMap.put(SPEC_MEASURE_REC, specificMeasureRecord);
		recordMap.put(GEN_MEASURE_REC, generalMeasureRecord);
		recordMap.put(FISH_AREA_HIS_REC, fisheryAreasHistoryRecord);
		recordMap.put(VME_HIS_REC, vmesHistoryRecord);
		recordMap.put(INFO_SOURCE_REC, informationSourceRecord);
		recordMap.put(GEO_REF_REC, geoReferenceRecord);
		recordMap.put(FACT_SHEET_REC, factSheetRecord);

		/*
		 * Note: Block of instruction related to map who contains lists of first
		 * parameter for TabularGenerator
		 */

		List<Rfmo> rfmoList = new ArrayList<Rfmo>();
		if (!vmeList.isEmpty()) {
			rfmoList.add(vmeList.get(0).getRfmo());
		}

		listMap.put(VME_PROFILE_REC, vmeList);
		listMap.put(SPEC_MEASURE_REC, vmeList);
		listMap.put(GEN_MEASURE_REC, rfmoList);
		listMap.put(FISH_AREA_HIS_REC, rfmoList);
		listMap.put(VME_HIS_REC, rfmoList);
		listMap.put(INFO_SOURCE_REC, rfmoList);
		listMap.put(GEO_REF_REC, vmeList);
		listMap.put(FACT_SHEET_REC, prepereList(vmeList));

	}

	@SuppressWarnings("unchecked")
	public <F, S, T> void fillWorkSheet(WritableSheet wSheet) {
		List<List<Object>> tabular = g.generate((List<F>) listMap.get(wSheet.getName()),
				(RecordGenerator<F, S, T>) recordMap.get(wSheet.getName()));
		fillCells(tabular, wSheet);
	}

	public void fillCells(List<List<Object>> tabular, WritableSheet wSheet) {
		// precondition
		if (tabular.isEmpty()) {
			throw new VmeException("no first row found");
		}

		WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		for (int r = 0; r < tabular.size(); r++) {
			List<Object> record = tabular.get(r);
			for (int c = 0; c < record.size(); c++) {
				Object cell = record.get(c);
				try {
					if (cell == null) {
						wSheet.addCell(new Blank(c, r));
					} else {
						boolean problem = true;

						if (cell.getClass().equals(String.class)) {
							problem = false;
							String stringContent = (String) cell;
							if (r == 0) {
								wSheet.addCell(new Label(c, r, stringContent, cellFormat));
							} else {
								wSheet.addCell(new Label(c, r, stringContent));
							}
						}

						if (cell instanceof Integer) {
							problem = false;
							Integer integerContent = (Integer) cell;
							wSheet.addCell(new Number(c, r, integerContent, new WritableCellFormat(
									NumberFormats.INTEGER)));
						}

						if (cell instanceof Long) {
							problem = false;
							Long integerContent = (Long) cell;
							wSheet.addCell(new Number(c, r, integerContent, new WritableCellFormat(
									NumberFormats.INTEGER)));
						}
						if (cell instanceof Date) {
							problem = false;
							Date dateContent = (Date) cell;
							wSheet.addCell(new jxl.write.DateTime(c, r, dateContent));
						}

						if (cell instanceof PersistentBag) {
							throw new VmeException(ERROR_TYPE_NOT_FOUND + cell.getClass());
						}

						if (problem) {
							throw new VmeException(ERROR_TYPE_NOT_FOUND + cell.getClass());
						}
					}
				} catch (RowsExceededException e) {
					LOG.error(ERROR_DURING_CREATION, e);
					throw new VmeException(e);
				} catch (WriteException e) {
					LOG.error(ERROR_DURING_CREATION, e);
				}
			}
		}
	}

	public List<VmeContainer> prepereList(List<Vme> vmeList) {
		List<VmeContainer> cList = new ArrayList<VmeContainer>();
		if (!vmeList.isEmpty()) {
			for (Vme vme : vmeList) {
				List<VmeObservation> observations = fDao.findVmeObservationByVme(vme.getId());
				VmeContainer c = new VmeContainer(vme.getName(), observations);
				cList.add(c);
			}
		}
		return cList;
	}
}
