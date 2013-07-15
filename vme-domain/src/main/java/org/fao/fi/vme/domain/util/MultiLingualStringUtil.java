package org.fao.fi.vme.domain.util;

import java.util.HashMap;
import java.util.Map;

import org.fao.fi.vme.domain.MultiLingualString;

/**
 * Utility class in order to generate a multilingual strings
 * 
 * @author Erik van Ingen
 * 
 */
public class MultiLingualStringUtil {

	public MultiLingualString english(String text) {
		MultiLingualString l = new MultiLingualString();
		Map<Integer, String> stringMap = new HashMap<Integer, String>();
		stringMap.put(Lang.EN, text);
		l.setStringMap(stringMap);
		return l;
	}

	public String getEnglish(MultiLingualString multiLingualString) {
		return multiLingualString.getStringMap().get(Lang.EN);
	}

}
