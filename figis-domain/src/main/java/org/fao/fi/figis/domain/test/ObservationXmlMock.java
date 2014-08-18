package org.fao.fi.figis.domain.test;

import java.sql.Date;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.rule.Figis;
import org.vme.fimes.jaxb.JaxbMarshall;

public class ObservationXmlMock {
	private ObservationXmlMock(){
		
	}
	public static ObservationXml create() {

		ObservationXml xml = new ObservationXml();
		xml.setLanguage(Figis.LANG);
		xml.setStatus(Figis.STATUS);
		Date d = new Date(System.currentTimeMillis());
		xml.setLastEditDate(d);
		xml.setCreationDate(d);
		FIGISDoc figisDoc = new FIGISDoc();
		JaxbMarshall m = new JaxbMarshall();
		xml.setXml(m.marshalToString(figisDoc));
		return xml;
	}
}
