/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

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
abstract public class AbstractAsyncFactsheetChangeListener extends AbstractSyncFactsheetChangeListener {
	static final private int MAX_THREADS_IN_POOL = 64;

	final private ExecutorCompletionService<Void> _executorQueue = new ExecutorCompletionService<Void>(Executors.newFixedThreadPool(MAX_THREADS_IN_POOL));
	
	final protected AbstractAsyncFactsheetChangeListener $this = this;
	
	final protected void createFactsheets(final Long[] vmeIDs) {
		if(vmeIDs == null || vmeIDs.length == 0) {
			LOG.warn("Unable to create factsheets for a NULL or empty set of VME IDs");
		} else {
			LOG.info("Asynchronously creating factsheets for {} VMEs with ID {}", vmeIDs.length, this.serializeIDs(vmeIDs));
			
			for(final Long id : vmeIDs) {
				if(id != null) {
					this._executorQueue.submit(new Callable<Void>() {
						@Override
						public Void call() throws Exception {
							$this.doCreateFactsheets(id);
						
							LOG.info("Asynchronously created factsheet for VME with ID {}", id);
							
							return null;
						}
					});
				} else {
					LOG.warn("Unable to create factsheet for NULL VME ID");
				}
			}
		}
	}
	
	final protected void updateFactsheets(final Long[] vmeIDs) {
		if(vmeIDs == null || vmeIDs.length == 0) {
			LOG.warn("Unable to update factsheets for a NULL or empty set of VME IDs");
		} else {
			LOG.info("Asynchronously updating factsheets for {} VMEs with ID {}", vmeIDs.length, this.serializeIDs(vmeIDs));
			
			for(final Long id : vmeIDs) {
				if(id != null) {
					this._executorQueue.submit(new Callable<Void>() {
						@Override
						public Void call() throws Exception {
							$this.doUpdateFactsheets(id);
						
							LOG.info("Asynchronously updated factsheet for VME with ID {}", id);
							
							return null;
						}
					});
				} else {
					LOG.warn("Unable to update factsheet for NULL VME ID");
				}
			}
		}
	}
	
	final protected void deleteFactsheets(final Long[] vmeIDs) {
		if(vmeIDs == null || vmeIDs.length == 0) {
			LOG.warn("Unable to delete factsheets for a NULL or empty set of VME IDs");
		} else {
			LOG.info("Asynchronously deleting factsheets for {} VMEs with ID {}", vmeIDs.length, this.serializeIDs(vmeIDs));
			
			for(final Long id : vmeIDs) {
				if(id != null) {
					this._executorQueue.submit(new Callable<Void>() {
						@Override
						public Void call() throws Exception {
							$this.doDeleteFactsheets(id);
						
							LOG.info("Asynchronously deleted factsheet for VME with ID {}", id);
							
							return null;
						}
					});
				} else {
					LOG.warn("Unable to delete factsheet for NULL VME ID");
				}
			}
		}
	}
}