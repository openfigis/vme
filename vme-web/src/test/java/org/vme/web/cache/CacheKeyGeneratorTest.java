package org.vme.web.cache;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CacheKeyGeneratorTest {

	@Test
	public void testGenerateKey() {
		CacheKeyGenerator g = new CacheKeyGenerator();
		Object[] p = { 3, "fiets" };

		assertEquals("3fiets", g.generateKey(p));
	}
}
