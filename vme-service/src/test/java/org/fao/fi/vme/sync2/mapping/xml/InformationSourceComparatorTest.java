package org.fao.fi.vme.sync2.mapping.xml;

import static org.junit.Assert.assertTrue;

import org.fao.fi.vme.domain.model.InformationSource;
import org.junit.Test;

public class InformationSourceComparatorTest {

	InformationSourceComparator c = new InformationSourceComparator();

	@Test
	public void testCompare() {
		InformationSource o1 = new InformationSource();
		o1.setPublicationYear(2000);

		InformationSource o2 = new InformationSource();
		o2.setPublicationYear(1999);
		assertTrue(c.compare(o1, o2) < 0);
		o2.setPublicationYear(2000);
		assertTrue(c.compare(o1, o2) == 0);
		o2.setPublicationYear(2001);
		assertTrue(c.compare(o1, o2) > 0);
	}
}
