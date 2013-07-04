package org.fao.fi.vme.sync2;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.msaccess.component.VmeDaoException;

/**
 * 
 * sync the Vme DB with the FIGIS VME DB. This includes also the famous FIGIS XML.
 * 
 * 
 * @author Erik van Ingen
 * 
 */

public class ObservationSync implements Sync {

	public static final Short ORDER = -1;
	public static final Integer COLLECTION = 7300;

	@Inject
	FigisDao figisDao;

	@Inject
	VmeDao vmeDao;

	// @Override
	@Override
	public void sync() {
		List<Vme> objects = vmeDao.loadVmes();
		for (Vme vme : objects) {
			VmeObservationDomain vod = null;

			VmeObservation object = (VmeObservation) figisDao.find(VmeObservation.class, vme.getId());

			if (object != null && object.getObservationId() <= 0) {
				throw new VmeDaoException("object found in DB withough id");
			}
			if (object == null) {
				// do the new stuff
				vod = generateNewRefVme();

				// map it
				map(vme, vod);

				// and store it
				figisDao.persist(object);
			} else {
				figisDao.findVmeObservationDomain(vme.getId());
				map(vme, vod);
				figisDao.merge(vod);
			}
		}
	}

	private VmeObservationDomain generateNewRefVme() {
		VmeObservationDomain vod = new VmeObservationDomain();

		return vod;
	}

	private void map(Vme vme, VmeObservationDomain object) {
		// object.setId(vme.getId());
		// object.setMeta(172000);
	}
}
