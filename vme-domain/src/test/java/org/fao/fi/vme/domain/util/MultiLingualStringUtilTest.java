package org.fao.fi.vme.domain.util;

import org.fao.fi.vme.domain.MultiLingualString;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MultiLingualStringUtilTest {

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testEnglish() {
		String text = "Een klachten vrije wereld";
		MultiLingualString l = u.english(text);
		assertEquals(text, l.getStringMap().get(Lang.EN));
	}
}
