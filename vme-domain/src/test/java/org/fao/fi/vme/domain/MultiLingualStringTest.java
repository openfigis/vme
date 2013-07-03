package org.fao.fi.vme.domain;

import java.util.HashMap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MultiLingualStringTest {

	MultiLingualString s = new MultiLingualString();

	@Test
	public void testGetStringMap() {
		String text = "ik wil naar huis";
		int lang = 4;
		s.setStringMap(new HashMap<Integer, String>());
		s.getStringMap().put(lang, text);
		assertEquals(text, s.getStringMap().get(lang));
	}

}
