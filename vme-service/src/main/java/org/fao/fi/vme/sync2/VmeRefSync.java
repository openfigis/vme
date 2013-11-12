package org.fao.fi.vme.sync2;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.util.Lang;
import org.vme.service.dao.sources.figis.FigisDao;
import org.vme.service.dao.sources.vme.VmeDao;

/**
 * 
 * Map the REF_VME and the FS_OBSERVATION
 * 
 * TODO extract isolate code from GeoRefSync and VmeRefSync
 * 
 * 
 * @author Erik van Ingen
 * 
 */

public class VmeRefSync implements Sync {

	@Inject
	FigisDao figisDao;

	@Inject
	VmeDao vmeDao;

	@Override
	public void sync() {
		List<Vme> objects = vmeDao.loadVmes();
		for (Vme vme : objects) {
			RefVme object = (RefVme) figisDao.find(RefVme.class, vme.getId());

			if (object != null && object.getId() <= 0) {
				throw new VmeException("object found in DB withough id");
			}
			if (object == null) {
				// do the new stuff
				object = generateNewRefVme();

				// map it
				map(vme, object);

				// and store it
				figisDao.persist(object);
			} else {
				map(vme, object);
				figisDao.merge(object);
			}
		}
	}

	private RefVme generateNewRefVme() {
		RefVme r = new RefVme();

		return r;
	}

	private void map(Vme vme, RefVme object) {
		object.setId(vme.getId());
		object.setMeta(Figis.META_VME);
		object.setInventoryId(vme.getInventoryIdentifier());
		if (vme.getName() != null && vme.getName().getStringMap() != null) {
			object.setNameA(vme.getName().getStringMap().get(Lang.AR));
			object.setNameC(vme.getName().getStringMap().get(Lang.ZH));
			object.setNameE(vme.getName().getStringMap().get(Lang.EN));
			object.setNameF(vme.getName().getStringMap().get(Lang.FR));
			object.setNameR(vme.getName().getStringMap().get(Lang.RU));
			object.setNameS(vme.getName().getStringMap().get(Lang.ES));
		}
	}
}
