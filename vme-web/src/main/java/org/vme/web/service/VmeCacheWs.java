package org.vme.web.service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ehcache.Cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.config.vme.VmeDB;

/**
 * Webservice in order to be able to do cache operations when needed, not at
 * every other webservice request.
 * 
 * 
 * 
 * @author CacheDeleteHandler van Ingen
 * 
 */
@Path("/cache-delete")
@Singleton
public class VmeCacheWs {

	private static final Logger LOG = LoggerFactory.getLogger(VmeCacheWs.class);

	private static String MESSAGE = "VME_CACHE_DELETED_SUCCESS";

	@Inject
	@VmeDB
	private EntityManager em;

	@Inject
	private Cache cache;

	/**
	 * Clearing the First-level cache of Hibernate by invoking
	 * EntityManager.clear()
	 * 
	 * Clearing the ehCacheManager by invoking the cacheManager.clearAll();
	 * 
	 * @return VME_CACHE_DELETED_SUCCESS
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public synchronized String clean() {
		String message = MESSAGE;
		try {
			em.clear();
			cache.removeAll();
		} catch (PersistenceException e) {
			message = e.getMessage();
			LOG.error("Clearing Hibernate and EhCache caches in vme-web failed", e);
		}
		LOG.info("First level cache of Hibernate EntityManager has been cleared. EhCache cleared.");

		return message;
	}
}