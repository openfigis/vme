package org.vme.fimes.jaxb;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.xml.sax.SAXException;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

/**
 * Convert the java jaxb xml to a string.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class JaxbMarshall {

	private static Marshaller marshaller = null;
	private static final String ERROR = "There was a problem creating a JAXBContext object for serializing the object to XML.";

	private static final String SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";

	String UGLY_STRING = " xsi:type=\"xs:string\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"";

	static {
		try {

			// For some reason the jsr173 jar does not have anymore the
			// W3C_XML_SCHEMA_NS_URI. Therefore
			// SchemaFactory sf =
			// SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			SchemaFactory sf = SchemaFactory.newInstance(SCHEMA_LANGUAGE);

			JAXBContext context = JAXBContext.newInstance(FIGISDoc.class);
			marshaller = context.createMarshaller();
			marshaller.setEventHandler(new FimesValidationEventHandler());
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
					"http://www.fao.org/fi/figis/devcon/ http://www.fao.org/figis/fimes/schema/3_6/fi.xsd");

			// is this one needed, since JAXB_SCHEMA_LOCATION is also set?
			Schema schema = sf.newSchema(new URL("http://www.fao.org/figis/fimes/schema/3_6/fi.xsd"));
			marshaller.setSchema(schema);

			// avoid jaxb from escaping characters (nesessary to handle properly
			// CDATA)
			marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
				@Override
				public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
					out.write(ch, start, length);
				}
			});

			// this property works fine with jdk1.6.0_16
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new FimesNamespacePrefixMapper());

		} catch (JAXBException e) {
			throw new FimesSchemaException(ERROR, e);
		} catch (MalformedURLException e) {
			throw new FimesSchemaException(ERROR, e);
		} catch (SAXException e) {
			throw new FimesSchemaException(ERROR, e);
		}
	}

	public void validate(FIGISDoc figisDoc) {
		try {

			JAXBContext jc = JAXBContext.newInstance(FIGISDoc.class);
			JAXBSource figisDocJAXBSource = new JAXBSource(jc, figisDoc);

			SchemaFactory sf = SchemaFactory.newInstance(SCHEMA_LANGUAGE);

			Source fiMasterSource = new StreamSource("http://www.fao.org/figis/fimes/schema/3_6/fi_master.xsd");
			Schema schema = sf.newSchema(fiMasterSource);

			Validator validator = schema.newValidator();
			validator.setErrorHandler(new FimesErrorHandler());
			validator.validate(figisDocJAXBSource);

		} catch (JAXBException e) {
			throw new FimesSchemaException(ERROR, e);
		} catch (SAXException e) {
			throw new FimesSchemaException(ERROR, e);
		} catch (IOException e) {
			throw new FimesSchemaException(ERROR, e);
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

		String documentString = sw.toString();
		documentString = documentString.replaceAll(UGLY_STRING, "");
		return documentString;
	}
}
