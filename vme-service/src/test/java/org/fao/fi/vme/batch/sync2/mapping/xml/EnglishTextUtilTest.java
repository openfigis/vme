package org.fao.fi.vme.batch.sync2.mapping.xml;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.fao.fi.figis.devcon.Text;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.util.Lang;
import org.junit.Test;

/**
 * 
 * @author Roberto Empiri
 * 
 */

public class EnglishTextUtilTest {

	private EnglishTextUtil enUtil = new EnglishTextUtil();
	private MultiLingualString mult = new MultiLingualString();
	private HashMap<Integer, String> stringMap = new HashMap<Integer, String>();
	
	@Test
	public void testGetEnglishText() {
		stringMap.put(Lang.EN, "Hello world");
		mult.setStringMap(stringMap);
		Text text = enUtil.getEnglishText(mult);
		assertEquals("en", text.getLang());
		assertEquals("Hello world", text.getContent().get(0));
	}

	@Test
	public void testEnglish() {
		MultiLingualString multEn = enUtil.english("Hello world");
		stringMap.put(Lang.EN, "Hello world");
		mult.setStringMap(stringMap);
		assertEquals(multEn.getStringMap().get(Lang.EN), mult.getStringMap().get(Lang.EN));
		
	}
	
}
