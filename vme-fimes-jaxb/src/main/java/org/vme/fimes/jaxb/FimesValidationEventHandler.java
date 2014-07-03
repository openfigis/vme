package org.vme.fimes.jaxb;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FimesValidationEventHandler implements ValidationEventHandler {
	private static final Logger LOG = LoggerFactory.getLogger(FimesValidationEventHandler.class);

	@Override
	public boolean handleEvent(ValidationEvent event) {

		LOG.debug("MESSAGE: {}", event.getMessage());

		return true;

	}
}
