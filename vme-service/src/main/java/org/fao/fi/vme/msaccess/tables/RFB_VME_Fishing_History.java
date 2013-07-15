package org.fao.fi.vme.msaccess.tables;

import org.fao.fi.vme.domain.History;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.msaccess.mapping.TableDomainMapper;

public class RFB_VME_Fishing_History implements TableDomainMapper {

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	private int ID;
	private String RFB_ID;
	private int Year_ID;
	private String RFB_FishingAreas_GeneralText;
	private String RFB_VMEs_GeneralText;


	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getRFB_ID() {
		return RFB_ID;
	}

	public void setRFB_ID(String rFB_ID) {
		RFB_ID = rFB_ID;
	}

	public int getYear_ID() {
		return Year_ID;
	}

	public void setYear_ID(int year_ID) {
		Year_ID = year_ID;
	}

	public String getRFB_FishingAreas_GeneralText() {
		return RFB_FishingAreas_GeneralText;
	}

	public void setRFB_FishingAreas_GeneralText(String rFB_FishingAreas_GeneralText) {
		RFB_FishingAreas_GeneralText = rFB_FishingAreas_GeneralText;
	}

	public String getRFB_VMEs_GeneralText() {
		return RFB_VMEs_GeneralText;
	}

	public void setRFB_VMEs_GeneralText(String rFB_VMEs_GeneralText) {
		RFB_VMEs_GeneralText = rFB_VMEs_GeneralText;
	}

	@Override
	public Object map() {
		History o = new History();
		o.setId(new Long(this.ID));
		o.setHistory(u.english(this.getRFB_VMEs_GeneralText()));

		// TODO where will this one be mapped to?
		// this.getRFB_FishingAreas_GeneralText();

		o.setYear(this.Year_ID);

		return o;
	}
}
