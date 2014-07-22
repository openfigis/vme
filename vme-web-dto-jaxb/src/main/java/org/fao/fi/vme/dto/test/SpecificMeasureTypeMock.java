package org.fao.fi.vme.dto.test;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.fao.fi.vme.webservice.SpecificMeasureType;

public class SpecificMeasureTypeMock {

	private static int ID = 10;

	public static SpecificMeasureType create() {
		SpecificMeasureType s1 = new SpecificMeasureType();
		s1.setId(ID++);
		s1.setYear(1985);
		s1.setLang("en");
		s1.setMeasureSourceUrl("http://archive.nafo.int/open/fc/2013/fcdoc13-01.pdf");

		GregorianCalendar gcal = new GregorianCalendar();
		try {
			s1.setValidityPeriodEnd(DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal));
			s1.setValidityPeriodStart(DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal));
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}

		return s1;

	}

}
