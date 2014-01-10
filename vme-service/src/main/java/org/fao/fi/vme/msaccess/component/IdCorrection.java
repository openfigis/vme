package org.fao.fi.vme.msaccess.component;

import java.util.List;

import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.msaccess.model.ObjectCollection;

/**
 * The MS-Access DB gives a lot of id for the objects. Before saving it into the
 * RDBMS, these ids need to be deleted, in order to handle over this
 * responsibility to the RDBMS.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class IdCorrection {

	public void correct(List<ObjectCollection> objectCollectionList) {
		for (ObjectCollection objectCollection : objectCollectionList) {
			List<Object> objectList = objectCollection.getObjectList();
			for (Object object : objectList) {
				if (object instanceof ObjectId) {
					((ObjectId) object).setId(null);
				}
			}
		}
	}

}
