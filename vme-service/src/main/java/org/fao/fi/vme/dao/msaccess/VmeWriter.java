package org.fao.fi.vme.dao.msaccess;

import java.util.List;

import javax.inject.Inject;

public class VmeWriter {

	@Inject
	TableWriter tableWriter;

	public void write(List<ObjectCollection> objectCollectionList) {
		for (ObjectCollection objectCollection : objectCollectionList) {
			tableWriter.write(objectCollection);
		}

	}
}
