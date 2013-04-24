package org.fao.fi.vme.dao.msaccess;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class VmeAccessDbImportTest {

	@Test
	public void testGenerateObjects() {
		VmeAccessDbImport i = new VmeAccessDbImport();
		assertTrue(i.generateObjects().size() > 10);
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
