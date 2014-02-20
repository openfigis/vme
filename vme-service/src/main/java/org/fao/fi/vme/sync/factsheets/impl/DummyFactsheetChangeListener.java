/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.impl;

import javax.enterprise.inject.Alternative;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;

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
public class DummyFactsheetChangeListener extends AbstractAsyncFactsheetChangeListener {
	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleVmeAddition(org.fao.fi.vme.domain.model.Vme[])
	 */
	@Override
	protected void handleVmeAddition(Vme... added) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleVmeChange(org.fao.fi.vme.domain.model.Vme[])
	 */
	@Override
	protected void handleVmeChange(Vme... changed) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleVmeDeletion(org.fao.fi.vme.domain.model.Vme[])
	 */
	@Override
	protected void handleVmeDeletion(Vme... deleted) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleGeneralMeasureAddition(org.fao.fi.vme.domain.model.GeneralMeasure[])
	 */
	@Override
	protected void handleGeneralMeasureAddition(GeneralMeasure... added) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleGeneralMeasureChange(org.fao.fi.vme.domain.model.GeneralMeasure[])
	 */
	@Override
	protected void handleGeneralMeasureChange(GeneralMeasure... changed) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleGeneralMeasureDeletion(org.fao.fi.vme.domain.model.GeneralMeasure[])
	 */
	@Override
	protected void handleGeneralMeasureDeletion(GeneralMeasure... deleted) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleInformationSourceAddition(org.fao.fi.vme.domain.model.InformationSource[])
	 */
	@Override
	protected void handleInformationSourceAddition(InformationSource... added) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleInformationSourceChange(org.fao.fi.vme.domain.model.InformationSource[])
	 */
	@Override
	protected void handleInformationSourceChange(InformationSource... changed) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleInformationSourceDeletion(org.fao.fi.vme.domain.model.InformationSource[])
	 */
	@Override
	protected void handleInformationSourceDeletion(InformationSource... deleted) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleFishingFootprintAddition(org.fao.fi.vme.domain.model.extended.FisheryAreasHistory[])
	 */
	@Override
	protected void handleFishingFootprintAddition(FisheryAreasHistory... added) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleFishingFootprintChange(org.fao.fi.vme.domain.model.extended.FisheryAreasHistory[])
	 */
	@Override
	protected void handleFishingFootprintChange(FisheryAreasHistory... changed) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleFishingFootprintDeletion(org.fao.fi.vme.domain.model.extended.FisheryAreasHistory[])
	 */
	@Override
	protected void handleFishingFootprintDeletion(FisheryAreasHistory... deleted) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleRegionalHistoryAddition(org.fao.fi.vme.domain.model.extended.VMEsHistory[])
	 */
	@Override
	protected void handleRegionalHistoryAddition(VMEsHistory... added) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleRegionalHistoryChange(org.fao.fi.vme.domain.model.extended.VMEsHistory[])
	 */
	@Override
	protected void handleRegionalHistoryChange(VMEsHistory... changed) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.impl.AbstractAsyncFactsheetChangeListener#handleRegionalHistoryDeletion(org.fao.fi.vme.domain.model.extended.VMEsHistory[])
	 */
	@Override
	protected void handleRegionalHistoryDeletion(VMEsHistory... deleted) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
