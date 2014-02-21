package org.fao.fi.vme.batch.sync2;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.RefWaterArea;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;

/**
 * 
 * Will the GeoRefs be taken from the geospatial tables? Will the GeoRefs be
 * filled by SyncBatch1?
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
	private FigisDao figisDao;

	@Inject
	private VmeDao vmeDao;

	private MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Override
	public void sync() {
		List<GeoRef> objects = (List<GeoRef>) vmeDao.loadObjects(GeoRef.class);
		for (GeoRef geoRef : objects) {

			syncGeoRef(geoRef);

		}
	}

	private void syncGeoRef(GeoRef geoRef) {
		// do the new stuff
		RefWaterArea object = generateNewRefWaterArea();

		// map it
		map(geoRef, object);

		// and sync it
		figisDao.syncRefWaterArea(geoRef.getGeographicFeatureID(), object);

	}

	@Override
	public void sync(Vme vme) {
		List<GeoRef> objects = vme.getGeoRefList();
		for (GeoRef geoRef : objects) {
			syncGeoRef(geoRef);
		}
	}

	private RefWaterArea generateNewRefWaterArea() {
		RefWaterArea r = new RefWaterArea();
		r.setMeta(Figis.META_WATER_AREA);
		return r;
	}

	private void map(GeoRef geoRef, RefWaterArea object) {
		object.setExternalId(geoRef.getGeographicFeatureID());
		if (geoRef.getVme() == null) {
			throw new VmeException("GeoRef.vme is null, is an inconsistent state. geoRef.id = " + geoRef.getId());
		}
		object.setName(u.getEnglish(geoRef.getVme().getName()));
	}

}
