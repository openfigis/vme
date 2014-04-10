package org.vme.web.service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

	static final private Logger LOG = LoggerFactory.getLogger(VmeCacheWs.class);

	private static String MESSAGE = "VME_CACHE_DELETED_SUCCESS";

	@Inject
	@VmeDB
	private EntityManager entityManager;

	/**
	 * Clearing the First-level cache of Hibernate by invoking
	 * EntityManager.clear()
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String clean() {

		System.out.println("The real VmeCacheWs fired!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		String message = MESSAGE;
		try {
			entityManager.clear();
		} catch (PersistenceException e) {
			message = e.getMessage();
			LOG.error("Caching of vme-web failed", e);
		}
		LOG.debug("First level cache of Hibernate EntityManager has been cleared.");

		return message;
	}
}