package org.fao.fi.vme.msaccess.tables;

import org.fao.fi.vme.domain.Rfmo;
import org.fao.fi.vme.msaccess.mapping.TableDomainMapper;

public class RFB_MetaData implements TableDomainMapper {

	private int ID;
	private int Year_ID;
	private String RFB_ID;
	private String RFB_L;
	private String RFB_www;
	private String RFB_FAO_FactSheets;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getYear_ID() {
		return Year_ID;
	}

	public void setYear_ID(int year_ID) {
		Year_ID = year_ID;
	}

	public String getRFB_ID() {
		return RFB_ID;
	}

	public void setRFB_ID(String rFB_ID) {
		RFB_ID = rFB_ID;
	}

	public String getRFB_L() {
		return RFB_L;
	}

	public void setRFB_L(String rFB_L) {
		RFB_L = rFB_L;
	}

	public String getRFB_www() {
		return RFB_www;
	}

	public void setRFB_www(String rFB_www) {
		RFB_www = rFB_www;
	}

	public String getRFB_FAO_FactSheets() {
		return RFB_FAO_FactSheets;
	}

	public void setRFB_FAO_FactSheets(String rFB_FAO_FactSheets) {
		RFB_FAO_FactSheets = rFB_FAO_FactSheets;
	}

	@Override
	public Object map() {
		Rfmo o = new Rfmo();
		// o.setFishingActivityList(fishingActivityList);
		// o.setGeneralMeasuresList(generalMeasuresList)
		o.setId(this.ID);
		// o.setManagedVmeList(managedVmeList)
		// o.setMeetingList(meetingList)
		return o;
	}

}
