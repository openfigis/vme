package org.fao.fi.vme.dao.msaccess;

import java.util.List;

import org.junit.Test;

public class VmeWriterTest {

	VmeReader reader = new VmeReader();

	VmeWriter writer = new VmeWriter();

	@Test
	public void testPersist() {
		List<Table> tables = reader.readObjects();

		writer.persist(tables);
	}

}
