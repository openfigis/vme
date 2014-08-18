/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.listeners.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import org.fao.fi.vme.FactSheetChangeException;
import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.VMEsHistory;
import org.fao.fi.vme.domain.model.Vme;
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
	protected static final Logger LOG = LoggerFactory.getLogger(SyncFactsheetChangeListener.class);
	private static final String UPDATE_ERROR = "Something fail on updating data";
	private static final String ADD_ERROR = "Something fail on adding data" ;
	
	@Inject
	protected FactsheetUpdater updater;
	
	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#VMEChanged(org.fao.fi.vme.domain.model.Vme[])
	 */
	
	@Override
	public final void vmeChanged(final Vme... changed){
		LOG.info("Notified of changes to {} elements of type Vme", changed == null ? "NULL" : changed.length);
		
		for(Vme in : changed) {
			try {
				this.updater.refreshVme(in.getId());
			} catch (Exception e) {
				throw new FactSheetChangeException(UPDATE_ERROR ,e);
			}
		}
		
		this.updateFactsheets(this.findVMEIDs(changed));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#VMEAdded(org.fao.fi.vme.domain.model.Vme[])
	 */
	@Override
	public final void vmeAdded(final Vme... added){
		LOG.info("Notified of additions of {} elements of type Vme", added == null ? "NULL" : added.length);	
		
		for(Vme in : added) {
			try {
				this.updater.refreshVme(in.getId());
			} catch (Exception e) {
				throw new FactSheetChangeException(ADD_ERROR ,e);
			}
		}
			
		this.createFactsheets(this.findVMEIDs(added));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#VMEDeleted(org.fao.fi.vme.domain.model.Vme[])
	 */
	@Override
	public final void vmeDeleted(final Vme... deleted){
		LOG.info("Notified of deletions of {} elements of type Vme", deleted == null ? "NULL" : deleted.length);	
				
		this.deleteFactsheets(this.findVMEIDs(deleted));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#generalMeasureChanged(org.fao.fi.vme.domain.model.GeneralMeasure[])
	 */
	@Override
	public final void generalMeasureChanged(final GeneralMeasure... changed){
		LOG.info("Notified of changes to {} elements of type GeneralMeasure", changed == null ? "NULL" : changed.length);
		
		for(GeneralMeasure in : changed) {
			try {
				this.updater.refreshGeneralMeasure(in.getId());
			} catch (Exception e) {
				throw new FactSheetChangeException(UPDATE_ERROR ,e);
			}
		}
		
		this.updateFactsheets(this.findVMEIDs(changed));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#generalMeasureAdded(org.fao.fi.vme.domain.model.GeneralMeasure[])
	 */
	@Override
	public final void generalMeasureAdded(final GeneralMeasure... added){
		LOG.info("Notified of additions of {} elements of type GeneralMeasure", added == null ? "NULL" : added.length);
		
		for(GeneralMeasure in : added) {
			try {
				this.updater.refreshGeneralMeasure(in.getId());
			} catch (Exception e) {
				throw new FactSheetChangeException(ADD_ERROR ,e);
			}
		}
		
		this.updateFactsheets(this.findVMEIDs(added));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#generalMeasureDeleted(org.fao.fi.vme.domain.model.GeneralMeasure[])
	 */
	@Override
	public final void generalMeasureDeleted(Rfmo owner, final GeneralMeasure... deleted){
		LOG.info("Notified of deletions of {} elements of type GeneralMeasure", deleted == null ? "NULL" : deleted.length);
		
		this.updateFactsheets(this.findVMEIDs(owner));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#informationSourceChanged(org.fao.fi.vme.domain.model.InformationSource[])
	 */
	@Override
	public final void informationSourceChanged(final InformationSource... changed){
		LOG.info("Notifiying listeners of changes to {} elements of type InformationSource", changed == null ? "NULL" : changed.length);

		for(InformationSource in : changed) {
			try {
				this.updater.refreshInformationSource(in.getId());
			} catch (Exception e) {
				throw new FactSheetChangeException(UPDATE_ERROR ,e);
			}
		}
		
		this.updateFactsheets(this.findVMEIDs(changed));

		LOG.info("Notified of changes to {} elements of type InformationSource", changed == null ? "NULL" : changed.length);
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#informationSourceAdded(org.fao.fi.vme.domain.model.InformationSource[])
	 */
	@Override
	public final void informationSourceAdded(final InformationSource... added){
		LOG.info("Notified of additions of {} elements of type InformationSource", added == null ? "NULL" : added.length);
		
		for(InformationSource in : added) {
			try {
				this.updater.refreshInformationSource(in.getId());
			} catch (Exception e) {
				throw new FactSheetChangeException(ADD_ERROR ,e);
			}
		}
			
		this.updateFactsheets(this.findVMEIDs(added));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#informationSourceDeleted(org.fao.fi.vme.domain.model.InformationSource[])
	 */
	@Override
	public final void informationSourceDeleted(Rfmo owner, final InformationSource... deleted){
		LOG.info("Notified of deletions of {} elements of type InformationSource", deleted == null ? "NULL" : deleted.length);
		
		this.updateFactsheets(this.findVMEIDs(owner));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#fishingFootprintChanged(org.fao.fi.vme.domain.model.extended.FisheryAreasHistory[])
	 */
	@Override
	public final void fishingFootprintChanged(final FisheryAreasHistory... changed){
		LOG.info("Notified of changes to {} elements of type FisheryAreasHistory", changed == null ? "NULL" : changed.length);
		
		for(FisheryAreasHistory in : changed) {
			try {
				this.updater.refreshFishingFootprint(in.getId());
			} catch (Exception e) {
				throw new FactSheetChangeException(UPDATE_ERROR ,e);
			}
		}
			
		this.updateFactsheets(this.findVMEIDs(changed));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#fishingFootprintAdded(org.fao.fi.vme.domain.model.extended.FisheryAreasHistory[])
	 */
	@Override
	public final void fishingFootprintAdded(final FisheryAreasHistory... added){
		LOG.info("Notified of additions of {} elements of type FisheryAreasHistory", added == null ? "NULL" : added.length);

		this.updateFactsheets(this.findVMEIDs(added));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#fishingFootprintDeleted(org.fao.fi.vme.domain.model.extended.FisheryAreasHistory[])
	 */
	@Override
	public final void fishingFootprintDeleted(Rfmo owner, final FisheryAreasHistory... deleted){
		LOG.info("Notified of deletions of {} elements of type FisheryAreasHistory", deleted == null ? "NULL" : deleted.length);
		
		this.updateFactsheets(this.findVMEIDs(owner));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#regionalHistoryChanged(org.fao.fi.vme.domain.model.extended.VMEsHistory[])
	 */
	@Override
	public final void regionalHistoryChanged(final VMEsHistory... changed){
		LOG.info("Notified of changes to {} elements of type VMEsHistory", changed == null ? "NULL" : changed.length);

		for(VMEsHistory in : changed) {
			try {
				this.updater.refreshRegionalHistory(in.getId());
			} catch (Exception e) {
				throw new FactSheetChangeException(UPDATE_ERROR ,e);
			}
		}
			
		this.updateFactsheets(this.findVMEIDs(changed));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#regionalHistoryAdded(org.fao.fi.vme.domain.model.extended.VMEsHistory[])
	 */
	@Override
	public final void regionalHistoryAdded(final VMEsHistory... added){
		LOG.info("Notified of additions of {} elements of type VMEsHistory", added == null ? "NULL" : added.length);
		
		for(VMEsHistory in : added) {
			try {
				this.updater.refreshRegionalHistory(in.getId());
			} catch (Exception e) {
				throw new FactSheetChangeException(ADD_ERROR ,e);
			}
		}
		
		this.updateFactsheets(this.findVMEIDs(added));
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetChangeListener#regionalHistoryDeleted(org.fao.fi.vme.domain.model.extended.VMEsHistory[])
	 */
	@Override
	public final void regionalHistoryDeleted(Rfmo owner, final VMEsHistory... deleted){
		LOG.info("Notified of deletions of {} elements of type VMEsHistory", deleted == null ? "NULL" : deleted.length);	
		
		this.updateFactsheets(this.findVMEIDs(owner));
	}
	
	protected void createFactsheets(final Long[] vmeIDs) {
		if(vmeIDs == null || vmeIDs.length == 0) {
			LOG.warn("Unable to create factsheets for a NULL or empty set of VME IDs");
		} else {
			LOG.info("Asynchronously creating factsheets for {} VMEs with ID {}", vmeIDs.length, this.serializeIDs(vmeIDs));
			
			for(final Long id : vmeIDs) {
				if(id != null) {
					try {
						this.updater.createFactsheets(id);
						
						LOG.info("Synchronously created factsheet for VME with ID {}", id);
					} catch(Exception t) {
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
						this.updater.updateFactsheets(id);
						
						LOG.info("Synchronously updated factsheet for VME with ID {}", id);
					} catch(Exception t) {
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
						this.updater.deleteFactsheets(id);
						
						LOG.info("Synchronously deleted factsheet for VME with ID {}", id);
					} catch(Exception t) {
						LOG.error("Unable to synchronously delete factsheet for VME with ID {}: {}", id, t.getMessage(), t);
					}
				} else {
					LOG.warn("Unable to delete factsheet for NULL VME ID");
				}
			}
		}
	}
		
	protected final Long[] findVMEIDs(Vme... vmes) {
		Collection<Long> idCollection = new HashSet<Long>();
		
		for(Vme in : vmes) {
			if(in != null && in.getId() != null) {
				idCollection.add(in.getId());
			}
		}
		
		return idCollection.toArray(new Long[idCollection.size()]);
	}
	
	protected final Long[] findVMEIDs(GeneralMeasure... generalMeasures) {
		return this.findVMEIDs(this.findRFMOs(generalMeasures));
	}
	
	protected final Long[] findVMEIDs(InformationSource... informationSources) {
		return this.findVMEIDs(this.findRFMOs(informationSources));
	}
	
	protected final Long[] findVMEIDs(FisheryAreasHistory... fishingFootprints) {
		return this.findVMEIDs(this.findRFMOs(fishingFootprints));
	}
	
	protected final Long[] findVMEIDs(VMEsHistory... regionalHistories) {
		return this.findVMEIDs(this.findRFMOs(regionalHistories));
	}
	
	protected final Long[] findVMEIDs(Rfmo... rfmos) {
		Collection<Long> idCollection = new HashSet<Long>();
				
		for(Rfmo authority : rfmos) {
			if(authority != null && authority.getListOfManagedVmes() != null) {
				for(Vme vme : authority.getListOfManagedVmes()) {
					if(vme.getId() != null) {
						idCollection.add(vme.getId());
					}
				}
			}
		}
		
		LOG.info("IDs for VMEs belonging to {} are: {}", this.serializeIDs(rfmos), this.serializeIDs(idCollection.toArray(new Object[idCollection.size()])));
		
		return idCollection.toArray(new Long[idCollection.size()]);
	}
	
	protected final Rfmo[] findRFMOs(GeneralMeasure... generalMeasures) {
		Collection<Rfmo> rfmoCollection = new ArrayList<Rfmo>();
		
		for(GeneralMeasure gm : generalMeasures) {
			if(gm != null && gm.getRfmo() != null) {
				rfmoCollection.add(gm.getRfmo());
			}
		}
		
		LOG.info("RFMOs owning GeneralMeasures {} are: {}", this.serializeIDs(generalMeasures), this.serializeIDs(rfmoCollection.toArray(new Rfmo[rfmoCollection.size()])));
		
		return rfmoCollection.toArray(new Rfmo[rfmoCollection.size()]);
	}
	
	protected final Rfmo[] findRFMOs(InformationSource... informationSources) {
		Collection<Rfmo> rfmoCollection = new ArrayList<Rfmo>();
		
		for(InformationSource is : informationSources) {
			if(is != null && is.getRfmo() != null) {
				rfmoCollection.add(is.getRfmo());
			}
		}
		
		LOG.info("RFMOs owning InformationSources {} are: {}", this.serializeIDs(informationSources), this.serializeIDs(rfmoCollection.toArray(new Rfmo[rfmoCollection.size()])));
		
		return rfmoCollection.toArray(new Rfmo[rfmoCollection.size()]);
	}
	
	protected final Rfmo[] findRFMOs(FisheryAreasHistory... fishingFootprints) {
		Collection<Rfmo> rfmoCollection = new ArrayList<Rfmo>();
		
		for(FisheryAreasHistory ff : fishingFootprints) {
			if(ff != null && ff.getRfmo() != null) {
				rfmoCollection.add(ff.getRfmo());
			}
		}
		
		LOG.info("RFMOs owning FishingFootprints {} are: {}", this.serializeIDs(fishingFootprints), this.serializeIDs(rfmoCollection.toArray(new Rfmo[rfmoCollection.size()])));
		
		return rfmoCollection.toArray(new Rfmo[rfmoCollection.size()]);
	}
	
	protected final Rfmo[] findRFMOs(VMEsHistory... regionalHistories) {
		Collection<Rfmo> rfmoCollection = new ArrayList<Rfmo>();
		
		for(VMEsHistory rh : regionalHistories) {
			if(rh != null && rh.getRfmo() != null) {
				rfmoCollection.add(rh.getRfmo());
			}
		}
		
		LOG.info("RFMOs owning RegionalHistories {} are: {}", this.serializeIDs(regionalHistories), this.serializeIDs(rfmoCollection.toArray(new Rfmo[rfmoCollection.size()])));
		
		return rfmoCollection.toArray(new Rfmo[rfmoCollection.size()]);
	}
	
	protected final String serializeIDs(Object[] idArray) {
		StringBuilder result = new StringBuilder("[ ");
		
		for(Object in : idArray) {
			result.append(in).append(", ");
		}
		
		result.append("]");
		
		return result.toString().replaceAll("\\, \\]$", " ]");
	}
	
	protected final String serializeIDs(ObjectId<?>[] objects) {
		Collection<Object> idCollection = new HashSet<Object>();
		
		for(ObjectId<?> in : objects) {
			if(in != null && in.getId() != null) {
				idCollection.add(in.getId());
			}
		}
		
		return this.serializeIDs(idCollection.toArray(new Object[idCollection.size()]));
	}
	
	protected final String serializeIDs(Rfmo[] rfmos) {
		Collection<Object> idCollection = new HashSet<Object>();
		
		for(Rfmo in : rfmos) {
			if(in != null && in.getId() != null) {
				idCollection.add(in.getId());
			}
		}
		
		return this.serializeIDs(idCollection.toArray(new Object[idCollection.size()]));
	}
}