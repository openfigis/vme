package org.fao.fi.vme.domain.util;

import org.apache.commons.lang.StringUtils;
import org.fao.fi.vme.domain.model.MultiLingualString;

/**
 * General purpose validator for MultiLingualString
 * 
 * Not yet used.
 * 
 * @author Erik van Ingen
 *
 */
public class MultiLingualStringValidator {

	private String report;

	public String getReport() {
		return report;
	}

	/**
	 * A MultiLingualString is considered valid if it holds at least real English text.
	 * 
	 * @param MultiLingualString
	 *            mls
	 * @return
	 */
	public boolean validate(MultiLingualString mls) {
		boolean valid = true;
		report = "";
		if (mls.getStringMap() == null || mls.getStringMap().get(Lang.EN) == null) {
			valid = false;
			report = "The MultiLingualString did not contain any text, it should contain it in order to be able to be replaced. StringMap == "
					+ mls.getStringMap() + ".";
		} else if (StringUtils.isBlank(mls.getStringMap().get(Lang.EN))) {
			valid = false;
			report = "The string is empty";
		}

		return valid;

	}
}
