/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.listeners.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.sync.factsheets.listeners.FactsheetChangeListener;
import org.fao.fi.vme.sync.factsheets.updaters.FactsheetUpdater;
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
 * @since 
 * 20 Feb 2014
 */
@Alternative
public class SyncFactsheetChangeListener implements FactsheetChangeListener {
	static final protected Logger LOG = LoggerFactory.getLogger(SyncFactsheetChangeListener.class);
	
	protected @Inject FactsheetUpdater _updater;
	
	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#VMEChanged(org.fao.fi.vme.domain.model.Vme[])
	 */
	@Override
	final public void VMEChanged(final Vme... changed) throws Exception {
		LOG.info("Notified of changes to {} elements of type Vme", ( changed == null ? "NULL" : changed.length));
		
		this.updateFactsheets(this.findVMEIDs(changed));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#VMEAdded(org.fao.fi.vme.domain.model.Vme[])
	 */
	@Override
	final public void VMEAdded(final Vme... added) throws Exception {
		LOG.info("Notified of additions of {} elements of type Vme", ( added == null ? "NULL" : added.length));	
		
		this.createFactsheets(this.findVMEIDs(added));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#VMEDeleted(org.fao.fi.vme.domain.model.Vme[])
	 */
	@Override
	final public void VMEDeleted(final Vme... deleted) throws Exception {
		LOG.info("Notified of deletions of {} elements of type Vme", ( deleted == null ? "NULL" : deleted.length));	
		
		this.deleteFactsheets(this.findVMEIDs(deleted));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#generalMeasureChanged(org.fao.fi.vme.domain.model.GeneralMeasure[])
	 */
	@Override
	final public void generalMeasureChanged(final GeneralMeasure... changed) throws Exception {
		LOG.info("Notified of changes to {} elements of type GeneralMeasure", ( changed == null ? "NULL" : changed.length));
		
		this.updateFactsheets(this.findVMEIDs(changed));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#generalMeasureAdded(org.fao.fi.vme.domain.model.GeneralMeasure[])
	 */
	@Override
	final public void generalMeasureAdded(final GeneralMeasure... added) throws Exception {
		LOG.info("Notified of additions of {} elements of type GeneralMeasure", ( added == null ? "NULL" : added.length));
		
		this.createFactsheets(this.findVMEIDs(added));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#generalMeasureDeleted(org.fao.fi.vme.domain.model.GeneralMeasure[])
	 */
	@Override
	final public void generalMeasureDeleted(final GeneralMeasure... deleted) throws Exception {
		LOG.info("Notified of deletions of {} elements of type GeneralMeasure", ( deleted == null ? "NULL" : deleted.length));
		
		this.deleteFactsheets(this.findVMEIDs(deleted));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#informationSourceChanged(org.fao.fi.vme.domain.model.InformationSource[])
	 */
	@Override
	final public void informationSourceChanged(final InformationSource... changed) throws Exception {
		LOG.info("Notifiying listeners of changes to {} elements of type InformationSource", ( changed == null ? "NULL" : changed.length));

		this.updateFactsheets(this.findVMEIDs(changed));

		LOG.info("Notified of changes to {} elements of type InformationSource", ( changed == null ? "NULL" : changed.length));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#informationSourceAdded(org.fao.fi.vme.domain.model.InformationSource[])
	 */
	@Override
	final public void informationSourceAdded(final InformationSource... added) throws Exception {
		LOG.info("Notified of additions of {} elements of type InformationSource", ( added == null ? "NULL" : added.length));
		
		this.createFactsheets(this.findVMEIDs(added));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#informationSourceDeleted(org.fao.fi.vme.domain.model.InformationSource[])
	 */
	@Override
	final public void informationSourceDeleted(final InformationSource... deleted) throws Exception {
		LOG.info("Notified of deletions of {} elements of type InformationSource", ( deleted == null ? "NULL" : deleted.length));
		
		this.deleteFactsheets(this.findVMEIDs(deleted));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#fishingFootprintChanged(org.fao.fi.vme.domain.model.extended.FisheryAreasHistory[])
	 */
	@Override
	final public void fishingFootprintChanged(final FisheryAreasHistory... changed) throws Exception {
		LOG.info("Notified of changes to {} elements of type FisheryAreasHistory", ( changed == null ? "NULL" : changed.length));
		
		this.updateFactsheets(this.findVMEIDs(changed));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#fishingFootprintAdded(org.fao.fi.vme.domain.model.extended.FisheryAreasHistory[])
	 */
	@Override
	final public void fishingFootprintAdded(final FisheryAreasHistory... added) throws Exception {
		LOG.info("Notified of additions of {} elements of type FisheryAreasHistory", ( added == null ? "NULL" : added.length));
		
		this.createFactsheets(this.findVMEIDs(added));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#fishingFootprintDeleted(org.fao.fi.vme.domain.model.extended.FisheryAreasHistory[])
	 */
	@Override
	final public void fishingFootprintDeleted(final FisheryAreasHistory... deleted) throws Exception {
		LOG.info("Notified of deletions of {} elements of type FisheryAreasHistory", ( deleted == null ? "NULL" : deleted.length));
		
		this.deleteFactsheets(this.findVMEIDs(deleted));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#regionalHistoryChanged(org.fao.fi.vme.domain.model.extended.VMEsHistory[])
	 */
	@Override
	final public void regionalHistoryChanged(final VMEsHistory... changed) throws Exception {
		LOG.info("Notified of changes to {} elements of type VMEsHistory", ( changed == null ? "NULL" : changed.length));

		this.updateFactsheets(this.findVMEIDs(changed));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#regionalHistoryAdded(org.fao.fi.vme.domain.model.extended.VMEsHistory[])
	 */
	@Override
	final public void regionalHistoryAdded(final VMEsHistory... added) throws Exception {
		LOG.info("Notified of additions of {} elements of type VMEsHistory", ( added == null ? "NULL" : added.length));
		
		this.createFactsheets(this.findVMEIDs(added));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#regionalHistoryDeleted(org.fao.fi.vme.domain.model.extended.VMEsHistory[])
	 */
	@Override
	final public void regionalHistoryDeleted(final VMEsHistory... deleted) throws Exception {
		LOG.info("Notified of deletions of {} elements of type VMEsHistory", ( deleted == null ? "NULL" : deleted.length));	

		this.deleteFactsheets(this.findVMEIDs(deleted));
	}
	
	protected void createFactsheets(final Long[] vmeIDs) {
		if(vmeIDs == null || vmeIDs.length == 0) {
			LOG.warn("Unable to create factsheets for a NULL or empty set of VME IDs");
		} else {
			LOG.info("Asynchronously creating factsheets for {} VMEs with ID {}", vmeIDs.length, this.serializeIDs(vmeIDs));
			
			for(final Long id : vmeIDs) {
				if(id != null) {
					try {
						this._updater.createFactsheets(id);
						
						LOG.info("Synchronously created factsheet for VME with ID {}", id);
					} catch(Throwable t) {
						LOG.error("Unable to synchronously create factsheet for VME with ID {}: {}", id, t.getMessage(), t);
					}
				} else {
					LOG.warn("Unable to create factsheet for NULL VME ID");
				}
			}
		}
	}
	
	protected void updateFactsheets(final Long[] vmeIDs) {
		if(vmeIDs == null || vmeIDs.length == 0) {
			LOG.warn("Unable to update factsheets for a NULL or empty set of VME IDs");
		} else {
			LOG.info("Asynchronously updating factsheets for {} VMEs with ID {}", vmeIDs.length, this.serializeIDs(vmeIDs));
			
			for(final Long id : vmeIDs) {
				if(id != null) {
					try {
						this._updater.updateFactsheets(id);
						
						LOG.info("Synchronously updated factsheet for VME with ID {}", id);
					} catch(Throwable t) {
						LOG.error("Unable to synchronously update factsheet for VME with ID {}: {}", id, t.getMessage(), t);
					}
				} else {
					LOG.warn("Unable to update factsheet for NULL VME ID");
				}
			}
		}
	}
	
	protected void deleteFactsheets(final Long[] vmeIDs) {
		if(vmeIDs == null || vmeIDs.length == 0) {
			LOG.warn("Unable to delete factsheets for a NULL or empty set of VME IDs");
		} else {
			LOG.info("Asynchronously deleting factsheets for {} VMEs with ID {}", vmeIDs.length, this.serializeIDs(vmeIDs));
			
			for(final Long id : vmeIDs) {
				if(id != null) {
					try {
						this._updater.deleteFactsheets(id);
						
						LOG.info("Synchronously deleted factsheet for VME with ID {}", id);
					} catch(Throwable t) {
						LOG.error("Unable to synchronously delete factsheet for VME with ID {}: {}", id, t.getMessage(), t);
					}
				} else {
					LOG.warn("Unable to delete factsheet for NULL VME ID");
				}
			}
		}
	}
		
	final protected Long[] findVMEIDs(Vme... vmes) {
		Collection<Long> IDs = new HashSet<Long>();
		
		for(Vme in : vmes) {
			if(in != null && in.getId() != null)
				IDs.add(in.getId());
		}
		
		return IDs.toArray(new Long[IDs.size()]);
	}
	
	final protected Long[] findVMEIDs(GeneralMeasure... generalMeasures) {
		return this.findVMEIDs(this.findRFMOs(generalMeasures));
	}
	
	final protected Long[] findVMEIDs(InformationSource... informationSources) {
		return this.findVMEIDs(this.findRFMOs(informationSources));
	}
	
	final protected Long[] findVMEIDs(FisheryAreasHistory... fishingFootprints) {
		return this.findVMEIDs(this.findRFMOs(fishingFootprints));
	}
	
	final protected Long[] findVMEIDs(VMEsHistory... regionalHistories) {
		return this.findVMEIDs(this.findRFMOs(regionalHistories));
	}
	
	final protected Long[] findVMEIDs(Rfmo... RFMOs) {
		Collection<Long> IDs = new HashSet<Long>();
				
		for(Rfmo authority : RFMOs) {
			if(authority != null && authority.getListOfManagedVmes() != null)
				for(Vme vme : authority.getListOfManagedVmes()) {
					if(vme.getId() != null)
						IDs.add(vme.getId());
				}
		}
		
		LOG.info("IDs for VMEs belonging to {} are: {}", this.serializeIDs(RFMOs), this.serializeIDs(IDs.toArray(new Object[IDs.size()])));
		
		return IDs.toArray(new Long[IDs.size()]);
	}
	
	final protected Rfmo[] findRFMOs(GeneralMeasure... generalMeasures) {
		Collection<Rfmo> RFMOs = new ArrayList<Rfmo>();
		
		for(GeneralMeasure gm : generalMeasures) {
			if(gm != null && gm.getRfmo() != null)
				RFMOs.add(gm.getRfmo());
		}
		
		LOG.info("RFMOs owning GeneralMeasures {} are: {}", this.serializeIDs(generalMeasures), this.serializeIDs(RFMOs.toArray(new Rfmo[RFMOs.size()])));
		
		return RFMOs.toArray(new Rfmo[RFMOs.size()]);
	}
	
	final protected Rfmo[] findRFMOs(InformationSource... informationSources) {
		Collection<Rfmo> RFMOs = new ArrayList<Rfmo>();
		
		for(InformationSource is : informationSources) {
			if(is != null && is.getRfmo() != null)
				RFMOs.add(is.getRfmo());
		}
		
		LOG.info("RFMOs owning InformationSources {} are: {}", this.serializeIDs(informationSources), this.serializeIDs(RFMOs.toArray(new Rfmo[RFMOs.size()])));
		
		return RFMOs.toArray(new Rfmo[RFMOs.size()]);
	}
	
	final protected Rfmo[] findRFMOs(FisheryAreasHistory... fishingFootprints) {
		Collection<Rfmo> RFMOs = new ArrayList<Rfmo>();
		
		for(FisheryAreasHistory ff : fishingFootprints) {
			if(ff != null && ff.getRfmo() != null)
				RFMOs.add(ff.getRfmo());
		}
		
		LOG.info("RFMOs owning FishingFootprints {} are: {}", this.serializeIDs(fishingFootprints), this.serializeIDs(RFMOs.toArray(new Rfmo[RFMOs.size()])));
		
		return RFMOs.toArray(new Rfmo[RFMOs.size()]);
	}
	
	final protected Rfmo[] findRFMOs(VMEsHistory... regionalHistories) {
		Collection<Rfmo> RFMOs = new ArrayList<Rfmo>();
		
		for(VMEsHistory rh : regionalHistories) {
			if(rh != null && rh.getRfmo() != null)
				RFMOs.add(rh.getRfmo());
		}
		
		LOG.info("RFMOs owning RegionalHistories {} are: {}", this.serializeIDs(regionalHistories), this.serializeIDs(RFMOs.toArray(new Rfmo[RFMOs.size()])));
		
		return RFMOs.toArray(new Rfmo[RFMOs.size()]);
	}
	
	final protected String serializeIDs(Object[] IDs) {
		StringBuilder result = new StringBuilder("[ ");
		
		for(Object in : IDs) {
			result.append(in).append(", ");
		}
		
		result.append("]");
		
		return result.toString().replaceAll("\\, \\]$", " ]");
	}
	
	final protected String serializeIDs(ObjectId<?>[] objects) {
		Collection<Object> IDs = new HashSet<Object>();
		
		for(ObjectId<?> in : objects)
			if(in != null && in.getId() != null)
				IDs.add(in.getId());
		
		return this.serializeIDs(IDs.toArray(new Object[IDs.size()]));
	}
	
	final protected String serializeIDs(Rfmo[] rfmos) {
		Collection<Object> IDs = new HashSet<Object>();
		
		for(Rfmo in : rfmos)
			if(in != null && in.getId() != null)
				IDs.add(in.getId());
		
		return this.serializeIDs(IDs.toArray(new Object[IDs.size()]));
	}
}