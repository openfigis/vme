package org.fao.fi.vme.dao.msaccess;

import java.util.List;

public class VmeWriter {

	TableWriter tableWriter = new TableWriter();

	public void write(List<ObjectCollection> objectCollectionList) {
		for (ObjectCollection objectCollection : objectCollectionList) {
			tableWriter.write(objectCollection);
		}

	}
}
