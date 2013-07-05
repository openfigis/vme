package org.fao.fi.vme.sync2;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.rule.VmeObservationDomainFactory;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.domain.GeoRef;
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

	VmeObservationDomainFactory f = new VmeObservationDomainFactory();

	public static final Short ORDER = -1;
	public static final Integer COLLECTION = 7300;

	@Inject
	FigisDao figisDao;

	@Inject
	VmeDao vmeDao;

	ObjectMapping om = new ObjectMapping();

	@Override
	public void sync() {
		List<Vme> objects = vmeDao.loadVmes();
		for (Vme vme : objects) {
			VmeObservationDomain vod = om.mapVme2Figis(vme);
		}
	}

	private void map(Vme vme, VmeObservationDomain vod) {
		List<GeoRef> l = vme.getGeoRefList();
		for (GeoRef geoRef : l) {

		}

		Observation o = vod.getObservationList().get(0);
		ObservationXml xml = o.getObservationsPerLanguage().get(0);

		vod.setReportingYear(vme.getValidityPeriod().getEndYear().toString());

		// is it really necessary to always get the reference object?
		RefVme refVme = (RefVme) figisDao.find(RefVme.class, vme.getId());
		vod.setRefVme(refVme);
		xml.setLastEditDate(new Date(System.currentTimeMillis()));
	}
}
