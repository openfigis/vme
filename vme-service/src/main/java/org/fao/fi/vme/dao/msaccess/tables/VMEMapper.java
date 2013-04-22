package org.fao.fi.vme.dao.msaccess.tables;

public class VMEMapper extends GenericMapper {

	public VMEMapper() {
		String colums[] = { "ID", "RFB_ID", "VME_ID", "Year_ID", "VME_Validity_Start", "VME_Validity_End",
				"VME_Geoform", "VME_GeogArea1", "VME_GeogArea2", "VME_GeogAreaFAO", "VME_Coord", "VME_Area_Type(Name)",
				"VME_Status", "VME_Description_Physical", "VME_Description_Biology", "VME_Description_Impact" };
		this.colums = colums;
		object = new VME();
	}

}
