package org.vme.fimes.jaxb;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.xml.bind.JAXBElement;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.GeneralBiology;
import org.fao.fi.figis.devcon.HabitatBio;
import org.fao.fi.figis.devcon.Min;
import org.fao.fi.figis.devcon.ObjectFactory;
import org.fao.fi.figis.devcon.Range;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.figis.devcon.VME;
import org.fao.fi.figis.devcon.VMEIdent;
import org.junit.Test;

public class JaxbMarshallTest {

	JaxbMarshall m = new JaxbMarshall();

	@Test
	public void testMarshalToStringMinMax() {

		ObjectFactory f = new ObjectFactory();
		JAXBElement<Min> minJAXBElement = f.createRangeMin(f.createMin());
		fillObject("2010", minJAXBElement);

		Range range = f.createRange();
		range.setType("Time");
		range.getContent().add(minJAXBElement);

		VMEIdent vmeIdent = new VMEIdent();
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(range);

		VME vme = new VME();
		vme.setVMEIdent(vmeIdent);
		FIGISDoc doc = new FIGISDoc();
		doc.setVME(vme);

		String s = m.marshalToString(doc);
		System.out.println(s);
		assertFalse(s.contains("xsi:type=\"xs:string"));

	}

	public <T> void fillObject(Object value, JAXBElement<T> jaxbObject) {
		if (value != null && jaxbObject != null) {
			@SuppressWarnings("unchecked")
			T cccc = (T) value.toString();
			jaxbObject.setValue(cccc);
		}
	}

	@Test
	public void testMarshalToString() {
		FIGISDoc doc = new FIGISDoc();

		String s = m.marshalToString(doc);
		assertTrue(s.startsWith("<?xml version="));
		assertTrue(s.contains("FIGISDoc"));

	}

	@Test
	public void testMarshalToStringCdata() {
		FIGISDoc doc = new FIGISDoc();
		GeneralBiology gb = new GeneralBiology();
		Text text1 = new Text();

		String textWithCdata = "<![CDATA[a text]]>";

		text1.getContent().add(textWithCdata);
		gb.getTextsAndImagesAndTables().add(text1);
		HabitatBio habitatBio = new HabitatBio();
		habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs().add(gb);
		doc.setVME(new VME());
		doc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(habitatBio);
		String s = m.marshalToString(doc);
		System.out.println(s);
		assertTrue(s.contains("<fi:Text><![CDATA[a text]]></fi:Text>"));

	}
}
