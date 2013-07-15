package org.fao.fi.figis.devcon;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

import org.w3c.dom.Node;

public class JaxbValidationEventHandler implements ValidationEventHandler {
	private final List<String> messages = new ArrayList<String>();

	public List<String> getMessages() {
		return messages;
	}

	@Override
	public boolean handleEvent(ValidationEvent event) {
		if (event == null)
			throw new IllegalArgumentException("event is null");

		// calculate the severity prefix and return value
		String severity = null;
		boolean continueParsing = false;
		switch (event.getSeverity()) {
		case ValidationEvent.WARNING:
			severity = "Warning";
			continueParsing = true; // continue after warnings
			break;
		case ValidationEvent.ERROR:
			severity = "Error";
			continueParsing = true; // terminate after errors
			break;
		case ValidationEvent.FATAL_ERROR:
			severity = "Fatal error";
			continueParsing = false; // terminate after fatal errors
			break;
		default:
			assert false : "Unknown severity.";
		}

		String location = getLocationDescription(event);
		String message = severity + " parsing " + location + " due to " + event.getMessage();
		messages.add(message);

		return continueParsing;
	}

	private String getLocationDescription(ValidationEvent event) {
		ValidationEventLocator locator = event.getLocator();
		if (locator == null) {
			return "XML with location unavailable";
		}

		StringBuffer msg = new StringBuffer();
		URL url = locator.getURL();
		Object obj = locator.getObject();
		Node node = locator.getNode();
		int line = locator.getLineNumber();

		if (url != null || line != -1) {
			msg.append("line " + line);
			if (url != null)
				msg.append(" of " + url);
		} else if (obj != null) {
			msg.append(" obj: " + obj.toString());
		} else if (node != null) {
			msg.append(" node: " + node.toString());
		}

		return msg.toString();
	}
}
