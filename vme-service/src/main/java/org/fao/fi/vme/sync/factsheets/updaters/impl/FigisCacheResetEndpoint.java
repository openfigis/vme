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
public class FigisCacheResetEndpoint {

	private static final String PROPERTY_FILE_NAME = "vme-reports-store-gateway.properties";
	private static final String PROPERTY_NAME_SERVER = "figis.web.cache.reset.server.vme";
	private static final String PROPERTY_NAME_RESOURCE = "figis.web.cache.reset.resource.vme";
	private static final String PROPERTY_PNAME_RESOURCE = "figis.web.cache.reset.paramtername.vme";
	private static final String PROPERTY_PVALUE_RESOURCE = "figis.web.cache.reset.paramtervalue.vme";

	private static final Logger LOG = LoggerFactory.getLogger(FigisCacheResetEndpoint.class);

	private String cacheResetServer;

	private String cacheResetResource;
	private String cacheResetParameterName;
	private String cacheResetParameterValue;

	public FigisCacheResetEndpoint() {

		Properties prop = new Properties();
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);
			prop.load(inputStream);

			// get the property value and print it out
			this.cacheResetServer = prop.getProperty(PROPERTY_NAME_SERVER);
			this.cacheResetResource = prop.getProperty(PROPERTY_NAME_RESOURCE);
			this.cacheResetParameterName = prop.getProperty(PROPERTY_PNAME_RESOURCE);
			this.cacheResetParameterValue = prop.getProperty(PROPERTY_PVALUE_RESOURCE);

		} catch (IOException ex) {
			LOG.error("Problem reading " + PROPERTY_FILE_NAME, ex);
			throw new VmeException(ex);
		}

	}

	public String getCacheResetEndpoint() {
		return cacheResetServer + cacheResetResource + "?" + cacheResetParameterName + "=" + cacheResetParameterValue;
	}

	public String getCacheResetParameterName() {
		return cacheResetParameterName;
	}

	public String getCacheResetParameterValue() {
		return cacheResetParameterValue;
	}

	public String getCacheResetServer() {
		return cacheResetServer;
	}

	public String getCacheResetResource() {
		return cacheResetResource;
	}

	public void setCacheResetServer(String cacheResetServer) {
		this.cacheResetServer = cacheResetServer;
	}
}
