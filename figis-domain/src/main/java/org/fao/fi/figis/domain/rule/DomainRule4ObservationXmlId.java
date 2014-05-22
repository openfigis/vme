package org.fao.fi.figis.domain.rule;

import java.util.HashMap;
import java.util.Map;

import org.fao.fi.figis.FigisDomainException;
import org.fao.fi.figis.domain.ObservationXml;

/**
 * TODO provide this class with reference data from RTMS. Now hardcoded in this class.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class DomainRule4ObservationXmlId {

	static Map<Integer, String> map = new HashMap<Integer, String>();
	static {
		map.put(Integer.valueOf(0), "--");
		map.put(Integer.valueOf(1), "en");
		map.put(Integer.valueOf(2), "fr");
		map.put(Integer.valueOf(3), "es");
		map.put(Integer.valueOf(4), "zh");
		map.put(Integer.valueOf(5), "ar");
		map.put(Integer.valueOf(6), "it");
		map.put(Integer.valueOf(7), "de");
		map.put(Integer.valueOf(8), "pt");
		map.put(Integer.valueOf(9), "el");
		map.put(Integer.valueOf(10), "hr");
		map.put(Integer.valueOf(11), "sl");
		map.put(Integer.valueOf(12), "ru");
		map.put(Integer.valueOf(13), "uk");
		map.put(Integer.valueOf(14), "bg");
		map.put(Integer.valueOf(15), "ro");
		map.put(Integer.valueOf(16), "tr");
		map.put(Integer.valueOf(99), "--");

	}

	/**
	 * compose id based on the language and the observation id.
	 * 
	 * @param o
	 */
	public void composeId(ObservationXml xml) {
		if (xml.getObservation().getId() == null || xml.getLanguage() == null) {
			throw new FigisDomainException("xmlObservation.id() or xml.language is null, observationId = "
					+ xml.getObservation().getId() + ", language = " + xml.getLanguage());
		}
		String id = composeId(xml.getObservation().getId(), map.get(xml.getLanguage()));
		xml.setId(id);
	}

	public String composeId(Long observationId, String language) {
		if (observationId == null || language == null) {
			throw new FigisDomainException("observationId or language is null, observationId = " + observationId
					+ ", language = " + language);
		}
		return observationId + ":" + language;
	}

}
