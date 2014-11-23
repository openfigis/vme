package org.fao.fi.vme.batch.sync2;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.Lang;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;

/**
 * 
 * Map the REF_VME and the FS_OBSERVATION
 * 
 * TODO extract isolate code from GeoRefSync and VmeRefSync
 * 
 * 
 * @author Erik van Ingen
 * 
 */

public class VmeRefSync implements Sync {

	@Inject
	FigisDao figisDao;

	@Inject
	VmeDao vmeDao;

	@Override
	public void sync() {
		List<Vme> objects = vmeDao.loadVmes();
		for (Vme vme : objects) {
			sync(vme);

		}
		deleteOld();
	}

	/**
	 * Delete all those references which do not appear in the VME DB
	 */
	/*
	 * Simplified below method in order to avoid below errors: *
	 * 
	 * The logic was completely valid anyway. It is done to understand the problem better.
	 * 
	 * 
	 * [ ERROR ] { org.fao.fi.vme.sync.factsheets.listeners.impl.AsyncFactsheetChangeListener$2 } - Unable to
	 * asynchronously update factsheet for VME with ID 24053 org.hibernate.AssertionFailure: collection was processed
	 * twice by flush() at org.hibernate.engine.internal.Collections.prepareCollectionForUpdate(Collections.java:223) at
	 * at org.hibernate.jpa.internal.QueryImpl.getResultList(QueryImpl.java:449) at
	 * org.vme.dao.impl.AbstractJPADao.selectFrom(AbstractJPADao.java:124) at
	 * org.vme.dao.impl.AbstractJPADao.loadObjects(AbstractJPADao.java:128) at
	 * org.vme.dao.sources.vme.VmeDao.loadObjects(VmeDao.java:133) at
	 * org.fao.fi.vme.batch.sync2.VmeRefSync.deleteOld(VmeRefSync.java:50) at
	 * org.fao.fi.vme.batch.sync2.VmeRefSync.sync(VmeRefSync.java:82) at
	 * org.fao.fi.vme.batch.sync2.SyncBatch2.syncFigisWithVme(SyncBatch2.java:52) at
	 * org.fao.fi.vme.sync.factsheets.updaters
	 * .impl.FigisFactsheetUpdater.updateFactsheets(FigisFactsheetUpdater.java:74) at
	 * org.fao.fi.vme.sync.factsheets.listeners
	 * .impl.AsyncFactsheetChangeListener$2.call(AsyncFactsheetChangeListener.java:73) at
	 * org.fao.fi.vme.sync.factsheets.
	 * listeners.impl.AsyncFactsheetChangeListener$2.call(AsyncFactsheetChangeListener.java:69)
	 */
	private void deleteOld() {
		List<RefVme> refVmeList = figisDao.loadObjects(RefVme.class);
		for (RefVme refVme : refVmeList) {
			if (vmeDao.findVme(refVme.getId()) == null) {
				figisDao.remove(refVme);
			}
		}
	}

	@Override
	public void sync(Vme vme) {
		RefVme object = (RefVme) figisDao.find(RefVme.class, vme.getId());

		if (object != null && object.getId() <= 0) {
			throw new VmeException("object found in DB withough id");
		}
		if (object == null) {
			// do the new stuff
			object = generateNewRefVme();

			// map it
			map(vme, object);

			// and store it
			figisDao.persist(object);
		} else {
			map(vme, object);
			figisDao.merge(object);
		}
		deleteOld();
	}

	private RefVme generateNewRefVme() {
		RefVme r = new RefVme();

		return r;
	}

	private void map(Vme vme, RefVme object) {
		object.setId(vme.getId());
		object.setMeta(Figis.META_VME);
		object.setInventoryId(vme.getInventoryIdentifier());
		if (vme.getName() != null && vme.getName().getStringMap() != null) {
			object.setNameA(vme.getName().getStringMap().get(Lang.AR));
			object.setNameC(vme.getName().getStringMap().get(Lang.ZH));
			object.setNameE(vme.getName().getStringMap().get(Lang.EN));
			object.setNameF(vme.getName().getStringMap().get(Lang.FR));
			object.setNameR(vme.getName().getStringMap().get(Lang.RU));
			object.setNameS(vme.getName().getStringMap().get(Lang.ES));
		}
	}
}
