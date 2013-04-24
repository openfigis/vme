package org.fao.fi.vme.dao.msaccess;

import org.junit.Test;

public class VmeAccessDbImportTest {

	VmeAccessDbImport i = new VmeAccessDbImport();

	@Test
	public void testGenerateObjects() {
		i.importMsAccessData();
	}

}
