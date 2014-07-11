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

//FFiorellato: Useless?
@Deprecated
public class InformationSourceCodelist {

	private static String value1 = "Book";
	private static String value2 = "Meeting documents";
	private static String value3 = "Journal";
	private static String value4 = "Project";
	private static String value6 = "CD-ROM/DVD";
	private static String value99 = "Other";

	private static final Map<Long, String> map = new HashMap<Long, String>();
	static {
		map.put(Long.valueOf(1), value1);
		map.put(Long.valueOf(2), value2);
		map.put(Long.valueOf(3), value3);
		map.put(Long.valueOf(4), value4);
		map.put(Long.valueOf(6), value6);
		map.put(Long.valueOf(99), value99);
	}

	public String getDescription(Long informationSourceType) {
		return map.get(informationSourceType);
	}
}
