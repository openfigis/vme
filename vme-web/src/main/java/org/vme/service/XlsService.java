package org.vme.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.inject.Inject;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.fao.fi.vme.domain.model.Vme;
import org.vme.dao.sources.vme.VmeDao;

public class XlsService extends AbstractService {

	@Inject
	private VmeDao vdao;

	@Inject
	private WritableWorkbookFactory f;

	public ByteArrayInputStream createXlsFile(String authorityAcronym) throws Exception {

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

		ww.write();
		ww.close();

		return new ByteArrayInputStream(baos.toByteArray());
	}
	
}
