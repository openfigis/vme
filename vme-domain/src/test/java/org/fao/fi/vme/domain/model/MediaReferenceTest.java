package org.fao.fi.vme.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.fao.fi.vme.domain.test.MediaReferenceMock;
import org.junit.Test;

public class MediaReferenceTest {

	@Test
	public void testEqualsObject() {
		MediaReference r1 = MediaReferenceMock.create();
		MediaReference r2 = MediaReferenceMock.create();
		assertEquals(r1, r2);
		r1.setCredits(r1.getDescription());
		assertFalse(r1.equals(r2));
	}

}
