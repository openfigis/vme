package org.fao.fi.vme.sync2.mapping;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.rule.RtmsVme;
import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.util.Lang;

public class ObjectMapping {

	public VmeObservationDomain mapVme2Figis(Vme vme) {
		List<ObservationDomain> odList = new ArrayList<ObservationDomain>();

		List<GeoRef> l = vme.getGeoRefList();
		for (GeoRef geoRef : l) {
			// create the xml (only english yet)
			ObservationXml xml = new ObservationXml();
			xml.setLanguage(Lang.EN);
			xml.setStatus(RtmsVme.STATUS);
			Date d = new Date(System.currentTimeMillis());
			xml.setLastEditDate(d);
			xml.setCreationDate(d);

			List<ObservationXml> xmlList = new ArrayList<ObservationXml>();
			xmlList.add(xml);

			// create an ObservationDomain for every year
			ObservationDomain o = new ObservationDomain();
			o.setReportingYear(geoRef.getYear().toString());
			o.setObservationsPerLanguage(xmlList);

			// add to to the list
			odList.add(o);

		}
		VmeObservationDomain vod = new VmeObservationDomain();
		vod.setObservationDomainList(odList);

		return vod;
	};

}
