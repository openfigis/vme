package org.fao.fi.vme.msaccess.tables;

import java.util.ArrayList;

import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.msaccess.mapping.TableDomainMapper;

public class RFB_MetaData implements TableDomainMapper {

	private int iD;
	private int yearId;
	private String rfbId;
	private String rfbL;
	private String rfbWw;
	private String rfbFAOFactSheets;

	public int getID() {
		return iD;
	}

	public void setID(int iD) {
		this.iD = iD;
	}

	public int getYear_ID() {
		return yearId;
	}

	public void setYear_ID(int yearId) {
		this.yearId = yearId;
	}

	public String getRFB_ID() {
		return rfbId;
	}

	public void setRFB_ID(String rfbId) {
		this.rfbId = rfbId;
	}

	public String getRFB_L() {
		return rfbL;
	}

	public void setRFB_L(String rfbL) {
		this.rfbL = rfbL;
	}

	public String getRFB_www() {
		return rfbWw;
	}

	public void setRFB_www(String rfbWw) {
		this.rfbWw = rfbWw;
	}

	public String getRFB_FAO_FactSheets() {
		return rfbFAOFactSheets;
	}

	public void setRFB_FAO_FactSheets(String rfbFAOFactSheets) {
		this.rfbFAOFactSheets = rfbFAOFactSheets;
	}

	@Override
	public Object map() {
		Rfmo o = new Rfmo();
		// o.setFishingActivityList(fishingActivityList);
		// o.setGeneralMeasuresList(generalMeasuresList)
		o.setId(this.rfbId);

		o.setHasFisheryAreasHistory(new ArrayList<FisheryAreasHistory>());
		o.setHasVmesHistory(new ArrayList<VMEsHistory>());

		// o.setManagedVmeList(managedVmeList)
		// o.setMeetingList(meetingList)
		return o;
	}

}
