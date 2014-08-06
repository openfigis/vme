package org.fao.fi.vme.domain.logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.fao.fi.vme.domain.model.VMEsHistory;
import org.junit.Test;

public class YearComperatorTest {

	@Test
	public void testCompare() {
		VMEsHistory h1 = new VMEsHistory();
		h1.setYear(2010);
		VMEsHistory h2 = new VMEsHistory();
		h2.setYear(2011);
		List<VMEsHistory> l = new ArrayList<VMEsHistory>();
		l.add(h2);
		l.add(h1);
		Collections.sort(l, new YearComparator());
		assertEquals(h1, l.get(0));
		assertEquals(h2, l.get(1));

	}

}
