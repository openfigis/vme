package org.fao.fi.vme.dto.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.fao.fi.vme.webservice.SpecificMeasureType;

public class SpecificMeasureTypeMock {

	private static final int ID = 10;
	private static int OID = 958;

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

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		df.format(Calendar.getInstance());
		 s1.setValidityPeriodEnd(df.format(Calendar.getInstance()));
		 s1.setValidityPeriodStart(df.format(Calendar.getInstance()));

		return s1;

	}
}
