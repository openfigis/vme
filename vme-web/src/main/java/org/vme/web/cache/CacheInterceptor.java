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

	@Inject
	private Cache cache;

	private CacheKeyGenerator g = new CacheKeyGenerator();

	@AroundInvoke
	public Object authorize(InvocationContext ic) {
		String key = g.generateKey(ic.getParameters());
		log.info("key is " + key);

		if ("".equals(key)) {
			log.error("key should not be null");
			throw new VmeException("key should not be null");
		}
		Object returnObject = null;

		if (cache.isKeyInCache(key)) {
			if (cache.get(key) != null) {
				returnObject = (Object) cache.get(key).getObjectValue();
			} else {
				log.error("Key found but not object?");
				throw new VmeException("Key found but not object?");
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
			log.error("Method should not return null");
			throw new VmeException("Method should not return null");
		}
		return returnObject;
	}

}