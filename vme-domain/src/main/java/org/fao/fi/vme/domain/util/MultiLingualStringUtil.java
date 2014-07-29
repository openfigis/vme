package org.fao.fi.vme.domain.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.fao.fi.vme.domain.model.MultiLingualString;

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
	
	public void addFrench(MultiLingualString u, String text) {
		u.getStringMap().put(Lang.FR, text);
	}

	/**
	 * Return english string if there is, else null.
	 * 
	 * @param multiLingualString
	 * @return
	 */
	public String getEnglish(MultiLingualString multiLingualString) {
		String english = null;
		if (multiLingualString != null) {
			english = multiLingualString.getStringMap().get(Lang.EN);
			if (StringUtils.isBlank(english)) {
				english = null;
			}
		}
		return english;
	}

}
