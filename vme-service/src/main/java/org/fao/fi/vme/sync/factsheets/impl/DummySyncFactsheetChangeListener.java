/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.impl;

import javax.enterprise.inject.Alternative;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 20 Feb 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 20 Feb 2014
 */
@Alternative
public class DummySyncFactsheetChangeListener extends AbstractSyncFactsheetChangeListener {
	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#doCreateFactsheets(java.lang.Long[])
	 */
	@Override
	protected void doCreateFactsheets(Long... factsheetIDs) throws Exception {
		LOG.info("Creating factsheets for {} VMEs with ID {}", factsheetIDs.length, this.serializeIDs(factsheetIDs));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#doUpdateFactsheets(java.lang.Long[])
	 */
	@Override
	protected void doUpdateFactsheets(Long... factsheetIDs) throws Exception {
		LOG.info("Updating factsheets for {} VMEs with ID {}", factsheetIDs.length, this.serializeIDs(factsheetIDs));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#doDeleteFactsheets(java.lang.Long[])
	 */
	@Override
	protected void doDeleteFactsheets(Long... factsheetIDs) throws Exception {
		LOG.info("Deleting factsheets for {} VMEs with ID {}", factsheetIDs.length, this.serializeIDs(factsheetIDs));
	}
}