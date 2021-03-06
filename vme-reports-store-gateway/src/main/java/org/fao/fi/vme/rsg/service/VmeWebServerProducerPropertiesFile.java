/**
 * (c) 2014 FAO / UN (project: vme-dao)
 */
package org.fao.fi.vme.rsg.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb.VmeWebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Alternative
 * @author Erik van Ingen
 * 
 */
@Alternative
public class VmeWebServerProducerPropertiesFile {
	final static private Logger LOG = LoggerFactory.getLogger(VmeWebServerProducerPropertiesFile.class);

	@Produces
	public VmeWebServer produceVmeWebSearchCacheClient() {
		VmeWebServer c = new VmeWebServer();

		Properties prop = new Properties();
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("vme-web.properties");
			prop.load(inputStream);

			// get the property value and print it out
			c.setServer(prop.getProperty("vme.web.server"));

		} catch (IOException ex) {
			LOG.error("Problem reading vme-web.properties", ex);
			throw new VmeException(ex);
		}
		return c;
	}
}