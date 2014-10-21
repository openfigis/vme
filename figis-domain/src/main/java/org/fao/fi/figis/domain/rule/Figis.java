package org.fao.fi.figis.domain.rule;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Take the defaults from this class in order to push data to figis.
 * 
 * 
 * http://km.fao.org/FIGISwiki/index.php/FigisFlow
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class Figis {

	public static final Short ORDER = -1;
	public static final String COLLECTION = "_VME";

	// public static final Integer COLLECTION = 7300;

	public static final Map<String, Integer> RFMO_COVERPAGE = new HashMap<String, Integer>();
	public static final Map<String, Integer> RFMO_COLLECTION = new HashMap<String, Integer>();
	static {
		RFMO_COVERPAGE.put("CCAMLR", 861);
		RFMO_COVERPAGE.put("GFCM", 862);
		RFMO_COVERPAGE.put("NAFO", 863);
		RFMO_COVERPAGE.put("NEAFC", 864);
		RFMO_COVERPAGE.put("NPFC", 865);
		RFMO_COVERPAGE.put("SEAFO", 866);
		RFMO_COVERPAGE.put("SPRFMO", 867);
		RFMO_COVERPAGE.put("WECAFC", 868);

		RFMO_COLLECTION.put("CCAMLR", 7351);
		RFMO_COLLECTION.put("GFCM", 7352);
		RFMO_COLLECTION.put("NAFO", 7353);
		RFMO_COLLECTION.put("NEAFC", 7354);
		RFMO_COLLECTION.put("SEAFO", 7355);
		RFMO_COLLECTION.put("NPFC", 7356);
		RFMO_COLLECTION.put("SPRFMO", 7357);

		RFMO_COLLECTION.put("WECAFC", 0); // fake number, does not yet exist.

	}
	/**
	 * 
	 */
	public static final Boolean PRIMARY = false;
	/**
	 * A reference factsheet is not to be published. Is not relevant for vme
	 * because the lifecycle is outside the figis cms.
	 */
	public static final Boolean REFERENCE = false;

	public static final Integer META_ID = 267000;
	public static final Integer META_VME = 172000;
	public static final Integer META_WATER_AREA = 280001;
	public static final Integer STATUS = 2;
	public static final Integer LANG = 1;
	public static final String CODE_SYSTEM = "vme";
	public static final String EN = "en";

	private Figis() {

	}

}
