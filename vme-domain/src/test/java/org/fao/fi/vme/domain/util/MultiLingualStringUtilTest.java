package org.fao.fi.vme.domain.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.junit.Test;

public class MultiLingualStringUtilTest {

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void erik() {
		System.out.println(0 ^ 0);
		System.out.println(0 ^ 1);
		System.out.println(1 ^ 0);
		System.out.println(1 ^ 1);

		System.out.println(0 | 0);
		System.out.println(0 | 1);
		System.out.println(1 | 0);
		System.out.println(1 | 1);
	}

	@Test
	public void testCopyMultiLingual() {
		String s1 = "Mozart";
		String s2 = "Beethoven";
		MultiLingualString m1 = u.english(s1);
		MultiLingualString m2 = u.english(s2);

		GeneralMeasure source = new GeneralMeasure();
		source.setFishingArea(m1);
		GeneralMeasure destination = new GeneralMeasure();
		destination.setFishingArea(m2);
		Object oDestination = destination.getFishingArea().getStringMap();

		assertEquals(u.getEnglish(source.getFishingArea()), s1);
		assertEquals(u.getEnglish(destination.getFishingArea()), s2);
		u.copyMultiLingual(source, destination);

		assertEquals(u.getEnglish(source.getFishingArea()), s1);
		assertEquals(u.getEnglish(destination.getFishingArea()), s1);

		// should still contain only 1 string
		assertEquals(1, destination.getFishingArea().getStringMap().size());

		// make sure the destination has not changed object, only the string
		// needs to be changed.
		assertEquals(oDestination, destination.getFishingArea().getStringMap());

	}

	@Test
	public void testCopyMultiLingualNullCaseDestination() {
		String s1 = "Mozart";
		MultiLingualString m1 = u.english(s1);
		GeneralMeasure source = new GeneralMeasure();
		source.setFishingArea(m1);
		GeneralMeasure destination = new GeneralMeasure();
		u.copyMultiLingual(source, destination);
		assertEquals(s1, u.getEnglish(destination.getFishingArea()));
	}

	@Test
	public void testCopyMultiLingualNullCaseSource() {
		String s1 = "Mozart";
		MultiLingualString m1 = u.english(s1);
		GeneralMeasure source = new GeneralMeasure();
		GeneralMeasure destination = new GeneralMeasure();
		destination.setFishingArea(m1);
		u.copyMultiLingual(source, destination);
		assertEquals(null, u.getEnglish(destination.getFishingArea()));
	}

	@Test
	public void testReplace() {
		String text = "Een klachten vrije wereld";
		MultiLingualString l = u.english(text);
		assertEquals(text, l.getStringMap().get(Lang.EN));
		Object o = l.getStringMap();

		String newText = "Een vrije klachten wereld";
		u.replaceEnglish(l, newText);
		assertEquals(newText, l.getStringMap().get(Lang.EN));
		assertEquals(o, l.getStringMap());

	}

	@Test
	public void testReplaceEmpty() {
		MultiLingualString l = new MultiLingualString();
		String newText = "Een vrije klachten wereld";
		try {
			u.replaceEnglish(l, newText);
			fail();
		} catch (Exception e) {
		}
	}

	@Test
	public void testEnglish() {
		String text = "Een klachten vrije wereld";
		MultiLingualString l = u.english(text);
		assertEquals(text, l.getStringMap().get(Lang.EN));
	}

	@Test
	public void testGetEnglish() {
		MultiLingualString l = new MultiLingualString();
		l = u.english("");
		assertNull(u.getEnglish(l));
		String text = "Een klachten vrije wereld";
		l = u.english(text);
		assertEquals(text, l.getStringMap().get(Lang.EN));
	}
}
