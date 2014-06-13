package org.fao.fi.vme.batch.sync2.mapping.xml;

import javax.xml.bind.JAXBElement;

/**
 * Utility to fill the value of a Jaxb object
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class JAXBElementUtil {

	@SuppressWarnings("unchecked")
	public <T> void fillObject(Object value, JAXBElement<T> jaxbObject) {
		if (value != null && jaxbObject != null) {
			T cccc = (T) value.toString();
			jaxbObject.setValue(cccc);
		}
	}
}
