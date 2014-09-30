package org.vme.fimes.jaxb;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * 
 * ErrorHandler for handling detected validation errors when a FigisDic is
 * validated against the FI schema.
 * 
 * @author Erik van Ingen
 *
 */
public class FimesErrorHandler implements ErrorHandler {

	private static String ACCEPTED_ERROR1 = "cvc-elt.4.3: Type 'xs:string' is not validly derived from the type definition, 'Min', of element 'Min'.";
	private static String ACCEPTED_ERROR2 = "cvc-elt.4.3: Type 'xs:string' is not validly derived from the type definition, 'Max', of element 'Max'.";

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		throw new FimesSchemaException(exception);
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		System.out.println(exception.getMessage());
		if (!exception.getMessage().equals(ACCEPTED_ERROR1) && !exception.getMessage().equals(ACCEPTED_ERROR2)) {
			throw new FimesSchemaException(exception);
		}
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		throw new FimesSchemaException(exception);
	}

}
