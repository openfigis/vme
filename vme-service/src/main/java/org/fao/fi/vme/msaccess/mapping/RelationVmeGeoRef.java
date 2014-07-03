package org.fao.fi.vme.msaccess.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This relation is in the vme table itself, therefore it can not be established
 * in the table itself.
 * 
 * @author Erik van Ingen
 * 
 */
public class RelationVmeGeoRef {

	private static final Logger LOG = LoggerFactory.getLogger(RelationVmeGeoRef.class);

	public void correct(List<ObjectCollection> objectCollectionList) {
		for (ObjectCollection objectCollection : objectCollectionList) {
			if (objectCollection.getClazz().equals(Vme.class)) {

				work(objectCollection);

				setVme(objectCollection);

				postConditionCheck(objectCollection);

			}
		}
	}

	private void setVme(ObjectCollection objectCollection) {
		List<Object> object = objectCollection.getObjectList();
		for (Object found : object) {
			Vme vme = (Vme) found;
			List<GeoRef> l = vme.getGeoRefList();
			for (GeoRef geoRef : l) {
				geoRef.setVme(vme);
			}
			List<Profile> pl = vme.getProfileList();
			if (pl != null) {
				for (Profile profile : pl) {
					profile.setVme(vme);
				}
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
	private void work(ObjectCollection objectCollection) {
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

		// remove the doubles

		for (Object object : vmeList) {
			List<GeoRef> doubleGeoRefs = new ArrayList<GeoRef>();
			List<GeoRef> list = ((Vme) object).getGeoRefList();
			Map<String, GeoRef> geoRefmap = new HashMap<String, GeoRef>();
			for (GeoRef geoRef : list) {
				if (geoRefmap.containsKey(geoRef.getGeographicFeatureID() + geoRef.getYear())) {
					// this case should not happen in the Acces DB.
					LOG.error("double georefs should not appear in the Acces DB, featureId = "
							+ geoRef.getGeographicFeatureID());
					doubleGeoRefs.add(geoRef);
				} else {
					geoRefmap.put(geoRef.getGeographicFeatureID() + geoRef.getYear(), geoRef);
				}
			}
			for (GeoRef geoRef : doubleGeoRefs) {
				((Vme) object).getGeoRefList().remove(geoRef);
			}
		}

	}

	private void mergeVme(Vme vme, Vme vmeTarget) {
		if (vme.getGeoRefList().size() != 1 || vme.getGeoRefList().size() != 1) {
			throw new VmeException("Vme has always 1 georef at this stage");
		}

		vmeTarget.getGeoRefList().add(vme.getGeoRefList().get(0));
		if (vme.getProfileList() != null) {
			vmeTarget.getProfileList().add(vme.getProfileList().get(0));
		}
	}
}
