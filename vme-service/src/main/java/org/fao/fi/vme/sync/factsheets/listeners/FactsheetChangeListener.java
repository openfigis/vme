/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.listeners;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
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
public interface FactsheetChangeListener {
	void vmeChanged(Vme... changed);
	void vmeAdded(Vme... added);
	void vmeDeleted(Vme... deleted);
	
	void generalMeasureChanged(GeneralMeasure... changed);
	void generalMeasureAdded(GeneralMeasure... added);
	void generalMeasureDeleted(Rfmo owner, GeneralMeasure... deleted);
	
	void informationSourceChanged(InformationSource... changed);
	void informationSourceAdded(InformationSource... added);
	void informationSourceDeleted(Rfmo owner, InformationSource... deleted);
	
	void fishingFootprintChanged(FisheryAreasHistory... changed);
	void fishingFootprintAdded(FisheryAreasHistory... added);
	void fishingFootprintDeleted(Rfmo owner, FisheryAreasHistory... deleted);
	
	void regionalHistoryChanged(VMEsHistory... changed);
	void regionalHistoryAdded(VMEsHistory... added);
	void regionalHistoryDeleted(Rfmo owner, VMEsHistory... deleted);
}