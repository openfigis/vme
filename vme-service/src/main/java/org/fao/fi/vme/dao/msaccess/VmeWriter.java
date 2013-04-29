package org.fao.fi.vme.dao.msaccess;

import java.util.List;

import javax.inject.Inject;

public class VmeWriter {

	@Inject
	TableWriter tableWriter;

	public void write(List<ObjectCollection> objectCollectionList) {
		for (ObjectCollection objectCollection : objectCollectionList) {
			System.out.println(objectCollection.getClazz().getSimpleName());
			tableWriter.write(objectCollection);
		}
	}

	public void merge(List<ObjectCollection> objectCollectionList) {
		for (ObjectCollection objectCollection : objectCollectionList) {
			System.out.println("========================");
			System.out.println(objectCollection.getClazz().getSimpleName());
			tableWriter.merge(objectCollection);
		}
	}

}
