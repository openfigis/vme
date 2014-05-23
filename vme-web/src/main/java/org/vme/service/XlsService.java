package org.vme.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

public class XlsService {

	@Inject
	private VmeDao vdao;

	@Inject
	@ConceptProvider
	private ReferenceDaoImpl refDao;

	MultiLingualStringUtil UTIL = new MultiLingualStringUtil();

	private WritableWorkbookFactory f = new WritableWorkbookFactory();

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

	public void fillWorkSheet(WritableSheet wSheet, List<Vme> vme) throws RowsExceededException, WriteException {

		WritableCellFormat integerFormat = new WritableCellFormat(NumberFormats.INTEGER);

		if (wSheet.getName().equals("VME_Profile")) {

			wSheet.addCell(new Label(0, 0, "Vme Name"));
			wSheet.addCell(new Label(1, 0, "Area Type"));
			wSheet.addCell(new Label(2, 0, "Geographic Reference"));
			wSheet.addCell(new Label(3, 0, "Criteria"));
			wSheet.addCell(new Label(4, 0, "Begin year"));
			wSheet.addCell(new Label(5, 0, "End year"));
			wSheet.addCell(new Label(6, 0, "Profile"));
			wSheet.addCell(new Label(7, 0, "Year"));
			wSheet.addCell(new Label(8, 0, "Geo Form"));
			wSheet.addCell(new Label(9, 0, "Phisical description"));
			wSheet.addCell(new Label(10, 0, "Biological description"));
			wSheet.addCell(new Label(11, 0, "Impact description"));

			int x = 1;
			for (Vme v : vme) {
				wSheet.addCell(new Label(0, x, UTIL.getEnglish(v.getName())));
				wSheet.addCell(new Label(1, x, v.getAreaType()));
				wSheet.addCell(new Label(2, x, UTIL.getEnglish(v.getGeoArea())));
				wSheet.addCell(new Label(3, x, v.getCriteria()));
				wSheet.addCell(new Number(4, x, v.getValidityPeriod().getBeginYear(), integerFormat));
				wSheet.addCell(new Number(5, x, v.getValidityPeriod().getEndYear(), integerFormat));
				if (!v.getProfileList().isEmpty()) {
					for (Profile p : v.getProfileList()) {
						wSheet.addCell(new Number(7, x, p.getYear(), integerFormat));
						wSheet.addCell(new Label(8, x, UTIL.getEnglish(p.getGeoform())));
						wSheet.addCell(new Label(9, x, UTIL.getEnglish(p.getDescriptionPhisical())));
						wSheet.addCell(new Label(10, x, UTIL.getEnglish(p.getDescriptionBiological())));
						wSheet.addCell(new Label(11, x, UTIL.getEnglish(p.getDescriptionImpact())));
						x++;
					}
				} else {
					x++;
				}
			}

		}

		if (wSheet.getName().equals("Specific_measure")) {

			wSheet.addCell(new Label(0, 0, "Vme Name"));
			wSheet.addCell(new Label(1, 0, "SpecificMeasure"));
			wSheet.addCell(new Label(2, 0, "Year"));
			wSheet.addCell(new Label(3, 0, "Begin year"));
			wSheet.addCell(new Label(4, 0, "End year"));
			wSheet.addCell(new Label(5, 0, "Information Source URL"));

			int x = 1;
			for (Vme v : vme) {
				wSheet.addCell(new Label(0, x, UTIL.getEnglish(v.getName())));
				if (!v.getSpecificMeasureList().isEmpty()) {
					for (SpecificMeasure sm : v.getSpecificMeasureList()) {
						wSheet.addCell(new Label(1, x, UTIL.getEnglish(sm.getVmeSpecificMeasure())));
						wSheet.addCell(new Number(2, x, sm.getYear(), integerFormat));
						wSheet.addCell(new Number(3, x, sm.getValidityPeriod().getBeginYear(), integerFormat));
						wSheet.addCell(new Number(4, x, sm.getValidityPeriod().getEndYear(), integerFormat));
						wSheet.addCell(new Label(5, x, sm.getInformationSource().getUrl().toString()));
						x++;
					}
				} else {
					x++;
				}
			}
		}

		if (wSheet.getName().equals("General_Measure")) {
			wSheet.addCell(new Label(0, 0, "Vme Name"));

			for (Vme v : vme) {

			}
		}

		if (wSheet.getName().equals("History")) {

		}

		if (wSheet.getName().equals("Info_Sources")) {

		}

		if (wSheet.getName().equals("Geo_Reference")) {

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

}
