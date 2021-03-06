package org.fao.fi.vme.dto.test;

import org.fao.fi.vme.webservice.SpecificMeasureType;

public class SpecificMeasureTypeMock {
	
	private static final int ID = 10;
	private static int OID = 958;

	private SpecificMeasureTypeMock() {
		
	}
	
	public static SpecificMeasureType create() {
		SpecificMeasureType s1 = new SpecificMeasureType();
		s1.setOid(OID++);
		s1.setId(ID);
		s1.setYear(1985);
		s1.setLang("en");
		s1.setMeasureSourceUrl("http://archive.nafo.int/open/fc/2013/fcdoc13-01.pdf");
		String text = "Text met <b>kippesoep<b> en aardbeien. ";
		String cddataText = "<![CDATA[" + text + "]]>";
		s1.setMeasureText(cddataText);
		
		return s1;

	}
}
