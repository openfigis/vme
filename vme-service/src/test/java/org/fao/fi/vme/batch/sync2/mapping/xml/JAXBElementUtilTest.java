package org.fao.fi.vme.batch.sync2.mapping.xml;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.JAXBElement;

import org.fao.fi.figis.devcon.Min;
import org.fao.fi.figis.devcon.ObjectFactory;
import org.junit.Test;

public class JAXBElementUtilTest {

	JAXBElementUtil u = new JAXBElementUtil();
	private ObjectFactory f = new ObjectFactory();

	@Test
	public void testfillObject() {
		Min min = f.createMin();
		Date justADate = Calendar.getInstance().getTime();
		JAXBElement<Min> minJAXBElement = f.createRangeMin(min);
		u.fillObject(justADate, minJAXBElement);

		assertEquals(justADate.toString(), minJAXBElement.getValue());

	}
}
