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
@Deprecated //FFiorellato: Useless?
public class InformationSourceCodelist {

	private static String value1 = "Book";
	private static String value2 = "Meeting documents";
	private static String value3 = "Journal";
	private static String value4 = "Project";
	private static String value6 = "CD-ROM/DVD";
	private static String value99 = "Other";

	private static final Map<Long, String> map = new HashMap<Long, String>();
	static {
		map.put(new Long(1), value1);
		map.put(new Long(2), value2);
		map.put(new Long(3), value3);
		map.put(new Long(4), value4);
		map.put(new Long(6), value6);
		map.put(new Long(99), value99);
	}

	public String getDescription(Long informationSourceType) {
		return map.get(informationSourceType);
	}
}
