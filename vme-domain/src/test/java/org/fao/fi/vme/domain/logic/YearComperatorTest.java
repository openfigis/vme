package org.fao.fi.vme.domain.logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.fao.fi.vme.domain.History;
import org.junit.Test;

public class YearComperatorTest {

	@Test
	public void testCompare() {
		History h1 = new History();
		h1.setYear(2010);
		History h2 = new History();
		h2.setYear(2011);
		List<History> l = new ArrayList<History>();
		l.add(h2);
		l.add(h1);
		Collections.sort(l, new YearComperator());
		assertEquals(h1, l.get(0));
		assertEquals(h2, l.get(1));

	}

}
