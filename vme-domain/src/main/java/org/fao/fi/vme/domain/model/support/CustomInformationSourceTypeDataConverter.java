/**
 * (c) 2014 FAO / UN (project: vme-domain)
 */
package org.fao.fi.vme.domain.model.support;

import java.util.HashMap;
import java.util.Map;

import org.fao.fi.vme.domain.model.reference.InformationSourceType;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.AbstractDataConverter;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 27 Feb 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 27 Feb 2014
 */

//To actually remove this, we need to update the RM client libraries to the new versions of the RSG libs
/**
 * @deprecated
 */
@Deprecated
public class CustomInformationSourceTypeDataConverter extends AbstractDataConverter<InformationSourceType> {
	private final Map<Long, InformationSourceType> mapById = new HashMap<Long, InformationSourceType>();
	
	public CustomInformationSourceTypeDataConverter() {
		mapById.put(1L, new InformationSourceType(1L, "Book", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
		mapById.put(2L, new InformationSourceType(2L, "Meeting documents", InformationSourceType.IS_A_MEETING_DOCUMENT));
		mapById.put(3L, new InformationSourceType(3L, "Journal", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
		mapById.put(4L, new InformationSourceType(4L, "Project", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
		mapById.put(6L, new InformationSourceType(6L, "CD-ROM/DVD", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
		mapById.put(99L, new InformationSourceType(99L, "Other", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
	}
	
	/* (non-Javadoc)
	 * @see org.gcube.application.rsg.support.compiler.bridge.converters.impl.AbstractDataConverter#doParse(java.lang.String)
	 */
	@Override
	protected InformationSourceType doParse(String value){
		return mapById.get(Long.parseLong(value));
	}

	/* (non-Javadoc)
	 * @see org.gcube.application.rsg.support.compiler.bridge.converters.impl.AbstractDataConverter#doConvert(java.lang.Object)
	 */
	@Override
	protected String doConvert(InformationSourceType element){
		return element.getId().toString();
	}
}
