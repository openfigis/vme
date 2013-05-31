package org.fao.fi.vme.figisxml.component;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.domain.Vme;

public class VmeRefSync implements Sync {

	@Inject
	FigisDao figisDao;

	@Inject
	VmeDao vmeDao;

	public void sync() {
		List<Vme> objects = vmeDao.loadVmes();
		for (Vme vme : objects) {
			RefVme object = figisDao.loadRefVme(vme.getId());
			if (object == null) {
				object = new RefVme();
				map(vme, object);
				figisDao.persist(object);
			} else {
				map(vme, object);
				figisDao.merge(object);
			}
		}
	}

	private void map(Vme vme, RefVme object) {
		object.setId(vme.getId());
	}
}
