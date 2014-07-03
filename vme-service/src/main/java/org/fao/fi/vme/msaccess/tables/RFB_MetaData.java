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
	private String rfbWww;
	private String rfbFAOFactSheets;

	public int getID() {
		return iD;
	}

	public void setID(int iD) {
		this.iD = iD;
	}

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public String getRfbId() {
		return rfbId;
	}

	public void setRfbId(String rfbId) {
		this.rfbId = rfbId;
	}

	public String getRfbL() {
		return rfbL;
	}

	public void setRfbL(String rfbL) {
		this.rfbL = rfbL;
	}

	public String getRfbWww() {
		return rfbWww;
	}

	public void setRfbWww(String rfbWww) {
		this.rfbWww = rfbWww;
	}

	public String getRfbFAOFactSheets() {
		return rfbFAOFactSheets;
	}

	public void setRfbFAOFactSheets(String rfbFAOFactSheets) {
		this.rfbFAOFactSheets = rfbFAOFactSheets;
	}

	@Override
	public Object map() {
		Rfmo o = new Rfmo();
		o.setId(this.rfbId);

		o.setHasFisheryAreasHistory(new ArrayList<FisheryAreasHistory>());
		o.setHasVmesHistory(new ArrayList<VMEsHistory>());

		return o;
	}

}
