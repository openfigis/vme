package org.vme.fimes.jaxb;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.fao.fi.figis.devcon.FIGISDoc;

/**
 * Convert the java jaxb xml to a string.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class JaxbMarshall {

	private static Marshaller marshaller = null;

	static {
		try {
			JAXBContext context = JAXBContext.newInstance(FIGISDoc.class);
			marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			// this property works fine with jdk1.6.0_16
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new FimesNamespacePrefixMapper());
		} catch (JAXBException e) {
			throw new RuntimeException(
					"There was a problem creating a JAXBContext object for formatting the object to XML.");
		}
	}

	/**
	 * 
	 * @param figisDoc
	 * @return
	 */
	public String marshalToString(FIGISDoc figisDoc) {
		StringWriter sw = new StringWriter();
		try {
			marshaller.marshal(figisDoc, sw);
		} catch (JAXBException e) {
			throw new RuntimeException("There was a problem creating a the figis xml from a java object", e);
		}
		return sw.toString();
	}
}
