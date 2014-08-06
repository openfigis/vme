package org.fao.fi.vme.msaccess.tableextension;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.VMEsHistory;

/**
 * The pattern is that every table in MS-Acces can be mapped to one domain
 * object. This case is an exception where the Access table
 * RFB_VME_Fishing_History is mapped to 2 tables.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class HistoryHolder {

	private FisheryAreasHistory fisheryAreasHistory;
	private VMEsHistory vmesHistory;

	public FisheryAreasHistory getFisheryAreasHistory() {
		return fisheryAreasHistory;
	}

	public void setFisheryAreasHistory(FisheryAreasHistory fisheryAreasHistory) {
		this.fisheryAreasHistory = fisheryAreasHistory;
	}

	public VMEsHistory getVmesHistory() {
		return vmesHistory;
	}

	public void setVmesHistory(VMEsHistory vmesHistory) {
		this.vmesHistory = vmesHistory;
	}

}
