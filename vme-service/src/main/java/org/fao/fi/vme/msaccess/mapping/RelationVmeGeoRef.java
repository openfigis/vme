package org.fao.fi.vme.msaccess.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.msaccess.model.ObjectCollection;

/**
 * This relation is in the vme table itself, therefore it can not be established in the table itself.
 * 
 * @author vaningen
 * 
 */
public class RelationVmeGeoRef {

	public void correct(List<ObjectCollection> objectCollectionList) {
		for (ObjectCollection objectCollection : objectCollectionList) {
			if (objectCollection.getClazz().equals(Vme.class)) {
				workToDo(objectCollection);
				// checks
				postConditionCheck(objectCollection);

			}
		}
	}

	private void postConditionCheck(ObjectCollection objectCollection) {
		List<Object> object = objectCollection.getObjectList();
		Set<String> set = new HashSet<String>();
		for (Object found : object) {
			Vme vme = (Vme) found;
			List<GeoRef> l = vme.getGeoRefList();
			for (GeoRef geoRef : l) {
				if (set.contains(geoRef.getGeographicFeatureID())) {
					throw new VmeException("Double found : " + geoRef.getGeographicFeatureID());
				}
				set.add(geoRef.getGeographicFeatureID());
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
		// remove the doubles vme's
		for (Object object : doubles) {
			objectCollection.getObjectList().remove(object);
		}

		// remove the double georefs.
		Map<String, GeoRef> geoRefmap = new HashMap<String, GeoRef>();
		for (Object object : vmeList) {
			List<GeoRef> doubleGeoRefs = new ArrayList<GeoRef>();
			List<GeoRef> list = ((Vme) object).getGeoRefList();
			for (GeoRef geoRef : list) {
				if (geoRefmap.containsKey(geoRef.getGeographicFeatureID())) {
					doubleGeoRefs.add(geoRef);
				} else {
					geoRefmap.put(geoRef.getGeographicFeatureID(), geoRef);
				}
			}
			for (GeoRef geoRef : doubleGeoRefs) {
				((Vme) object).getGeoRefList().remove(geoRef);
			}
		}

	}

	private void mergeVme(Vme vme, Vme vmeTarget) {
		if (vme.getGeoRefList() != null && vme.getGeoRefList().size() == 1) {
			vmeTarget.getGeoRefList().add(vme.getGeoRefList().get(0));
		}
	}

}
