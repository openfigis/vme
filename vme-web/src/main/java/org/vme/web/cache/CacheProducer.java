package org.vme.web.cache;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class CacheProducer {

	private static final String cacheName = "vme-web-cache";

	@Produces
	Cache produceCache() {
		CacheManager singletonManager = this.produceCacheManager();

		// Cache(String name, int maxElementsInMemory,
		// boolean overflowToDisk, boolean eternal, long timeToLiveSeconds, long
		// timeToIdleSeconds)

		// for some reason, the @ApplicationScoped is not liked by the unittests
		// (proxy problems)
		if (!singletonManager.cacheExists(cacheName)) {
			Cache memoryOnlyCache = new Cache(cacheName, 5000, false, true, 0, 0);

			singletonManager.addCache(memoryOnlyCache);
		}

		return singletonManager.getCache(cacheName);
	}

	@Produces
	@ApplicationScoped
	CacheManager produceCacheManager() {
		return CacheManager.create();
	}

}
