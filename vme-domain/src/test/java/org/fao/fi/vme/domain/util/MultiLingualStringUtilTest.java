package org.fao.fi.vme.domain.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.junit.Test;

public class MultiLingualStringUtilTest {

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testEnglish() {
		String text = "Een klachten vrije wereld";
		MultiLingualString l = u.english(text);
		assertEquals(text, l.getStringMap().get(Lang.EN));
	}
	
	@Test
	public void testGetEnglish(){
		MultiLingualString l = new MultiLingualString();
		l = u.english("");
		assertNull(u.getEnglish(l));
		String text = "Een klachten vrije wereld";
		l = u.english(text);
		assertEquals(text, l.getStringMap().get(Lang.EN));
	}
}
