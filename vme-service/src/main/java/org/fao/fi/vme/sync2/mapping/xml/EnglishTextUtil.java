package org.fao.fi.vme.sync2.mapping.xml;

import org.apache.commons.lang.StringUtils;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.vme.domain.MultiLingualString;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class EnglishTextUtil {

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	public Text getEnglishText(MultiLingualString multiLingualString) {
		String string = u.getEnglish(multiLingualString);
		Text text = null;
		if (!StringUtils.isBlank(string)) {
			text = new Text();
			text.getContent().add(u.getEnglish(multiLingualString));
			text.setLang(Figis.EN);
		}
		return text;
	}
}
