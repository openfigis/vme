package org.fao.fi.vme.sync2.mapping.xml;

import java.sql.Date;

import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.rule.Figis;

/**
 * 
 * When creating the observation, this class is used with certain default values.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class DefaultObservationXml {

	public ObservationXml define() {
		ObservationXml xml = new ObservationXml();
		xml.setLastEditDate(new Date(System.currentTimeMillis()));
		xml.setCreationDate(new Date(System.currentTimeMillis()));
		xml.setLanguage(Figis.LANG);
		xml.setStatus(Figis.STATUS);
		return xml;
	}
}
