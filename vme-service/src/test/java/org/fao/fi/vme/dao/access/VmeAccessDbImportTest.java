package org.fao.fi.vme.dao.access;

import org.fao.fi.vme.dao.msaccess.VmeAccessDbImport;
import org.junit.Test;

public class VmeAccessDbImportTest {

	VmeAccessDbImport i = new VmeAccessDbImport();

	@Test
	public void testImportAccessDB() {
		i.generateObjects();
	}

}
