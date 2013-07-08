package org.fao.fi.vme.sync2;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.sync2.mapping.ObjectMapping;

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
	private FigisDao figisDao;

	@Inject
	private VmeDao vmeDao;

	private final ObjectMapping om = new ObjectMapping();

	@Override
	public void sync() {
		List<Vme> objects = vmeDao.loadVmes();
		for (Vme vme : objects) {
			VmeObservationDomain vod = om.mapVme2Figis(vme);
			RefVme refVme = (RefVme) figisDao.find(RefVme.class, vme.getId());
			vod.setRefVme(refVme);
			figisDao.syncVmeObservationDomain(vod);
		}
	}

}
