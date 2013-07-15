package org.fao.fi.figis.devcon;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;
import org.custommonkey.xmlunit.XMLTestCase;
import org.junit.Test;
import org.purl.dc.elements._1.Title;
import org.vme.fimes.jaxb.FimesNamespacePrefixMapper;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class FIGISDocTest extends XMLTestCase {

	File file = new File("src/test/resources/VMEFactSheet_XML_prototype.xml");
	File fileShort = new File("src/test/resources/VmeShort.xml");

	// FIGISDoc expectedResult = null;

	java.lang.Class<FIGISDoc> clazz = FIGISDoc.class;

	/**
	 * Marshall is from java to XML, which we need for the project.
	 * 
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * @throws JAXBException
	 */
	@Test
	public void testMarshallShort() throws SAXException, IOException, JAXBException {
		FIGISDoc expectedResult = prepareExpectedResult();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Writer out = new BufferedWriter(new OutputStreamWriter(outputStream));

		// set up JAXB marshalling context
		JAXBContext context = JAXBContext.newInstance(clazz);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		// this property works fine with jdk1.6.0_16
		// marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new FimesNamespacePrefixMapper());
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new FimesNamespacePrefixMapper());

		marshaller.marshal(expectedResult, out);

		// marshal to console - nice if you're looking
		marshaller.marshal(expectedResult, new BufferedWriter(new OutputStreamWriter(System.out)));

		// convert to InputSource for the comparison test
		InputSource marshallingResult = new InputSource(new ByteArrayInputStream(outputStream.toByteArray()));

		// get expected result from filesystem
		InputSource expectedResultFile = new InputSource(new FileInputStream(fileShort));

		// comparison test
		Diff diff = new Diff(expectedResultFile, marshallingResult);
		diff.overrideDifferenceListener(new IgnoreTextAndAttributeValuesDifferenceListener());
		assertTrue("Marshalled Object matches expected XML " + diff, diff.similar());
	}

	/**
	 * Unmarshall is from xml to java. For the project we only need from java to xml.
	 * 
	 * @throws JAXBException
	 */
	public void testUnmarshallFull() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		JaxbValidationEventHandler handler = new JaxbValidationEventHandler();
		unmarshaller.setEventHandler(handler);

		Object object = unmarshaller.unmarshal(file);
		if (!handler.getMessages().isEmpty()) {
			String errorMessage = "XML parse errors:";
			for (Object message : handler.getMessages()) {
				errorMessage += "\n" + message;
			}
			throw new JAXBException(errorMessage);
		}

		// test equality
		// Equality cannot be tested like this because the equals of the jaxb
		// objects cannot be changed. Therefore it
		// will be tested on not null
		// assertEquals("Unmarshalling object", expectedResult, object);
		assertNotNull(object);
	}

	/**
	 * preparing the expected result in the
	 */
	public FIGISDoc prepareExpectedResult() {
		VMEIdent vmeIdent = new VMEIdent();

		FigisID figisID = new FigisID();
		figisID.setContent("10");

		Title title = new Title();
		title.setContent("Corner Rise Seamounts");

		WaterAreaRef waterAreaRef = new WaterAreaRef();

		ForeignID foreignID = new ForeignID();
		foreignID.setCodeSystem("vme");
		foreignID.setCode("VME_5067");
		waterAreaRef.getFigisIDsAndForeignIDs().add(foreignID);

		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(figisID);
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(title);
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(waterAreaRef);
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add("2012");

		VME vme = new VME();
		vme.setVMEIdent(vmeIdent);

		FIGISDoc figisDoc = new FIGISDoc();
		figisDoc.setVME(vme);

		return figisDoc;

	}

}
