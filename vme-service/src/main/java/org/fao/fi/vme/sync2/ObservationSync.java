package org.fao.fi.vme.sync2;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.sync2.mapping.ObjectMapping;

/**
 * 
 * Sync the Vme DB with the FIGIS VME DB. This includes also the famous FIGIS XML. The data is pushed from the vme DB to
 * the figis DB.
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

	private final ObjectMapping om = new ObjectMapping();

	@Override
	public void sync() {

		List<Vme> objects = vmeDao.loadVmes();

		for (Vme vme : objects) {
			// this refresh here is necessary because apparently eventual new data has not yet been committed? Solving
			// this problem costed me about 3 hours...
			vmeDao.getEm().refresh(vme);

			vme.getRfmo().getGeneralMeasuresList();
			for (GeneralMeasures gm : vme.getRfmo().getGeneralMeasuresList()) {

			}

			VmeObservationDomain vod = om.mapVme2Figis2(vme);
			RefVme refVme = (RefVme) figisDao.find(RefVme.class, vme.getId());
			vod.setRefVme(refVme);
			figisDao.syncVmeObservationDomain(vod);
		}
	}

}
