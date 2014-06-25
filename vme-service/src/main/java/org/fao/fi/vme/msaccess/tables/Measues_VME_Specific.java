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

	private int sourceId;

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

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

	public String getVmeId() {
		return vmeId;
	}

	public void setVmeId(String vmeId) {
		this.vmeId = vmeId;
	}

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public String getVmeSpecificMeasureValidityStart() {
		return vmeSpecificMeasureValidityStart;
	}

	public void setVmeSpecificMeasureValidityStart(String vmeSpecificMeasureValidityStart) {
		this.vmeSpecificMeasureValidityStart = vmeSpecificMeasureValidityStart;
	}

	public String getVmeSpecificMeasureValidityEnd() {
		return vmeSpecificMeasureValidityEnd;
	}

	public void setVmeSpecificMeasureValidityEnd(String vmeSpecificMeasureValidityEnd) {
		this.vmeSpecificMeasureValidityEnd = vmeSpecificMeasureValidityEnd;
	}

	public String getVmeSpecificMeasure() {
		return vmeSpecificMeasure;
	}

	public void setVmeSpecificMeasure(String vmeSpecificMeasure) {
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
		o.setYear(this.getYearId());
		o.setVmeSpecificMeasure(u.english(this.vmeSpecificMeasure));

		return o;
	}
}
