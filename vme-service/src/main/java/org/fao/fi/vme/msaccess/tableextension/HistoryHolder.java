package org.fao.fi.vme.msaccess.tableextension;

import org.fao.fi.vme.domain.model.History;

/**
 * The pattern is that every table in MS-Acces can be mapped to one domain object. This case is an exception where the
 * Access table RFB_VME_Fishing_History is mapped to 2 tables.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class HistoryHolder {

	private History fisheryAreasHistory;
	private History vmesHistory;

	public History getFisheryAreasHistory() {
		return fisheryAreasHistory;
	}

	public void setFisheryAreasHistory(History fisheryAreasHistory) {
		this.fisheryAreasHistory = fisheryAreasHistory;
	}

	public History getVmesHistory() {
		return vmesHistory;
	}

	public void setVmesHistory(History vmesHistory) {
		this.vmesHistory = vmesHistory;
	}

}
