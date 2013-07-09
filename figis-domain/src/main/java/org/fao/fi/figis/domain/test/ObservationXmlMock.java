package org.fao.fi.figis.domain.test;

import java.sql.Date;

import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.rule.Figis;

public class ObservationXmlMock {
	public static ObservationXml create() {

		ObservationXml xml = new ObservationXml();
		xml.setLanguage(2);
		xml.setStatus(Figis.STATUS);
		Date d = new Date(System.currentTimeMillis());
		xml.setLastEditDate(d);
		xml.setCreationDate(d);

		return xml;
	}
}
