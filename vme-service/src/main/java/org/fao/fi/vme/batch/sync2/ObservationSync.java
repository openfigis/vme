package org.fao.fi.vme.batch.sync2;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.vme.batch.sync2.mapping.ObjectMapping;
import org.fao.fi.vme.domain.model.Vme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;

/**
 * 
 * Sync the Vme DB with the FIGIS VME DB. This includes also the famous FIGIS
 * XML. The data is pushed from the vme DB to the figis DB.
 * 
 * 
 * @author Erik van Ingen
 * 
 */

public class ObservationSync implements Sync {

	@Inject
	private FigisDao figisDao;

	@Inject
	private VmeDao vmeDao;

	@Inject
	private ObjectMapping om;
	
	private static final Logger LOG = LoggerFactory.getLogger(ObservationSync.class);

	@Override
	public void sync() {

		List<Vme> objects = vmeDao.loadVmes();

		LOG.info(String.valueOf(objects.size()));
		for (Vme vme : objects) {
			if (vme.getId() != 28405L) {
				sync(vme);
			}

		}
	}

	@Override
	public void sync(Vme vme) {
		VmeObservationDomain vod = om.mapVme2Figis2(vme);

		RefVme refVme = (RefVme) figisDao.find(RefVme.class, vme.getId());
		vod.setRefVme(refVme);
		figisDao.syncVmeObservationDomain(vod);

	}
}
