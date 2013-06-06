package org.fao.fi.figis.domain.rule;

import java.util.HashMap;
import java.util.Map;

import org.fao.fi.figis.domain.ObservationXml;

/**
 * TODO provide this class with reference data from RTMS. Now hardcoded in this class.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class DomainRule {

	static Map<Integer, String> map = new HashMap<Integer, String>();
	static {
		map.put(new Integer(0), "--");
		map.put(new Integer(1), "en");
		map.put(new Integer(2), "fr");
		map.put(new Integer(3), "es");
		map.put(new Integer(4), "zh");
		map.put(new Integer(5), "ar");
		map.put(new Integer(6), "it");
		map.put(new Integer(7), "de");
		map.put(new Integer(8), "pt");
		map.put(new Integer(9), "el");
		map.put(new Integer(10), "hr");
		map.put(new Integer(11), "sl");
		map.put(new Integer(12), "ru");
		map.put(new Integer(13), "uk");
		map.put(new Integer(14), "bg");
		map.put(new Integer(15), "ro");
		map.put(new Integer(16), "tr");
		map.put(new Integer(99), "--");

	}

	/**
	 * compose id based on the language and the observation id.
	 * 
	 * @param o
	 */
	public void composeId(ObservationXml xml) {

		String id = xml.getObservation().getId() + ":" + map.get(xml.getLanguage());
		xml.setId(id);
	}
}
