package org.fao.fi.vme.batch.sync2.mapping.xml;

import org.apache.commons.lang.StringUtils;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

/**
 * Util to create org.fao.fi.figis.devcon.Text with CDATA
 * 
 * @author Erik van Ingen
 * 
 */
public class CdataUtil {

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	public Text getEnglishText(MultiLingualString multiLingualString) {
		String string = u.getEnglish(multiLingualString);
		Text text = null;
		if (!StringUtils.isBlank(string)) {
			text = new Text();
			String textWithCdata = "<![CDATA[" + u.getEnglish(multiLingualString) + "]]>";
			text.getContent().add(textWithCdata);
			text.setLang(Figis.EN);
		}
		return text;
	}

	/**
	 * Only when there is text, otherwise returns null
	 * 
	 * @param englishText
	 * @return
	 */
	public MultiLingualString english(String englishText) {
		MultiLingualString mls = null;
		if (!StringUtils.isBlank(englishText)) {
			mls = u.english(englishText);
		}
		return mls;
	}

}
