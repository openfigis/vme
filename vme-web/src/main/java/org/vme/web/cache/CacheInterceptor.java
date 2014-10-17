package org.vme.web.cache;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.fao.fi.vme.VmeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Interceptor to add to methods which need to be cached. Wrote one because
 * Jcache is still not up to speed.
 * 
 * 
 * @author Erik van Ingen
 *
 */
@Cached
@Interceptor
public class CacheInterceptor {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static final String KEYNULL = "key should not be null";
	private static final String KEYNOTFOUND = "Key found but not object?";
	private static final String METHOD = "Method should not return null";

	@Inject
	private Cache cache;

	private CacheKeyGenerator g = new CacheKeyGenerator();

	@AroundInvoke
	public Object authorize(InvocationContext ic) {

		String key = g.generateKey(ic.getMethod().getName(), ic.getParameters());
		log.info("key is " + key);

		if ("".equals(key)) {
			log.error(KEYNULL);
			throw new VmeException(KEYNULL);
		}
		Object returnObject = null;

		if (cache.isKeyInCache(key)) {
			if (cache.get(key) != null) {
				returnObject = (Object) cache.get(key).getObjectValue();
			} else {
				log.error(KEYNOTFOUND);
				throw new VmeException(KEYNOTFOUND);
			}
		}
		if (returnObject == null) {
			try {
				returnObject = (Object) ic.proceed();
			} catch (Exception e) {
				throw new VmeException(e);
			}
			Element element = new Element(key, returnObject);
			cache.put(element);
		}

		if (returnObject == null) {
			log.error(METHOD);
			throw new VmeException(METHOD);
		}
		return returnObject;
	}
}