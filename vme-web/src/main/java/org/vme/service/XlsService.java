package org.vme.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.fao.fi.vme.domain.model.Vme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.sources.vme.VmeDao;

public class XlsService extends AbstractService {

	@Inject
	private VmeDao vdao;

	@Inject
	private WritableWorkbookFactory f;
	
	private static final Logger log = LoggerFactory.getLogger(XlsService.class);

	public ByteArrayInputStream createXlsFile(String authorityAcronym) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		/*
		 * Note: this for block removes vmes from other RFMO by recognising them
		 * from RFMO`s id
		 */
		
		List<Vme> vmeListPerRfmo = filterVmePerRfmo(vdao.loadVmes(), authorityAcronym);

		WritableWorkbook ww = f.create(baos, vmeListPerRfmo);

		/*
		 * Note: this for block adds information and data to all worksheets of
		 * the returning file
		 */

		for (WritableSheet wSheet : ww.getSheets()) {
			f.fillWorkSheet(wSheet);
		}

		try {
			ww.write();
		} catch (IOException e1) {
			log.error(e1.getMessage(), e1);
		}
		try {
			ww.close();
		} catch (WriteException | IOException e) {
			log.error(e.getMessage(), e);
		}

		return new ByteArrayInputStream(baos.toByteArray());
	}
	
}
