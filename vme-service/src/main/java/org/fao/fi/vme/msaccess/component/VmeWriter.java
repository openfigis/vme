package org.fao.fi.vme.msaccess.component;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.fimes.jaxb.FimesValidationEventHandler;

/**
 * Writes the data to the vme DB.
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeWriter {
	final static private Logger LOG = LoggerFactory.getLogger(VmeWriter.class);
	@Inject
	TableWriter tableWriter;

	public void write(List<ObjectCollection> objectCollectionList) {
		for (ObjectCollection objectCollection : objectCollectionList) {
			// System.out.println(objectCollection.getClazz().getSimpleName());
			tableWriter.write(objectCollection);
		}
	}

	public void merge(List<ObjectCollection> objectCollectionList) {
		for (ObjectCollection objectCollection : objectCollectionList) {
			LOG.debug("========================");
			LOG.debug(objectCollection.getClazz().getSimpleName());
			tableWriter.merge(objectCollection);
		}
	}

}
