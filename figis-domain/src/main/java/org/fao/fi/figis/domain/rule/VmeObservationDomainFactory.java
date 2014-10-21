package org.fao.fi.figis.domain.rule;

import java.sql.Date;
import java.util.ArrayList;

import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.VmeObservationDomain;

/**
 * @deprecated
 */

@Deprecated
public class VmeObservationDomainFactory {

	public VmeObservationDomain create() {
		VmeObservationDomain vod = new VmeObservationDomain();
		vod.setObservationDomainList(new ArrayList<ObservationDomain>());
		ObservationDomain o = createObservation();
		ObservationXml xml = createObservationXml();
		o.setObservationsPerLanguage(new ArrayList<ObservationXml>());
		o.getObservationsPerLanguage().add(xml);
		vod.getObservationDomainList().add(o);
		return vod;

	}

	public ObservationDomain createObservation() {
		ObservationDomain o = new ObservationDomain();
		o.setOrder(Figis.ORDER);
		// o.setCollection(Figis.COLLECTION);
		return o;
	}

	public ObservationXml createObservationXml() {
		ObservationXml xml = new ObservationXml();
		xml.setLanguage(2);
		xml.setStatus(2);
		Date d = new Date(System.currentTimeMillis());
		xml.setLastEditDate(d);
		xml.setCreationDate(d);
		return xml;
	}
}
