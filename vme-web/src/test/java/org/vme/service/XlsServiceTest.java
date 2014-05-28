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

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.junit.Test;
import org.vme.dao.sources.vme.VmeDao;

//@RunWith(CdiRunner.class)
//@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducer.class })
public class XlsServiceTest {

	@Inject
	private XlsService xlsService = new XlsService();

	@Inject
	private VmeDao vDao;

	private WritableWorkbookFactory f = new WritableWorkbookFactory();

	// @Test
	public void testCreateXlsFile() throws Exception {
		ByteArrayInputStream bos = xlsService.createXlsFile("SEAFO");
		assertNotNull(bos);
	}

	@Test
	public void testFillWorkSheet() throws RowsExceededException, WriteException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Vme vme = VmeMock.generateVme(2);
		vme.getValidityPeriod().setEndYear(null);
		vme.setCriteria(null);

		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(vme);

		WritableWorkbook ww = f.create(baos);

		for (WritableSheet wSheet : ww.getSheets()) {
			xlsService.fillWorkSheet(wSheet, vmeList);
		}

		assertEquals(7, ww.getNumberOfSheets());

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
			xlsService.fillWorkSheet(wSheet, vmeList);
		}

		assertEquals(7, ww.getNumberOfSheets());

		for (WritableSheet wSheet : ww.getSheets()) {
			assertTrue(wSheet.getRows() > 1);
		}

	}

	// @Test
	public void testGetAuthorityIdByAcronym() {
		// fail("Not yet implemented");
	}

}
