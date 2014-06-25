package org.fao.fi.vme.msaccess.tables;

import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.msaccess.mapping.TableDomainMapper;

public class Measues_VME_Specific implements TableDomainMapper {

	private int iD;
	private String rfbId;
	private String vmeId;
	private int yearId;
	private String vmeSpecificMeasureValidityStart;
	private String vmeSpecificMeasureValidityEnd;
	private String vmeSpecificMeasure;

	private int Source_ID;

	public int getSource_ID() {
		return Source_ID;
	}

	public void setSource_ID(int sourceId) {
		Source_ID = sourceId;
	}

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

	public String getVME_ID() {
		return vmeId;
	}

	public void setVME_ID(String vmeId) {
		this.vmeId = vmeId;
	}

	public int getYear_ID() {
		return yearId;
	}

	public void setYear_ID(int yearId) {
		this.yearId = yearId;
	}

	public String getVME_SpecificMeasure_Validity_Start() {
		return vmeSpecificMeasureValidityStart;
	}

	public void setVME_SpecificMeasure_Validity_Start(String vmeSpecificMeasureValidityStart) {
		this.vmeSpecificMeasureValidityStart = vmeSpecificMeasureValidityStart;
	}

	public String getVME_SpecificMeasure_Validity_End() {
		return vmeSpecificMeasureValidityEnd;
	}

	public void setVME_SpecificMeasure_Validity_End(String vmeSpecificMeasureValidityEnd) {
		this.vmeSpecificMeasureValidityEnd = vmeSpecificMeasureValidityEnd;
	}

	public String getVME_SpecificMeasure() {
		return vmeSpecificMeasure;
	}

	public void setVME_SpecificMeasure(String vmeSpecificMeasure) {
		this.vmeSpecificMeasure = vmeSpecificMeasure;
	}

	@Override
	public Object map() {
		MultiLingualStringUtil u = new MultiLingualStringUtil();

		SpecificMeasure o = new SpecificMeasure();
		// o.getDocument().setUrl(this.get)
		o.setId(Long.valueOf(this.getID()));
		// o.setMeasureSummary(this.get)
		o.setValidityPeriod(new ValidityPeriod());

		// Intervention Erik van Ingen 5 June 2014. Commented because this logic
		// // is not used anymore and therefore does not need to be updated.
		// o.getValidityPeriod().setBeginYear(Integer.valueOf(this.getVME_SpecificMeasure_Validity_Start()));
		// o.getValidityPeriod().setEndYear(Integer.valueOf(this.getVME_SpecificMeasure_Validity_End()));
		o.setYear(this.getYear_ID());
		o.setVmeSpecificMeasure(u.english(this.vmeSpecificMeasure));

		return o;
	}
}
