package org.fao.fi.figis.devcon;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.custommonkey.xmlunit.XMLTestCase;
import org.fao.fi.commons.JaxbValidationEventHandler;
import org.junit.Test;

public class FIGISDocTest extends XMLTestCase {

	File file = new File("src/test/resources/VMEFactSheet_XML_prototype.xml");

	@Test
	public void testUnmarshall() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(FIGISDoc.class);
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

	@Test
	public void testSetVME() {
		fail("Not yet implemented");
	}

}
