/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.impl;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.fao.fi.vme.batch.sync2.SyncBatch2;
import org.fao.fi.vme.domain.model.Vme;
import org.vme.dao.sources.vme.VmeDao;

/**
 * Place your class / interface description here.
 * 
 * History:
 * 
 * ------------- --------------- ----------------------- Date Author Comment
 * ------------- --------------- ----------------------- 20 Feb 2014 Fiorellato
 * Creation.
 * 
 * @version 1.0
 * @since 20 Feb 2014
 */
@Alternative
public class FactsheetAsyncChangeListenerImpl extends AbstractSyncFactsheetChangeListener {

	@Inject
	VmeDao vmeDao;

	@Inject
	SyncBatch2 syncBatch2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener
	 * #doCreateFactsheets(java.lang.Long[])
	 */
	@Override
	protected void doCreateFactsheets(Long[] vmeIds) throws Exception {
		LOG.info("Creating factsheets for {} VMEs with ID {}", vmeIds.length, vmeIds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener
	 * #doUpdateFactsheets(java.lang.Long[])
	 */
	@Override
	protected void doUpdateFactsheets(Long[] vmeIds) throws Exception {
		for (Long vmeId : vmeIds) {
			Vme vme = vmeDao.findVme(vmeId);
			syncBatch2.syncFigisWithVme(vme);
		}
		LOG.info("Updating factsheets for {} VMEs with ID {}", vmeIds.length, vmeIds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener
	 * #doDeleteFactsheets(java.lang.Long[])
	 */
	@Override
	protected void doDeleteFactsheets(Long[] vmeIds) throws Exception {
		LOG.info("Deleting factsheets for {} VMEs with ID {}", vmeIds.length, vmeIds);
	}
}