package org.vme.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import jxl.write.Blank;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.Vme;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.service.tabular.TabularGenerator;
import org.vme.service.tabular.record.VmeContainer;

public class XlsService {

	@Inject
	private VmeDao vdao;

	@Inject
	private FigisDao fDao;

	@Inject
	@ConceptProvider
	private ReferenceDaoImpl refDao;

	private WritableWorkbookFactory f = new WritableWorkbookFactory();
	private TabularGenerator g = new TabularGenerator();
	private static HashMap<String, Class<?>> classMap;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public ByteArrayInputStream createXlsFile(String authorityAcronym) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		/*
		 * Note: this block create all the different worksheets needed by RFMO
		 */

		WritableWorkbook ww = f.create(baos);

		/*
		 * Note: this block handles wrong request from RFMO so they can access
		 * their file by querying by Id or the Acronym
		 */

		/*
		 * Note: this for block removes vmes from other RFMO by recognising them
		 * from RFMO`s id
		 */
		List<Vme> vmeList = vdao.loadVmes();
		List<Vme> vmeListPerRfmo = new ArrayList<Vme>();

		for (Vme v : vmeList) {
			if (v.getRfmo().getId().equals(authorityAcronym)) {
				vmeListPerRfmo.add(v);
			}
		}

		/*
		 * Note: this for block adds information and data to all worksheets of
		 * the returning file
		 */
		for (WritableSheet wSheet : ww.getSheets()) {
			fillWorkSheet(wSheet, vmeListPerRfmo);
		}

		ww.write();
		ww.close();

		return new ByteArrayInputStream(baos.toByteArray());
	}

	/**
	 * Note: this is the refactoring of fillWorkSheet method!
	 * 
	 */

	public void fillWorkSheet(WritableSheet wSheet, List<Vme> vmeList) throws RowsExceededException, WriteException {

		if (wSheet.getName().equals("Description")) {
			List<List<Object>> tabular = g.generateVmeProfile(vmeList);
			fillCells(tabular, wSheet);
		}

		if (wSheet.getName().equals("Measures specific to this area")) {
			List<List<Object>> tabular = g.generateSpecificMeasure(vmeList);
			fillCells(tabular, wSheet);
		}

		if (wSheet.getName().equals("General Measure")) {
			List<List<Object>> tabular = g.generateGeneralMeasure(vmeList.get(0).getRfmo());
			fillCells(tabular, wSheet);
		}

		if (wSheet.getName().equals("Overview of fishing areas")) {
			List<List<Object>> tabular = g.generateFisheryHistory(vmeList.get(0).getRfmo());
			fillCells(tabular, wSheet);
		}

		if (wSheet.getName().equals("Overview of VMEs")) {
			List<List<Object>> tabular = g.generateVMEHistory(vmeList.get(0).getRfmo());
			fillCells(tabular, wSheet);
		}

		if (wSheet.getName().equals("Meeting reports")) {
			List<List<Object>> tabular = g.generateInfoSource(vmeList.get(0).getRfmo());
			fillCells(tabular, wSheet);
		}

		if (wSheet.getName().equals("Geo Reference")) {
			List<List<Object>> tabular = g.generateGeoRef(vmeList);
			fillCells(tabular, wSheet);
		}

		if (wSheet.getName().equals("Fact Sheets")) {
			List<VmeContainer> vmeContainerList = prepereList(vmeList);
			List<List<Object>> tabular = g.generateFactSheet(vmeContainerList);
			fillCells(tabular, wSheet);
		}

	}

	private List<VmeContainer> prepereList(List<Vme> vmeList) {
		List<VmeContainer> cList = new ArrayList<VmeContainer>();
		for (Vme vme : vmeList) {
			List<VmeObservation> observations = fDao.findVmeObservationByVme(vme.getId());
			VmeContainer c = new VmeContainer(vme.getName(), observations);
			cList.add(c);
		}
		return cList;
	}

	private void fillCells(List<List<Object>> tabular, WritableSheet wSheet) {
		for (int r = 0; r < tabular.size(); r++) {
			List<Object> record = tabular.get(r);
			for (int c = 0; c < record.size(); c++) {
				Object cell = record.get(c);
				try {
					if (cell == null) {
						wSheet.addCell(new Blank(c, r));
					} else {
						if (cell instanceof String) {
							String stringContent = (String) cell;
							wSheet.addCell(new Label(c, r, stringContent));
						}
						if (cell instanceof Integer) {
							Integer integerContent = (Integer) cell;
							wSheet.addCell(new Number(c, r, integerContent, new WritableCellFormat(
									NumberFormats.INTEGER)));
						}
					}
				} catch (RowsExceededException e) {
					log.error("Error during creation of Excell sheet", e);
					throw new VmeException(e);
				} catch (WriteException e) {
					log.error("Error during creation of Excell sheet", e);
				}
			}
		}
	}

	public long getAuthorityIdByAcronym(String authorityAcronym) {

		List<Authority> authorities = refDao.getAllAuthorities();

		for (Authority authority : authorities) {
			if (authority.getAcronym().equals(authorityAcronym)) {
				return authority.getId();
			}
		}
		return 0;
	}

	public String dataString() {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		return dateFormat.format(cal.getTime());
	}

}
