package org.fao.fi.vme.dao.msaccess;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class VmeReaderTest {

	VmeReader reader = new VmeReader();

	@Test
	public void testReadObjects() {

		List<Table> tables = reader.readObjects();
		for (Table table : tables) {
			assertTrue(table.getRecords().size() > 10);
		}

	}

}
