package org.fao.fi.vme.sync.factsheets.updaters.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Singleton;

import org.fao.fi.vme.VmeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class loads the value figis.web.cache.reset.endpoint.vme from the
 * property file vme-reports-store-gateway.properties
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@Singleton
public class CacheResetEndpoint {

	private static final String PROPERTY_FILE_NAME = "vme-reports-store-gateway.properties";
	private static final String PROPERTY_NAME = "figis.web.cache.reset.endpoint.vme";
	final static private Logger LOG = LoggerFactory.getLogger(CacheResetEndpoint.class);

	private String cacheResetEndpoint;

	public CacheResetEndpoint() {

		Properties prop = new Properties();
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);
			prop.load(inputStream);

			// get the property value and print it out
			this.cacheResetEndpoint = prop.getProperty(PROPERTY_NAME);

		} catch (IOException ex) {
			LOG.error("Problem reading " + PROPERTY_FILE_NAME, ex);
			throw new VmeException(ex);
		}

	}

	public String getCacheResetEndpoint() {
		return cacheResetEndpoint;
	}

}
