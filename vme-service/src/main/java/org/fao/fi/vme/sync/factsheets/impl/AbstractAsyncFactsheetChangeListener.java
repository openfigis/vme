/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.sync.factsheets.FactsheetChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
abstract public class AbstractAsyncFactsheetChangeListener implements FactsheetChangeListener {
	static final protected Logger LOG = LoggerFactory.getLogger(AbstractAsyncFactsheetChangeListener.class);
		
	final static public int MAX_THREADS_IN_POOL = 64;

	final private ExecutorCompletionService<Void> _executorQueue = new ExecutorCompletionService<Void>(Executors.newFixedThreadPool(MAX_THREADS_IN_POOL));
	
	final private AbstractAsyncFactsheetChangeListener $this = this;
	
	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#VMEChanged(org.fao.fi.vme.domain.model.Vme[])
	 */
	@Override
	final public void VMEChanged(final Vme... changed) throws Exception {
		LOG.info("Notified of changes to {} elements of type Vme", ( changed == null ? "NULL" : changed.length));
		
		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleVmeChange(changed);
				
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#VMEAdded(org.fao.fi.vme.domain.model.Vme[])
	 */
	@Override
	final public void VMEAdded(final Vme... added) throws Exception {
		LOG.info("Notified of additions of {} elements of type Vme", ( added == null ? "NULL" : added.length));	
		
		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleVmeAddition(added);
				
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#VMEDeleted(org.fao.fi.vme.domain.model.Vme[])
	 */
	@Override
	final public void VMEDeleted(final Vme... deleted) throws Exception {
		LOG.info("Notified of deletions of {} elements of type Vme", ( deleted == null ? "NULL" : deleted.length));	
		
		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleVmeDeletion(deleted);
				
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#generalMeasureChanged(org.fao.fi.vme.domain.model.GeneralMeasure[])
	 */
	@Override
	final public void generalMeasureChanged(final GeneralMeasure... changed) throws Exception {
		LOG.info("Notified of changes to {} elements of type GeneralMeasure", ( changed == null ? "NULL" : changed.length));
		
		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleGeneralMeasureChange(changed);
				
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#generalMeasureAdded(org.fao.fi.vme.domain.model.GeneralMeasure[])
	 */
	@Override
	final public void generalMeasureAdded(final GeneralMeasure... added) throws Exception {
		LOG.info("Notified of additions of {} elements of type GeneralMeasure", ( added == null ? "NULL" : added.length));
		
		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleGeneralMeasureAddition(added);
				
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#generalMeasureDeleted(org.fao.fi.vme.domain.model.GeneralMeasure[])
	 */
	@Override
	final public void generalMeasureDeleted(final GeneralMeasure... deleted) throws Exception {
		LOG.info("Notified of deletions of {} elements of type GeneralMeasure", ( deleted == null ? "NULL" : deleted.length));
		
		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleGeneralMeasureDeletion(deleted);
				
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#informationSourceChanged(org.fao.fi.vme.domain.model.InformationSource[])
	 */
	@Override
	final public void informationSourceChanged(final InformationSource... changed) throws Exception {
		LOG.info("Notified of changes to {} elements of type InformationSource", ( changed == null ? "NULL" : changed.length));

		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleInformationSourceChange(changed);
				
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#informationSourceAdded(org.fao.fi.vme.domain.model.InformationSource[])
	 */
	@Override
	final public void informationSourceAdded(final InformationSource... added) throws Exception {
		LOG.info("Notified of additions of {} elements of type InformationSource", ( added == null ? "NULL" : added.length));
		
		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleInformationSourceAddition(added);
				
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#informationSourceDeleted(org.fao.fi.vme.domain.model.InformationSource[])
	 */
	@Override
	final public void informationSourceDeleted(final InformationSource... deleted) throws Exception {
		LOG.info("Notified of deletions of {} elements of type InformationSource", ( deleted == null ? "NULL" : deleted.length));
		
		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleInformationSourceDeletion(deleted);
				
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#fishingFootprintChanged(org.fao.fi.vme.domain.model.extended.FisheryAreasHistory[])
	 */
	@Override
	final public void fishingFootprintChanged(final FisheryAreasHistory... changed) throws Exception {
		LOG.info("Notified of changes to {} elements of type FisheryAreasHistory", ( changed == null ? "NULL" : changed.length));
		
		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleFishingFootprintChange(changed);
				
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#fishingFootprintAdded(org.fao.fi.vme.domain.model.extended.FisheryAreasHistory[])
	 */
	@Override
	final public void fishingFootprintAdded(final FisheryAreasHistory... added) throws Exception {
		LOG.info("Notified of additions of {} elements of type FisheryAreasHistory", ( added == null ? "NULL" : added.length));
		
		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleFishingFootprintAddition(added);
				
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#fishingFootprintDeleted(org.fao.fi.vme.domain.model.extended.FisheryAreasHistory[])
	 */
	@Override
	final public void fishingFootprintDeleted(final FisheryAreasHistory... deleted) throws Exception {
		LOG.info("Notified of deletions of {} elements of type FisheryAreasHistory", ( deleted == null ? "NULL" : deleted.length));
		
		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleFishingFootprintDeletion(deleted);
				
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#regionalHistoryChanged(org.fao.fi.vme.domain.model.extended.VMEsHistory[])
	 */
	@Override
	final public void regionalHistoryChanged(final VMEsHistory... changed) throws Exception {
		LOG.info("Notified of changes to {} elements of type VMEsHistory", ( changed == null ? "NULL" : changed.length));

		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleRegionalHistoryChange(changed);
				
				return null;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#regionalHistoryAdded(org.fao.fi.vme.domain.model.extended.VMEsHistory[])
	 */
	@Override
	final public void regionalHistoryAdded(final VMEsHistory... added) throws Exception {
		LOG.info("Notified of additions of {} elements of type VMEsHistory", ( added == null ? "NULL" : added.length));
		
		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleRegionalHistoryAddition(added);
			
				return null;
			}
		});		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#regionalHistoryDeleted(org.fao.fi.vme.domain.model.extended.VMEsHistory[])
	 */
	@Override
	final public void regionalHistoryDeleted(final VMEsHistory... deleted) throws Exception {
		LOG.info("Notified of deletions of {} elements of type VMEsHistory", ( deleted == null ? "NULL" : deleted.length));	

		this._executorQueue.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				$this.handleRegionalHistoryDeletion(deleted);
			
				return null;
			}
		});
	}
	
	abstract protected void handleVmeAddition(Vme... added) throws Exception;
	abstract protected void handleVmeChange(Vme... changed) throws Exception;
	abstract protected void handleVmeDeletion(Vme... deleted) throws Exception;
	
	abstract protected void handleGeneralMeasureAddition(GeneralMeasure... added) throws Exception;
	abstract protected void handleGeneralMeasureChange(GeneralMeasure... changed) throws Exception;
	abstract protected void handleGeneralMeasureDeletion(GeneralMeasure... deleted) throws Exception;
	
	abstract protected void handleInformationSourceAddition(InformationSource... added) throws Exception;
	abstract protected void handleInformationSourceChange(InformationSource... changed) throws Exception;
	abstract protected void handleInformationSourceDeletion(InformationSource... deleted) throws Exception;
	
	abstract protected void handleFishingFootprintAddition(FisheryAreasHistory... added) throws Exception;
	abstract protected void handleFishingFootprintChange(FisheryAreasHistory... changed) throws Exception;
	abstract protected void handleFishingFootprintDeletion(FisheryAreasHistory... deleted) throws Exception;
	
	abstract protected void handleRegionalHistoryAddition(VMEsHistory... added) throws Exception;
	abstract protected void handleRegionalHistoryChange(VMEsHistory... changed) throws Exception;
	abstract protected void handleRegionalHistoryDeletion(VMEsHistory... deleted) throws Exception;

}
