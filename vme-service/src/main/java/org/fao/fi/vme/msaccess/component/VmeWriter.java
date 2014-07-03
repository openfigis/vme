package org.fao.fi.vme.msaccess.component;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.msaccess.model.ObjectCollection;

/**
 * Writes the data to the vme DB.
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeWriter {
	@Inject
	TableWriter tableWriter;

	@Inject
	VmeDao4Msaccess vmeDao4Msaccess;

	public void persistNew(List<ObjectCollection> objectCollectionList) {
		vmeDao4Msaccess.persistObjectCollection(objectCollectionList);
	}
	
	public void persistNew(Object object) {
		vmeDao4Msaccess.persistObject(object);
	}

}
