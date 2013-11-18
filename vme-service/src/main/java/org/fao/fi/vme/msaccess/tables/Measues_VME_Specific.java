package org.fao.fi.vme.msaccess.tables;

import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.msaccess.mapping.TableDomainMapper;

public class Measues_VME_Specific implements TableDomainMapper {

	public void setMeetings_ID(int meetings_ID) {
		Meetings_ID = meetings_ID;
	}

	private int ID;
	private String RFB_ID;
	private String VME_ID;
	private int Year_ID;
	private String VME_SpecificMeasure_Validity_Start;
	private String VME_SpecificMeasure_Validity_End;
	private int Meetings_ID;
	private String VME_SpecificMeasure;

	public int getMeetings_ID() {
		return Meetings_ID;
	}

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

	public String getVME_ID() {
		return VME_ID;
	}

	public void setVME_ID(String vME_ID) {
		VME_ID = vME_ID;
	}

	public int getYear_ID() {
		return Year_ID;
	}

	public void setYear_ID(int year_ID) {
		Year_ID = year_ID;
	}

	public String getVME_SpecificMeasure_Validity_Start() {
		return VME_SpecificMeasure_Validity_Start;
	}

	public void setVME_SpecificMeasure_Validity_Start(String vME_SpecificMeasure_Validity_Start) {
		VME_SpecificMeasure_Validity_Start = vME_SpecificMeasure_Validity_Start;
	}

	public String getVME_SpecificMeasure_Validity_End() {
		return VME_SpecificMeasure_Validity_End;
	}

	public void setVME_SpecificMeasure_Validity_End(String vME_SpecificMeasure_Validity_End) {
		VME_SpecificMeasure_Validity_End = vME_SpecificMeasure_Validity_End;
	}

	public String getVME_SpecificMeasure() {
		return VME_SpecificMeasure;
	}

	public void setVME_SpecificMeasure(String vME_SpecificMeasure) {
		VME_SpecificMeasure = vME_SpecificMeasure;
	}

	@Override
	public Object map() {
		MultiLingualStringUtil u = new MultiLingualStringUtil();

		SpecificMeasure o = new SpecificMeasure();
		// o.getDocument().setUrl(this.get)
		o.setId(new Long(this.getID()));
		// o.setMeasureSummary(this.get)
		o.setValidityPeriod(new ValidityPeriod());
		o.getValidityPeriod().setBeginYear(new Integer(this.getVME_SpecificMeasure_Validity_Start()).intValue());
		o.getValidityPeriod().setEndYear(new Integer(this.getVME_SpecificMeasure_Validity_End()).intValue());
		// o.setVme(this.getVME_ID());
		o.setYear(this.getYear_ID());
		o.setVmeSpecificMeasure(u.english(this.VME_SpecificMeasure));

		return o;
	}
}
