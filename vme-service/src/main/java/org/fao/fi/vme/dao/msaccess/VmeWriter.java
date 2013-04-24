package org.fao.fi.vme.dao.msaccess;

import java.util.List;

public class VmeWriter {

	TableWriter tableWriter = new TableWriter();

	public void persist(List<Table> tables) {
		for (Table table : tables) {
			tableWriter.write(table);
		}

	}
}
