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
		ww.createSheet("VME_Profile", 0);
		ww.createSheet("Specific_measure", 1);
		ww.createSheet("General_Measure", 2);
		ww.createSheet("Fishery_Areas_History", 3);
		ww.createSheet("VMEs_History", 4);
//		ww.createSheet("Info_Sources", 5);
		ww.createSheet("Geo_Reference", 6);
		return ww;
	}

}
