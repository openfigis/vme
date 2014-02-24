/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.updaters.impl;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.fao.fi.vme.batch.sync2.SyncBatch2;
import org.fao.fi.vme.domain.model.Vme;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 21 Feb 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 21 Feb 2014
 */
@Alternative
public class FigisFactsheetUpdater extends AbstractFactsheetUpdater {
	@Inject
	SyncBatch2 syncBatch2;

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetUpdater#createFactsheets(java.lang.Long[])
	 */
	@Override
	public void createFactsheets(Long... vmeIDs) throws Exception {
		LOG.info("Creating factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);
		
		for (Long vmeId : vmeIDs) {
			Vme vme = vmeDao.findVme(vmeId);
			
			//This is necessary to reflect the persisted changes into the session...
			vmeDao.getEm().refresh(vme);
			
			syncBatch2.syncFigisWithVme(vme);
			
			this.updateCache(vmeId);
		}
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetUpdater#updateFactsheets(java.lang.Long[])
	 */
	@Override
	public void updateFactsheets(Long... vmeIDs) throws Exception {
		LOG.info("Updating factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);
		
		for (Long vmeId : vmeIDs) {
			Vme vme = vmeDao.findVme(vmeId);
			
			//This is necessary to reflect the persisted changes into the session...
			vmeDao.getEm().refresh(vme);
			
			syncBatch2.syncFigisWithVme(vme);
			
			this.updateCache(vmeId);
		}
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetUpdater#deleteFactsheets(java.lang.Long[])
	 */
	@Override
	public void deleteFactsheets(Long... vmeIDs) throws Exception {
		LOG.info("Deleting factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);
	}

}
