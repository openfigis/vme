package org.fao.fi.vme.batch.sync2.mapping.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * Codelist for InformationSources. To be taken from a master data repository in
 * the future.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class InformationSourceCodelist {

	private static String value0 = "Book";
	private static String value1 = "Journal";
	private static String value2 = "Project";
	private static String value3 = "Meeting documents";
	private static String value4 = "CD-ROM/DVD";
	private static String value5 = "Other";

	private static final Map<Integer, String> map = new HashMap<Integer, String>();
	static {
		map.put(new Integer(0), value0);
		map.put(new Integer(1), value1);
		map.put(new Integer(2), value2);
		map.put(new Integer(3), value3);
		map.put(new Integer(4), value4);
		map.put(new Integer(5), value5);
	}

	public String getDescription(Integer informationSourceType) {
		return map.get(informationSourceType);
	}

}
