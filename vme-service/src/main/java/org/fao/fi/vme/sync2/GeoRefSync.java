package org.fao.fi.vme.sync2;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.figis.domain.RefWaterArea;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.domain.GeoRef;

/**
 * 
 * Will the GeoRefs be taken from the geospatial tables? Will the GeoRefs be filled by SyncBatch1?
 * 
 * GeoRefSync pushes the water area refs from the vme DB to the figis DB.
 * 
 * 
 * TODO extract isolate code from GeoRefSync and VmeRefSync
 * 
 * @author Erik van Ingen
 * 
 */
public class GeoRefSync implements Sync {

	@Inject
	FigisDao figisDao;

	@Inject
	VmeDao vmeDao;

	@Override
	public void sync() {
		@SuppressWarnings("unchecked")
		List<GeoRef> objects = (List<GeoRef>) vmeDao.loadObjects(GeoRef.class);
		for (GeoRef geoRef : objects) {
			RefWaterArea object = (RefWaterArea) figisDao.find(RefWaterArea.class, geoRef.getId());

			if (object != null && object.getId() <= 0) {
				throw new VmeException("object found in DB withough id");
			}
			if (object == null) {
				// do the new stuff
				object = generateNewRefWaterArea();

				// map it
				map(geoRef, object);

				// and store it
				figisDao.persist(object);
			} else {
				map(geoRef, object);
				figisDao.merge(object);
			}
		}
	}

	private RefWaterArea generateNewRefWaterArea() {
		RefWaterArea r = new RefWaterArea();
		return r;
	}

	private void map(GeoRef geoRef, RefWaterArea object) {
		object.setId(geoRef.getId());
		object.setMeta(Figis.META_WATER_AREA);
		object.setExternalId(geoRef.getGeographicFeatureID());
	}

}
