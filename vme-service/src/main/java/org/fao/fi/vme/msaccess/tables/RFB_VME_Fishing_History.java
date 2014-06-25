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

	public String getRFB_ID() {
		return rfbId;
	}

	public void setRFB_ID(String rfbId) {
		this.rfbId = rfbId;
	}

	public int getYear_ID() {
		return yearId;
	}

	public void setYear_ID(int yearId) {
		this.yearId = yearId;
	}

	public String getRFB_FishingAreas_GeneralText() {
		return rfbFishingAreasGeneralText;
	}

	public void setRFB_FishingAreas_GeneralText(String rfbFishingAreasGeneralText) {
		this.rfbFishingAreasGeneralText = rfbFishingAreasGeneralText;
	}

	public String getRFB_VMEs_GeneralText() {
		return rfbVmesGeneralText;
	}

	public void setRFB_VMEs_GeneralText(String rfbVmesGeneralText) {
		this.rfbVmesGeneralText = rfbVmesGeneralText;
	}

	@Override
	public Object map() {

		FisheryAreasHistory fisheryAreasHistory = new FisheryAreasHistory();
		VMEsHistory vmesHistory = new VMEsHistory();

		// fisheryAreasHistory.setId(new Long(this.ID));
		// vmesHistory.setId(new Long(this.ID));

		fisheryAreasHistory.setHistory(u.english(this.getRFB_FishingAreas_GeneralText()));
		vmesHistory.setHistory(u.english(this.getRFB_VMEs_GeneralText()));

		fisheryAreasHistory.setYear(this.yearId);
		vmesHistory.setYear(this.yearId);

		HistoryHolder h = new HistoryHolder();
		h.setFisheryAreasHistory(fisheryAreasHistory);
		h.setVmesHistory(vmesHistory);

		return h;
	}
}
