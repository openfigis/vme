package org.fao.fi.vme.batch.sync2;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.vme.batch.sync2.mapping.ObjectMapping;
import org.fao.fi.vme.domain.model.Vme;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.vme.dao.ReferenceDAO;
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
	
	@Inject @ConceptProvider private ReferenceDAO refDao;

	private ObjectMapping om;

	@Override
	public void sync() {

		List<Vme> objects = vmeDao.loadVmes();

		for (Vme vme : objects) {
			sync(vme);

		}
	}
	
	@PostConstruct
	public void postConstruct() {
		this.om = new ObjectMapping(this.refDao);
	}

	@Override
	public void sync(Vme vme) {
		VmeObservationDomain vod = om.mapVme2Figis2(vme);

		RefVme refVme = (RefVme) figisDao.find(RefVme.class, vme.getId());
		vod.setRefVme(refVme);
		figisDao.syncVmeObservationDomain(vod);

	}
}
