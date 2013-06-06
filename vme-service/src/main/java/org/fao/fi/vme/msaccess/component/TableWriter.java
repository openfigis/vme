package org.fao.fi.vme.msaccess.component;

import javax.inject.Inject;

import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.msaccess.model.ObjectCollection;

public class TableWriter {

	@Inject
	VmeDao vmeDao;

	public void write(ObjectCollection objectCollection) {
		for (Object object : objectCollection.getObjectList()) {
			vmeDao.persist(object);
		}
	}

	public void merge(ObjectCollection objectCollection) {
		for (Object object : objectCollection.getObjectList()) {
			vmeDao.merge(object);
		}
	}

}
