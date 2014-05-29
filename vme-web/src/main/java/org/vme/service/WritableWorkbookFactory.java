package org.vme.service;

import java.io.IOException;
import java.io.OutputStream;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableWorkbook;

import org.fao.fi.vme.VmeException;

public class WritableWorkbookFactory {

	public WritableWorkbook create(OutputStream os) {
		WorkbookSettings wSettings = new WorkbookSettings();
		wSettings.setEncoding("utf8");
		wSettings.setExcel9File(true);

		WritableWorkbook ww;
		try {
			ww = Workbook.createWorkbook(os, wSettings);
		} catch (IOException e) {
			throw new VmeException(e);
		}
		ww.createSheet("Description", 0);
		ww.createSheet("Measures specific to this area", 1);
		ww.createSheet("General Measure", 2);
		ww.createSheet("Overview of fishing areas", 3);
		ww.createSheet("Overview of VMEs", 4);
		ww.createSheet("Meeting reports", 5);
		ww.createSheet("Geo Reference", 6);
		ww.createSheet("Fact Sheets", 7);
		return ww;
	}

}
