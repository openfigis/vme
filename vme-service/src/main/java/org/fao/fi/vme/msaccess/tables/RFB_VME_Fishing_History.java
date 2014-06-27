package org.fao.fi.vme.msaccess.tables;

import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.msaccess.mapping.TableDomainMapper;
import org.fao.fi.vme.msaccess.tableextension.HistoryHolder;

public class RFB_VME_Fishing_History implements TableDomainMapper {

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	private int iD;
	private String rfbId;
	private int yearId;
	private String rfbFishingAreasGeneralText;
	private String rfbVmesGeneralText;

	public int getID() {
		return iD;
	}

	public void setID(int iD) {
		this.iD = iD;
	}

	public String getRfbId() {
		return rfbId;
	}

	public void setRfbId(String rfbId) {
		this.rfbId = rfbId;
	}

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public String getRfbFishingAreasGeneralText() {
		return rfbFishingAreasGeneralText;
	}

	public void setRfbFishingAreasGeneralText(String rfbFishingAreasGeneralText) {
		this.rfbFishingAreasGeneralText = rfbFishingAreasGeneralText;
	}

	public String getRfbVmesGeneralText() {
		return rfbVmesGeneralText;
	}

	public void setRfbVmesGeneralText(String rfbVmesGeneralText) {
		this.rfbVmesGeneralText = rfbVmesGeneralText;
	}

	@Override
	public Object map() {

		FisheryAreasHistory fisheryAreasHistory = new FisheryAreasHistory();
		VMEsHistory vmesHistory = new VMEsHistory();

		// fisheryAreasHistory.setId(new Long(this.ID));
		// vmesHistory.setId(new Long(this.ID));

		fisheryAreasHistory.setHistory(u.english(this.getRfbFishingAreasGeneralText()));
		vmesHistory.setHistory(u.english(this.getRfbVmesGeneralText()));

		fisheryAreasHistory.setYear(this.yearId);
		vmesHistory.setYear(this.yearId);

		HistoryHolder h = new HistoryHolder();
		h.setFisheryAreasHistory(fisheryAreasHistory);
		h.setVmesHistory(vmesHistory);

		return h;
	}
}
