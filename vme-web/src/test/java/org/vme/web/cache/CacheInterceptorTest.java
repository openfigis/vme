package org.vme.web.cache;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({ CacheProducer.class, CacheInterceptor.class })
public class CacheInterceptorTest {

	@Inject
	CacheInterceptor cacheInterceptor;

	@Ignore("TODO I dont get this one to work")
	@Test
	public void testAuthorize() {
		assertNotNull(cacheInterceptor);
	}

}
