package org.fao.fi.vme.domain.change2;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Vme;
import org.vme.dao.sources.vme.VmeDao;

/**
 * 
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeTypeChange {

	@Inject
	private VmeDao vmeDao;

	public void migrate() {

		List<Vme> list = vmeDao.loadObjects(Vme.class);
		for (Vme v : list) {
			System.out.println(v.getAreaType());
		}

		System.out.println("Doing nothing, let Hibernate work. ");

	}
}
