package org.fao.fi.vme.msaccess.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.model.ObjectCollection;

/**
 * This relation is in the vme table itself, therefore it can not be established
 * in the table itself.
 * 
 * @author vaningen
 * 
 */
public class RelationVmeGeoRef {

	public void correct(List<ObjectCollection> objectCollectionList) {
		for (ObjectCollection objectCollection : objectCollectionList) {
			if (objectCollection.getClazz().equals(Vme.class)) {

				// report(objectCollection);

				workToDo(objectCollection);

				// set the vme on the GeoRef;
				setVmeOnGeoRef(objectCollection);

				// checks
				postConditionCheck(objectCollection);

				// report(objectCollection);

			}
		}
	}

	// private void report(ObjectCollection objectCollection) {
	// List<Object> objects = objectCollection.getObjectList();
	// Set<GeoRef> georefs = new HashSet<GeoRef>();
	// int i = 0;
	// for (Object object : objects) {
	// Vme vme = (Vme) object;
	// List<GeoRef> l = vme.getGeoRefList();
	// for (GeoRef geoRef : l) {
	// georefs.add(geoRef);
	// i++;
	// }
	// }
	// System.out.println(i + " was the total and the set size is " +
	// georefs.size());
	//
	// }

	private void setVmeOnGeoRef(ObjectCollection objectCollection) {
		List<Object> object = objectCollection.getObjectList();
		for (Object found : object) {
			Vme vme = (Vme) found;
			List<GeoRef> l = vme.getGeoRefList();
			for (GeoRef geoRef : l) {
				geoRef.setVme(vme);
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

				if (set.contains(geoRef.getGeographicFeatureID() + geoRef.getYear())) {
					throw new VmeException("Double found : " + geoRef.getGeographicFeatureID());
				}
				set.add(geoRef.getGeographicFeatureID() + geoRef.getYear());
				if (geoRef.getVme() == null) {
					throw new VmeException("GeoRef without Vme found");
				}

			}
			if (vme.getGeoRefList().size() < 1) {
				throw new VmeException("Every Vme should have 1 or more GeoRefs, right? Number is : "
						+ vme.getGeoRefList().size());
			}
		}

	}

	/**
	 * the 1 to many relation in the ms access table for vme- georef was
	 * implicit in the rows.
	 * 
	 * 
	 * @param objectCollection
	 */
	private void workToDo(ObjectCollection objectCollection) {
		List<Object> vmeList = objectCollection.getObjectList();
		List<Vme> doubles = new ArrayList<Vme>();
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
		for (Vme vme : doubles) {
			objectCollection.getObjectList().remove(vme);
		}

	}

	private void mergeVme(Vme vme, Vme vmeTarget) {
		if (vme.getGeoRefList() != null && vme.getGeoRefList().size() == 1) {
			vmeTarget.getGeoRefList().add(vme.getGeoRefList().get(0));
		}
	}

}
