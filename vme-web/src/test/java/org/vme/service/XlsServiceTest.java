package org.vme.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.junit.Test;

public class XlsServiceTest {

	@Test
	public void test() throws IOException, RowsExceededException, WriteException {
		

		    String fileName = "C:/RobertoWorkspace/fileExcel.xls";
		    WorkbookSettings ws = new WorkbookSettings();
			ws.setEncoding("utf8");
		    WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName), ws);
		    WritableSheet writablesheet1 = workbook.createSheet("Sheet1", 0);
		    WritableSheet writablesheet2 = workbook.createSheet("Sheet2", 1);
		    WritableSheet writablesheet3 = workbook.createSheet("Sheet3", 2);
		    Label label1 = new Label(0,0,"Emp_Name");
		    Label label2 = new Label(0,1,"Emp_FName");
		    Label label3 = new Label(0,2,"Emp_Salary");
		    writablesheet1.addCell(label1);
		    writablesheet2.addCell(label2);
		    writablesheet3.addCell(label3);
		    workbook.write();
		    workbook.close();

	}

}
