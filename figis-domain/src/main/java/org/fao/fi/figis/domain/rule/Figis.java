package org.fao.fi.figis.domain.rule;

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
	public static final Integer COLLECTION = 7300;

	/**
	 * 
	 */
	public static final Boolean PRIMARY = true;
	/**
	 * A reference factsheet is not to be published. Is not relevant for vme because the lifecycle is outside the figis
	 * cms.
	 */
	public static final Boolean REFERENCE = false;
	public static final Integer META = 172000;
	public static final Integer STATUS = 2;
	public static final Integer LANG = 1;
	public static final String CODE_SYSTEM = "vme";
	public static final String EN = "en";

}
