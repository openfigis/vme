/**
 * (c) 2013 FAO / UN (project: reports-store-gateway-support)
 */
package org.fao.fi.vme.domain.support;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.AbstractDataConverter;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 16/nov/2013   Fabio     Creation.
 *
 * @version 1.0
 * @since 16/nov/2013
 */
public class MultiLingualStringConverter extends AbstractDataConverter<MultiLingualString> {
	static final private MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
	
	/* (non-Javadoc)
	 * @see org.gcube.application.rsg.support.converters.impl.AbstractDataConverter#doConvert(java.io.Serializable)
	 */
	@Override
	protected String doConvert(MultiLingualString element) throws IllegalArgumentException {
		return UTIL.getEnglish(element);
	}

	/* (non-Javadoc)
	 * @see org.gcube.application.rsg.support.converters.impl.AbstractDataConverter#doParse(java.lang.String)
	 */
	@Override
	protected MultiLingualString doParse(String value) throws IllegalArgumentException {
		return UTIL.english(value);
	}
}
