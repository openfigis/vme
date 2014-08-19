package org.vme.web.cache;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.search.ObservationsRequest;

@RunWith(CdiRunner.class)
@AdditionalClasses({ CacheProducer.class, CacheInterceptor.class })
public class CacheTest {

	@Inject
	TestHelperCache h;

	@Test
	public void testClean() {

		ObservationsRequest o = new ObservationsRequest(null);
		o.setText("volkskrant");

		assertFalse(h.nonCachedMethod(o).equals(h.nonCachedMethod(o)));
		assertTrue(h.cachedMethod(o).equals(h.cachedMethod(o)));

	}

}
