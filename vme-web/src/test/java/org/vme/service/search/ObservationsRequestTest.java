package org.vme.service.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;

public class ObservationsRequestTest {

	@Test
	public void testEqualsObject() {
		ObservationsRequest o1 = new ObservationsRequest(UUID.randomUUID());
		o1.setText("gross");
		ObservationsRequest o2 = new ObservationsRequest(UUID.randomUUID());
		o2.setText("gross");
		assertTrue(o1.equals(o2));
		assertEquals(o1.hashCode(), o2.hashCode());

	}

}
