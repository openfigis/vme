package org.fao.fi.vme.msaccess.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.msaccess.model.ObjectCollection;

public class RelationVmeGeoRef {

	public void correct(List<ObjectCollection> objectCollectionList) {
		for (ObjectCollection objectCollection : objectCollectionList) {
			if (objectCollection.getClazz().equals(Vme.class)) {
				workToDo(objectCollection);
			}
		}
	}

	/**
	 * the 1 to many relation in the ms access table for vme- georef was implicit in the rows.
	 * 
	 * 
	 * @param objectCollection
	 */
	private void workToDo(ObjectCollection objectCollection) {
		List<Object> vmeList = objectCollection.getObjectList();
		List<Object> doubles = new ArrayList<Object>();
		Map<String, Vme> vmeMap = new HashMap<String, Vme>();
		for (Object object : vmeList) {
			Vme vme = (Vme) object;
			
			String key = vme.getInventoryIdentifier();
			if (vmeMap.containsKey(key)) {
				Vme vmeTarget = vmeMap.get(key);
				mergeVme(vme, vmeTarget);
				doubles.add(vme);
			} else {
				vmeMap.put(key, vme);
			}
		}
		// remove the doubles
		for (Object object : doubles) {
			objectCollection.getObjectList().remove(object);
		}

	}

	private void mergeVme(Vme vme, Vme vmeTarget) {
		if(vme.getGeoRefList() != null && vme.getGeoRefList().size() ==1){
			vmeTarget.getGeoRefList().add(vme.getGeoRefList().get(0));
		}
	}

}
