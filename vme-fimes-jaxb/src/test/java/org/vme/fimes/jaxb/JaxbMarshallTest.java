package org.vme.fimes.jaxb;

import static org.junit.Assert.assertTrue;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.GeneralBiology;
import org.fao.fi.figis.devcon.HabitatBio;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.figis.devcon.VME;
import org.junit.Test;

public class JaxbMarshallTest {

	JaxbMarshall m = new JaxbMarshall();

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
