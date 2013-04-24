package org.fao.fi.vme.dao.msaccess;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class VmeAccessDbImportTest {

	@Test
	public void testGenerateObjects() {
		VmeAccessDbImport i = new VmeAccessDbImport();
		List<Table> tables = i.generateObjects();
		for (Table table : tables) {
			assertTrue(table.getRecords().size() > 10);
		}

	}

	// @Test
	// public void testGetResultset() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testGetConnection() {
	// fail("Not yet implemented");
	// }

}
