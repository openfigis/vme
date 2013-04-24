package org.fao.fi.vme.dao.msaccess;

import java.util.List;

public class VmeAccessDbImport {

	private final VmeWriter writer = new VmeWriter();
	private final VmeReader reader = new VmeReader();

	public void importMsAccessData() {
		List<Table> tables = reader.readObjects();
		writer.persist(tables);
	}

}
