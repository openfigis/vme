package org.fao.fi.figis.domain.rule;

import java.sql.Date;
import java.util.ArrayList;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.VmeObservationDomain;

public class VmeObservationDomainFactory {

	public VmeObservationDomain create() {
		VmeObservationDomain vod = new VmeObservationDomain();
		vod.setObservationList(new ArrayList<Observation>());
		Observation o = createObservation();
		ObservationXml xml = createObservationXml();
		o.setObservationsPerLanguage(new ArrayList<ObservationXml>());
		o.getObservationsPerLanguage().add(xml);
		vod.getObservationList().add(o);
		return vod;

	}

	public Observation createObservation() {
		Observation o = new Observation();
		o.setOrder(RtmsVme.ORDER);
		o.setCollection(RtmsVme.COLLECTION);
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
