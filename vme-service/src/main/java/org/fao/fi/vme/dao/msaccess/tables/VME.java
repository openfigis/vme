package org.fao.fi.vme.dao.msaccess.tables;

import org.fao.fi.vme.dao.msaccess.mapping.TableDomainMapper;
import org.fao.fi.vme.dao.msaccess.mapping.ValidityPeriodRule;
import org.fao.fi.vme.domain.Vme;

public class VME implements TableDomainMapper {
	int ID;
	String RFB_ID;
	String VME_ID;
	int Year_ID;
	int VME_Validity_Start;
	int VME_Validity_End;
	String VME_Geoform;
	String VME_GeogArea1;
	String VME_GeogArea2;
	String VME_GeogAreaFAO;
	String VME_Coord;
	String VME_Area_Type;
	String VME_Status;
	String VME_Description_Physical;
	String VME_Description_Biology;
	String VME_Description_Impact;

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

	public int getVME_Validity_Start() {
		return VME_Validity_Start;
	}

	public void setVME_Validity_Start(int vME_Validity_Start) {
		VME_Validity_Start = vME_Validity_Start;
	}

	public int getVME_Validity_End() {
		return VME_Validity_End;
	}

	public void setVME_Validity_End(int vME_Validity_End) {
		VME_Validity_End = vME_Validity_End;
	}

	public String getVME_Geoform() {
		return VME_Geoform;
	}

	public void setVME_Geoform(String vME_Geoform) {
		VME_Geoform = vME_Geoform;
	}

	public String getVME_GeogArea1() {
		return VME_GeogArea1;
	}

	public void setVME_GeogArea1(String vME_GeogArea1) {
		VME_GeogArea1 = vME_GeogArea1;
	}

	public String getVME_GeogArea2() {
		return VME_GeogArea2;
	}

	public void setVME_GeogArea2(String vME_GeogArea2) {
		VME_GeogArea2 = vME_GeogArea2;
	}

	public String getVME_GeogAreaFAO() {
		return VME_GeogAreaFAO;
	}

	public void setVME_GeogAreaFAO(String vME_GeogAreaFAO) {
		VME_GeogAreaFAO = vME_GeogAreaFAO;
	}

	public String getVME_Coord() {
		return VME_Coord;
	}

	public void setVME_Coord(String vME_Coord) {
		VME_Coord = vME_Coord;
	}

	public String getVME_Area_Type() {
		return VME_Area_Type;
	}

	public void setVME_Area_Type(String vME_Area_Type) {
		VME_Area_Type = vME_Area_Type;
	}

	public String getVME_Status() {
		return VME_Status;
	}

	public void setVME_Status(String vME_Status) {
		VME_Status = vME_Status;
	}

	public String getVME_Description_Physical() {
		return VME_Description_Physical;
	}

	public void setVME_Description_Physical(String vME_Description_Physical) {
		VME_Description_Physical = vME_Description_Physical;
	}

	public String getVME_Description_Biology() {
		return VME_Description_Biology;
	}

	public void setVME_Description_Biology(String vME_Description_Biology) {
		VME_Description_Biology = vME_Description_Biology;
	}

	public String getVME_Description_Impact() {
		return VME_Description_Impact;
	}

	public void setVME_Description_Impact(String vME_Description_Impact) {
		VME_Description_Impact = vME_Description_Impact;
	}

	@Override
	public Object map() {
		Vme o = new Vme();
		o.setAreaType(this.VME_Area_Type);
		// o.setCriteria(this.)
		o.setDescriptionBiological(this.VME_Description_Biology);
		o.setDescriptionImpact(this.VME_Description_Impact);
		o.setDescriptionPhisical(this.VME_Description_Physical);
		o.setGeoform(this.VME_Geoform);
		// o.setGeographicLayerId(this.)
		o.setId(this.ID);
		// o.setName(this.get)
		// o.setRfmo(rfmo)
		ValidityPeriodRule r = new ValidityPeriodRule(this.VME_Validity_Start, this.VME_Validity_End);
		o.getValidityPeriod().setBeginYear(r.getStart());
		o.getValidityPeriod().setEndYear(r.getEnd());

		return o;
	}
}
