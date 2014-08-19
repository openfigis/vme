package org.vme.web.service;

import javax.interceptor.Interceptors;

import org.vme.web.cache.CacheInterceptor;
import org.vme.web.cache.Cached;

@Interceptors({ CacheInterceptor.class })
public class TestHelperCache {

	private int counter = 0;

	@Cached
	public int cachedMethod() {
		counter++;
		return counter;
	}

	public int getCounter() {
		return counter;
	}

	// @CacheRemoveAll(cacheName = "vme-web")
	public void cacheRemoveAll() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + counter;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestHelperCache other = (TestHelperCache) obj;
		if (counter != other.counter)
			return false;
		return true;
	}

}
